package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.sections.FileHeaderData.ColorMode;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public class ImageData extends DataObject {

    private byte[][] imageData;

    public ImageData(byte[][] imageData) {
        this.imageData = imageData;
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

        DataBufferByte bufferByte = new DataBufferByte(imageData, w * h);
        BandedSampleModel model = new BandedSampleModel(DataBuffer.TYPE_BYTE, w, h, channels);
        WritableRaster raster = WritableRaster.createRaster(model, bufferByte, new Point()).createCompatibleWritableRaster();

        ComponentColorModel colorModel = new ComponentColorModel(
                colorMode.getColorSpace(),
                null,
                false,
                false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);

        BufferedImage image = new BufferedImage(colorModel, raster, false, null);

        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                System.out.print(Integer.toBinaryString(image.getRGB(i, j))+" ");
            }
            System.out.println();
        }

        return image;

        /*
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                int extent = i + j * w;

                int r = imageData[0][extent];
                int g = imageData[1][extent];
                int b = imageData[2][extent];

                int c = (r << 16) + (g << 8) + b;
                c = c | (0xFF << 24);

                image.setRGB(i, j, c);

            }
        }

        return image;
        */


    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imageData.length=" + imageData.length +
                ", imageData<i>.length=" + Arrays.toString(Arrays.stream(imageData).map(bytes -> bytes.length).toArray()) +
                '}';
    }
}
