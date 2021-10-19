package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.sections.ImageData;

import java.util.Arrays;

public class RawImageRBlock extends ImageResourceBlock {

    private ImageData imageData;
    private byte[] data;

    public RawImageRBlock(ImageResourceID identity, String pascalString, long length, byte[] data) {
        super(identity, pascalString, length);
        imageData = new ImageData(this.data = data);
    }

    public ImageData getImageData() {
        return imageData;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RawImageRBlock{" +
                "imageData=" + imageData +
                ", data.length=" + data.length +
                '}';
    }
}
