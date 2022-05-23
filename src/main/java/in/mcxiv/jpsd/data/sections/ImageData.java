package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;

public class ImageData extends DataObject {

    private byte[] imageData;

    public ImageData(byte[] data) {
        imageData = data;
    }

    public ImageData(int[] imgData) {
//        imageData = imgData;
        throw new UnsupportedOperationException();
    }

    public int[] getData() {
        throw new UnsupportedOperationException();
//        return imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imageData.length=" + imageData.length +
                '}';
    }
}
