package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.sections.FileHeaderData.ColorMode;
import in.mcxiv.jpsd.io.PSDFileReader;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public class ImageData extends DataObject {

    private byte[][] imageData;

    public ImageData(byte[][] imageData) {
        this.imageData = imageData;
    }

    public ImageData(byte[] data) {

    }

    public byte[][] getImageData() {
        return imageData;
    }

    public int getNumberOfChannels() {
        return imageData.length;
    }

    /**
     * @return returns a 1D array representing the 2D image's channelIdx-th channel.
     * The 2D array is packed by arranging each rows one after another.
     */
    public byte[] getChannelData(int channelIdx) {
        return imageData[channelIdx];
    }

    public BufferedImage createImage(FileHeaderData header) {

        int w = header.getWidth();
        int h = header.getHeight();
        int channels = header.getChannels();
        ColorMode colorMode = header.getColorMode();
        short depth = header.getDepth();

        WritableRaster raster = WritableRaster.createBandedRaster(DataBuffer.TYPE_BYTE, w, h, channels, new Point());
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();

        for (int i = 0; i < channels; i++)
            System.arraycopy(imageData[i], 0, dataBuffer.getData(i), 0, imageData[i].length);

        ComponentColorModel colorModel = new ComponentColorModel(
                colorMode.getColorSpace(),
                null,
                false,
                false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);

        BufferedImage image = new BufferedImage(colorModel, raster, false, null);

        if (PSDFileReader.debugging()) {
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++)
                    System.out.print(Integer.toBinaryString(image.getRGB(i, j)) + " ");
                System.out.println();
            }
        }

        return image;
    }

    public BufferedImage __createImage(FileHeaderData header) {

        int w = header.getWidth();
        int h = header.getHeight();
        int channels = header.getChannels();
        ColorMode colorMode = header.getColorMode();
        short depth = header.getDepth();
        BufferedImage image = new BufferedImage(w, h, colorMode.getBIType());

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                int extent = i + j * w;

                int color = 0xFF << 24;
                for (int k = channels - 1, l = 0; k >= 0; k--, l++) {
                    int component = imageData[l][extent];
                    color |= component << (depth * k);
                }

                image.setRGB(i, j, color);
            }
        }

        return image;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imageData.length=" + imageData.length +
                ", imageData<i>.length=" + Arrays.toString(Arrays.stream(imageData).map(bytes -> bytes.length).toArray()) +
                '}';
    }
}
