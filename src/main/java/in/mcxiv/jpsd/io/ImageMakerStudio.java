package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.ImageMeta;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.GridAndGuidesRBlock;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock;
import in.mcxiv.jpsd.data.resource.types.VersionInfoRBlock;
import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.io.Utility.FunctionII;
import in.mcxiv.jpsd.io.Utility.Map2Dto1D;

import javax.imageio.ImageTypeSpecifier;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.imageio.ImageTypeSpecifier.createBanded;

public class ImageMakerStudio {

    public static BufferedImage toImage(PSDConnection file) {
        // We expect only RGB info present inside data.
        // TODO: support CMYK (by preprocessing the data into RGB)

        int[] data = file.getImageData().getData();

        FileHeaderData fhd = file.getFileHeaderData();
        int w = fhd.getWidth();
        int h = fhd.getHeight();
        DepthEntry depth = fhd.getDepthEntry();
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);

        LayerInfo layerInfo = file.getLayerAndMaskData().justGetALayerInfo();
        boolean hasAlpha = layerInfo != null && layerInfo.hasAlpha();

        int[] bankIndices = hasAlpha ? new int[]{0, 1, 2, 3} : new int[]{0, 1, 2};
        int[] bandOffsets = hasAlpha ? new int[4] : new int[3];

        BufferedImage image;
        switch (depth) { //@formatter:off
            case E:
                image = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(w, h);
                byte[][] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < bytes.length; i++)
                    for (int j = 0; j < bytes[i].length; j++)
                        bytes[i][j] = (byte) data[bytes[i].length * i + j];
                break;
            case S:
                image = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(w, h);
                short[][] shorts = ((DataBufferUShort) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < shorts.length; i++)
                    for (int j = 0; j < shorts[i].length; j++)
                        shorts[i][j] = (short) data[shorts[i].length * i + j];
                break;
            case T:
//                image = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(w, h);
//                int[][] ints = ((DataBufferInt) image.getRaster().getDataBuffer()).getBankData();
//                for (int i = 0; i < ints.length; i++)
//                    for (int j = 0; j < ints[i].length; j++)
//                        ints[i][j] = data[ints[i].length * i + j];

