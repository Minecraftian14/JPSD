package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.DepthEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ImageData extends DataObject {

    private int[] imageData;

    public ImageData(byte[] data) {
        throw new NotImplementedException();
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
