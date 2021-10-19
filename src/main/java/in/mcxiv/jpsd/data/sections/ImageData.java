package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;

import javax.imageio.stream.ImageInputStream;
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

    @Override
    public String toString() {
        return "ImageData{" +
                "imageData.length=" + imageData.length +
                ", imageData<i>.length=" + Arrays.toString(Arrays.stream(imageData).map(bytes -> bytes.length).toArray()) +
                '}';
    }
}