                BufferedImage out = new BufferedImage(fhd.getWidth(), fhd.getHeight(), BufferedImage.TYPE_INT_RGB);
                int s = fhd.getHeight() * fhd.getWidth();
                for (int x = 0; x < fhd.getWidth(); x++) {
                    for (int y = 0; y < fhd.getHeight(); y++) {
                        int i = y * fhd.getWidth() + x;
                        out.setRGB(x, y, (0xFF << 24) |
                                (((int) (data[i] * 255d / Integer.MAX_VALUE)) << 16) |
                                (((int) (data[i + s] * 255d / Integer.MAX_VALUE)) << 8) |
                                (((int) (data[i + 2 * s] * 255d / Integer.MAX_VALUE)))
                        );
                    }
                }
                image = out;
                break;
            case O:
            default:
                throw new IllegalStateException();
                //@formatter:on
        }
        return image;
    }

    public static PSDConnection fromImage(BufferedImage image) {
        // We expect only RGB info present inside data.
        // TODO: support CMYK (by preprocessing the data into RGB)

        short channels = 3;
        int width = image.getWidth();
        int height = image.getHeight();
        DepthEntry depth = DepthEntry.of(image);

        FileHeaderData fhd = new FileHeaderData(FileVersion.PSD, channels, height, width, depth.getValue(), ColorMode.RGB.getValue());

        ColorModeData colorModeData = new ColorModeData();

        List<ImageResourceBlock> blocks = new ArrayList<>();
        blocks.add(new ResolutionRBlock(ImageResourceID.Resolution, "", -1, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches));
        blocks.add(new GridAndGuidesRBlock(ImageResourceID.GridAndGuides, "", -1, 1, 576, 576, null));
        blocks.add(new VersionInfoRBlock(ImageResourceID.VersionInfo, "", -1, 1, true, "JPSD", "JPSD", 1));

        ImageResourcesData imageResourcesData = new ImageResourcesData(blocks.toArray(new ImageResourceBlock[0]));

        LayerAndMaskData layerAndMaskData = new LayerAndMaskData();

        ImageData imageData = new ImageData(convertToRawData(image));

        return new PSDConnection(fhd, colorModeData, imageResourcesData, layerAndMaskData, imageData);
    }

    public static int[] convertToRawData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int channels = 3;

        DepthEntry depth = DepthEntry.of(image);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        SampleModel sampleModel = image.getSampleModel();
        Map2Dto1D indexMap = Utility.getIndexMap(sampleModel);
        FunctionII componentMap = Utility.getComponentMap(image);

        int data[] = new int[width * height * channels];

        /*if (true) {

            boolean hasAlpha = image.getTransparency() != Transparency.OPAQUE;

            int[] bankIndices = hasAlpha ? new int[]{0, 1, 2, 3} : new int[]{0, 1, 2};
            int[] bandOffsets = hasAlpha ? new int[4] : new int[3];

            BufferedImage buffer = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(width, height);
            Graphics2D graphics = buffer.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();

            image = buffer;
        }*/

        if (sampleModel instanceof SinglePixelPackedSampleModel) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Color c = new Color(image.getRGB(i, j));
                    data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, 0)] = c.getRed();
                    data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, 1)] = c.getGreen();
                    data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, 2)] = c.getBlue();
                }
            }
        } else if (sampleModel instanceof BandedSampleModel) switch (depth) {
            case E:
                byte[][] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++)
                        for (int c = 0; c < channels; c++)
                            data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, c)] =
                                    bytes[componentMap.map(c)][indexMap.map(width, i, height, j, channels, 0)];
                break;
            case S:
                short[][] shorts = ((DataBufferUShort) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++)
                        for (int c = 0; c < channels; c++)
                            data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, c)] =
                                    shorts[componentMap.map(c)][indexMap.map(width, i, height, j, channels, 0)];
                break;
            case T:
                int[][] ints = ((DataBufferInt) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++)
                        for (int c = 0; c < channels; c++)
                            data[Utility.BANDED_INDEX_MAP.map(width, i, height, j, channels, c)] =
                                    ints[componentMap.map(c)][indexMap.map(width, i, height, j, channels, 0)];
                break;
            case O:
                throw new UnsupportedOperationException();
        }

        return data;
    }

    public static BufferedImage toImage(LayerRecord layer, PSDConnection file) {
        return toImage(layer, file, false);
    }

    public static BufferedImage toImageMask(LayerRecord layer, PSDConnection file) {
        if (layer.hasInfo(ChannelInfo.ChannelID.UserSuppliedMask))
            return toImage(layer, file, true);
        return null;
    }

    private static BufferedImage toImage(LayerRecord layer, PSDConnection file, boolean mask) {

        int w = layer.getWidth();
        int h = layer.getHeight();
        DepthEntry depth = file.getFileHeaderData().getDepthEntry();
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);

        boolean hasAlpha = layer.hasAlpha();

        int[] bankIndices = hasAlpha ? new int[]{0, 1, 2, 3} : new int[]{0, 1, 2};
        int[] bandOffsets = hasAlpha ? new int[4] : new int[3];

        int[][] data = new int[bandOffsets.length][];  // === hasAlpha ? new int[4][] : new int[3][];
        ImageMeta meta = new ImageMeta(w, h, file.getFileHeaderData().isLarge(), ColorMode.RGB, 1, depth);

        if (mask) {
            ChannelImageData cid = layer.getInfo(ChannelInfo.ChannelID.UserSuppliedMask).getData();
            int[] decode = RawDataDecoder.decode(cid.getCompression(), cid.getData(), meta);
            Arrays.fill(data, decode);
        } else for (int i = 0; i < data.length; i++) {
            ChannelImageData cid = layer.getInfo(i == 3 ? -1 : i).getData();
            data[i] = RawDataDecoder.decode(cid.getCompression(), cid.getData(), meta);
        }

        BufferedImage image;
        switch (depth) { //@formatter:off
            case E:
                image = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(w, h);
                byte[][] bytes = ((DataBufferByte) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < bytes.length; i++)
                    for (int j = 0; j < bytes[i].length; j++)
                        bytes[i][j] = (byte) data[i][j]; // data[bytes[i].length * i + j];
                break;
            case S:
                image = createBanded(cs, bankIndices, bandOffsets, depth.getDataType(), hasAlpha, false).createBufferedImage(w, h);
                short[][] shorts = ((DataBufferUShort) image.getRaster().getDataBuffer()).getBankData();
                for (int i = 0; i < shorts.length; i++)
                    for (int j = 0; j < shorts[i].length; j++)
                        shorts[i][j] = (short) data[i][j];
                break;
            case T:
                FileHeaderData fhd = file.getFileHeaderData();
                BufferedImage out = new BufferedImage(fhd.getWidth(), fhd.getHeight(), BufferedImage.TYPE_INT_RGB);
                int s = fhd.getHeight() * fhd.getWidth();
                for (int x = 0; x < fhd.getWidth(); x++) {
                    for (int y = 0; y < fhd.getHeight(); y++) {
                        int i = y * fhd.getWidth() + x;
                        out.setRGB(x, y, (0xFF << 24) |
                                (((int) (data[1][i] * 255d / Integer.MAX_VALUE)) << 16) |
                                (((int) (data[2][i] * 255d / Integer.MAX_VALUE)) << 8) |
                                (((int) (data[3][i] * 255d / Integer.MAX_VALUE)))
                        );
                    }
                }
                image = out;
                break;
            case O:
            default:
                throw new IllegalStateException();
                //@formatter:on
        }
        return image;
    }


}
