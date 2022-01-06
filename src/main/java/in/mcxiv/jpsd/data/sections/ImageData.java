package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;

public class ImageData extends DataObject {

    private int[] imageData;

    public ImageData(byte[] data) {
        throw new UnsupportedOperationException();
    }

    public ImageData(int[] imgData) {
        imageData = imgData;
    }

    public int[] getData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imageData.length=" + imageData.length +
                '}';
    }
}
