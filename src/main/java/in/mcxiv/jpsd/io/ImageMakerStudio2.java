package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.io.Utility.FunctionII;
import in.mcxiv.jpsd.io.Utility.Map2Dto1D;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;

public class ImageMakerStudio2 {

    // NOTE IMPORTANT
    //  when the depth is only like 8 or 16
    //  a single byte in the data being -1 means it represents 255!
    //  Meanwhile for 32 bit
    //  -1 is 01 only :eyes:
    //  127 seems to be the max
    public static byte[][] bytesBankFromImage(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        ColorModel colorModel = image.getColorModel();
        WritableRaster raster = image.getRaster();
        DataBuffer buffer = raster.getDataBuffer();
        SampleModel sampleModel = raster.getSampleModel();

        DepthEntry depth = DepthEntry.of(buffer);
        assert depth != null;
        ColorSpace colorSpace = colorModel.getColorSpace();
        int channels = sampleModel.getNumBands();

        if (colorSpace == null) {
            // TODO: Handle Indexed/Bitmap/Multichannel
            return null;
        }

        byte[][] data = new byte[channels][w * h * depth.getBytes()];

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        boolean didConversionFail = true;
        if (sampleModel instanceof SinglePixelPackedSampleModel) {
            int[] bitMasks = ((SinglePixelPackedSampleModel) sampleModel).getBitMasks();
            int[] bitOffsets = ((SinglePixelPackedSampleModel) sampleModel).getBitOffsets();

            switch (depth) {
                case O:
                    break;
                case E:
                    didConversionFail = false;
                    // TODO: what is this? 2 bit per channel?
                    byte[] bytes = ((DataBufferByte) buffer).getData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 1, 0)] = (byte)
                                        ((bytes[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;

                case S:
                    didConversionFail = false;
                    short[] shorts;
                    if (buffer instanceof DataBufferShort)
                        shorts = ((DataBufferShort) buffer).getData();
                    else if (buffer instanceof DataBufferUShort)
                        shorts = ((DataBufferUShort) buffer).getData();
                    else break;
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 1, 0)] = (byte)
                                        ((shorts[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;

                case T:
                    didConversionFail = false;
                    int[] ints = ((DataBufferInt) buffer).getData();

                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 1, 0)] = (byte)
                                        ((ints[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;
            }
        } else if (sampleModel instanceof BandedSampleModel) {
            Map2Dto1D indexMap = Utility.getIndexMap(sampleModel);
            FunctionII componentMap = Utility.getComponentMap(image);

            switch (depth) {
                case E:
                    didConversionFail = false;
                    byte[][] bytes = ((DataBufferByte) buffer).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 1, 0)] =
                                        bytes[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)];
                    break;
                case S:
                    didConversionFail = false;
                    short[][] shorts = ((DataBufferUShort) image.getRaster().getDataBuffer()).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                for (int b = 0; b < 2; b++)
                                    data[0][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 2, b)] =
                                            (byte) ((shorts[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)] >>> (8 * b)) & 0xff);

                    break;
                case T:
                    didConversionFail = false;
                    int[][] ints = ((DataBufferInt) image.getRaster().getDataBuffer()).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                for (int b = 0; b < 4; b++)
                                    data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 4, b)] =
                                            (byte) ((ints[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)]) >>> (8 * b) & 0xff);
                    break;
                case O:
                    throw new UnsupportedOperationException();
            }
        }
        if (didConversionFail) {
            for (int i = 0; i < w; i++)
                for (int j = 0; j < h; j++)
                    for (int c = 2; c >= 0; c--)
                        data[c][Utility.BANDED_INDEX_MAP.map(w, i, h, j, 1, 0, 1, 0)] =
                                (byte) ((image.getRGB(i, j) >>> (16 * c)) & 0xff);
        }

        return data;
    }

    public static BufferedImage toImage(PSDConnection connection) {

        FileHeaderData fhd = connection.getFileHeaderData();
        int w = fhd.getWidth();
        int h = fhd.getHeight();
        DepthEntry depth = fhd.getDepthEntry();
        ColorMode colorMode = fhd.getColorMode();
        short channels = fhd.getNumberOfChannels();

        ColorSpace colorSpace = colorMode.getColorSpace();

        if (colorSpace == null) {
            // TODO: Handle Indexed/Bitmap/Multichannel
            return null;
        }

        assert channels == colorSpace.getNumComponents() || channels == colorSpace.getNumComponents() + 1;
        boolean hasAlpha = channels == colorSpace.getNumComponents() + 1;
        int transferType = depth.getDataType();

        ComponentColorModel colorModel = new ComponentColorModel(colorSpace, hasAlpha, true, Transparency.TRANSLUCENT, transferType);

        BandedSampleModel sampleModel = new BandedSampleModel(transferType, w, h, channels);

        DataBuffer buffer = null;
        byte[] data = connection.getImageData().getImageData();
        assert data.length == w * h * channels * depth.getBytes();

        int span = w * h * depth.getBytes();

        switch (depth) {

            case O:
                DataBufferByte dataBufferByte0 = new DataBufferByte(data.length / channels / depth.getBytes(), channels);
                for (int i = 0; i < channels; i++) {
                    byte[] channel = dataBufferByte0.getData(i);
                    for (int j = 0; j < channel.length; j++)
                        channel[j] = (byte) (data[i * span + j] == 0 ? 0 : -1);
                }
                buffer = dataBufferByte0;
                break;

            case E:
                DataBufferByte dataBufferByte = new DataBufferByte(data.length / channels / depth.getBytes(), channels);
                for (int i = 0; i < channels; i++) {
                    byte[] channel = dataBufferByte.getData(i);
                    System.arraycopy(data, i * span, channel, 0, channel.length);
                }
                buffer = dataBufferByte;
                break;

            case S:
                DataBufferUShort dataBufferUShort = new DataBufferUShort(data.length / channels / depth.getBytes(), channels);
                for (int i = 0; i < channels; i++) {
                    short[] channel = dataBufferUShort.getData(i);
                    ByteBuffer wrap = ByteBuffer.wrap(data, i * span, span);
                    for (int j = 0; j < channel.length; j++)
                        channel[j] = wrap.getShort();
                }
                buffer = dataBufferUShort;
                break;

            case T:
                throw new UnsupportedOperationException("This feature is not yet implemented, it requires writing some stuff for managing for pixel data is stored in HDR images. Make an issue if this feature should be implemented.");
                /*DataBufferInt dataBufferInt = new DataBufferInt(data.length / channels / depth.getBytes(), channels);
                for (int i = 0; i < channels; i++) {
                    int[] channel = dataBufferInt.getData(i);
                    ByteBuffer wrap = ByteBuffer.wrap(data, i * span, span);
                    for (int j = 0; j < channel.length; j++)
                        channel[j] = Math.abs(wrap.getInt());
                }
                buffer = dataBufferInt;*/
                /*DataBufferFloat dataBufferFloat = new DataBufferFloat(data.length / channels / depth.getBytes(), channels);
                for (int i = 0; i < channels; i++) {
                    float[] channel = dataBufferFloat.getData(i);
                    ByteBuffer wrap = ByteBuffer.wrap(data, i * span, span);
                    for (int j = 0; j < channel.length; j++)
                        channel[j] = wrap.getFloat();
                }
                buffer = dataBufferFloat;*/
        }

        WritableRaster raster = WritableRaster.createWritableRaster(sampleModel, buffer, null);

        // todo: what does raster being premultiplied mean? should I do something in connection->bi to account for it? what about bi->connection?
        return new BufferedImage(colorModel, raster, true, new Hashtable<>());
    }

    public static PSDConnection fromImage(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        ColorModel colorModel = image.getColorModel();
        WritableRaster raster = image.getRaster();
        DataBuffer buffer = raster.getDataBuffer();
        SampleModel sampleModel = raster.getSampleModel();

        DepthEntry depth = DepthEntry.of(buffer);
        assert depth != null;
        ColorSpace colorSpace = colorModel.getColorSpace();
        ColorMode colorMode = ColorMode.of(colorSpace);
        int channels = sampleModel.getNumBands();

        if (colorSpace == null) {
            // TODO: Handle Indexed/Bitmap/Multichannel
            return null;
        }

        byte[] data = new byte[w * h * channels * depth.getBytes()];

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        boolean didConversionFail = true;
        if (sampleModel instanceof SinglePixelPackedSampleModel) {
            int[] bitMasks = ((SinglePixelPackedSampleModel) sampleModel).getBitMasks();
            int[] bitOffsets = ((SinglePixelPackedSampleModel) sampleModel).getBitOffsets();

            switch (depth) {
                case O:
                    break;
                case E:
                    didConversionFail = false;
                    // TODO: what is this? 2 bit per channel?
                    byte[] bytes = ((DataBufferByte) buffer).getData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 1, 0)] = (byte)
                                        ((bytes[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;

                case S:
                    didConversionFail = false;
                    short[] shorts;
                    if (buffer instanceof DataBufferShort)
                        shorts = ((DataBufferShort) buffer).getData();
                    else if (buffer instanceof DataBufferUShort)
                        shorts = ((DataBufferUShort) buffer).getData();
                    else break;
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 1, 0)] = (byte)
                                        ((shorts[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;

                case T:
                    didConversionFail = false;
                    int[] ints = ((DataBufferInt) buffer).getData();

                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 1, 0)] = (byte)
                                        ((ints[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, 0, 1, 0)] & bitMasks[c]) >>> bitOffsets[c]);
                    break;
            }
        } else if (sampleModel instanceof BandedSampleModel) {
            Map2Dto1D indexMap = Utility.getIndexMap(sampleModel);
            FunctionII componentMap = Utility.getComponentMap(image);

            switch (depth) {
                case E:
                    didConversionFail = false;
                    byte[][] bytes = ((DataBufferByte) buffer).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 1, 0)] =
                                        bytes[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)];
                    break;
                case S:
                    didConversionFail = false;
                    short[][] shorts = ((DataBufferUShort) image.getRaster().getDataBuffer()).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                for (int b = 0; b < 2; b++)
                                    data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 2, b)] =
                                            (byte) ((shorts[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)] >>> (8 * b)) & 0xff);

                    break;
                case T:
                    didConversionFail = false;
                    int[][] ints = ((DataBufferInt) image.getRaster().getDataBuffer()).getBankData();
                    for (int i = 0; i < w; i++)
                        for (int j = 0; j < h; j++)
                            for (int c = 0; c < channels; c++)
                                for (int b = 0; b < 4; b++)
                                    data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, channels, c, 4, b)] =
                                            (byte) ((ints[componentMap.map(c)][indexMap.map(w, i, h, j, channels, 0, 1, 0)]) >>> (8 * b) & 0xff);
                    break;
                case O:
                    throw new UnsupportedOperationException();
            }
        }
        if (didConversionFail) {
            colorMode = ColorMode.RGB;
            depth = DepthEntry.E;
            channels = 3;
            for (int i = 0; i < w; i++)
                for (int j = 0; j < h; j++)
                    for (int c = 2; c >= 0; c--)
                        data[Utility.BANDED_INDEX_MAP.map(w, i, h, j, 3, c, 1, 0)] =
                                (byte) ((image.getRGB(i, j) >>> (16 * c)) & 0xff);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileHeaderData fhd = new FileHeaderData(FileVersion.PSD.getValue(), channels, h, w, depth.getValue(), colorMode.getValue());
        return new PSDConnection(fhd, new ColorModeData(), new ImageResourcesData(), new LayerAndMaskData(), new ImageData(data));
    }

    public static BufferedImage toImage(LayerRecord layer, PSDConnection file) {
        return toImage(layer, file, false);
    }

    public static BufferedImage toImageMask(LayerRecord layer, PSDConnection file) {
        if (layer.hasInfo(ChannelInfo.ChannelID.UserSuppliedMask))
            return toImage(layer, file, true);
        return null;
    }

    private static BufferedImage toImage(LayerRecord layer, PSDConnection connection, boolean mask) {
        FileHeaderData fhd = connection.getFileHeaderData();
        int w = fhd.getWidth();
        int h = fhd.getHeight();
        DepthEntry depth = fhd.getDepthEntry();
        ColorMode colorMode = mask ? ColorMode.Grayscale : fhd.getColorMode();
        short channels = (short) (mask ? 1 : layer.getColorChannelsCount());
        ColorSpace colorSpace = colorMode.getColorSpace();

        if (colorSpace == null) {
            // TODO: Handle Indexed/Bitmap/Multichannel
            return null;
        }

        boolean hasAlpha = false;
        int transferType = depth.getDataType();

        ComponentColorModel colorModel = new ComponentColorModel(colorSpace, hasAlpha, true, Transparency.TRANSLUCENT, transferType);

        BandedSampleModel sampleModel = new BandedSampleModel(transferType, w, h, channels);

        byte[][] data = new byte[channels][];
        if (mask) {
            ChannelImageData cid = layer.getInfo(ChannelInfo.ChannelID.UserSuppliedMask).getData();
            data[0] = RawDataDecoder.decode(cid.getCompression(), cid.getData(), connection.getFileHeaderData());
        } else for (int i = 0; i < data.length; i++) {
            ChannelImageData cid = layer.getInfo(i == 3 ? -1 : i).getData();
            data[i] = RawDataDecoder.decode(cid.getCompression(), cid.getData(), connection.getFileHeaderData());
        }

        DataBuffer buffer = null;
        assert data[0].length == layer.getWidth() * layer.getHeight() * depth.getBytes();

        int span = w * h * depth.getBytes();

        switch (depth) {

            case O:
                DataBufferByte dataBufferByte0 = new DataBufferByte(data.length / channels / depth.getBytes(), channels);
                for (int c = 0; c < channels; c++) {
                    byte[] channel = dataBufferByte0.getData(c);
                    for (int i = 0; i < channel.length; i++)
                        channel[i] = (byte) (data[c][i] == 0 ? 0 : -1);
                }
                buffer = dataBufferByte0;
                break;

            case E:
                DataBufferByte dataBufferByte = new DataBufferByte(data.length / channels / depth.getBytes(), channels);
                for (int c = 0; c < channels; c++) {
                    byte[] channel = dataBufferByte.getData(c);
                    System.arraycopy(data[c], c * span, channel, 0, channel.length);
                }
                buffer = dataBufferByte;
                break;

            case S:
                DataBufferUShort dataBufferUShort = new DataBufferUShort(data.length / channels / depth.getBytes(), channels);
                for (int c = 0; c < channels; c++) {
                    short[] channel = dataBufferUShort.getData(c);
                    ByteBuffer wrap = ByteBuffer.wrap(data[c]);
                    for (int i = 0; i < channel.length; i++)
                        channel[i] = wrap.getShort();
                }
                buffer = dataBufferUShort;
                break;

            case T:
                throw new UnsupportedOperationException("This feature is not yet implemented, it requires writing some stuff for managing for pixel data is stored in HDR images. Make an issue if this feature should be implemented.");
        }

        WritableRaster raster = WritableRaster.createWritableRaster(sampleModel, buffer, null);

        return new BufferedImage(colorModel, raster, true, new Hashtable<>());
    }

    private static LayerRecord fromImage(BufferedImage image, BufferedImage mask, String layerName, PSDConnection connection) {
        if (mask != null) if (mask.getColorModel().getColorSpace().equals(ColorMode.Grayscale.getColorSpace())
                              || mask.getHeight() != image.getHeight() || mask.getWidth() != image.getWidth())
            throw new IllegalArgumentException("Mask is not compatible with the given image!");

        byte[][] bytes_image = bytesBankFromImage(image);
        byte[][] bytes_mask = mask == null ? null : bytesBankFromImage(mask);
        if (bytes_mask != null && bytes_mask.length > 1)
            PSDConnection.out.println("Why does the mask have more channels?");

        ArrayList<ChannelInfo> infos = new ArrayList<>();
        for (int c = 0; c < bytes_image.length; c++)
            infos.add(new ChannelInfo(ChannelInfo.ChannelID.of((short) c), new ChannelImageData(Compression.Raw_Data, bytes_image[c])));
        if (bytes_mask != null)
            infos.add(new ChannelInfo(ChannelInfo.ChannelID.UserSuppliedMask, new ChannelImageData(Compression.Raw_Data, bytes_mask[0])));

        return new LayerRecord(new Rectangle(image.getWidth(), image.getHeight()), infos, layerName);
    }
}
