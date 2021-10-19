package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.Arrays;

public class RawImageRBlock extends ImageResourceBlock {

    private byte[] data;

    public RawImageRBlock(ImageResourceID identity, String pascalString, long length, byte[] data) {
        super(identity, pascalString, length);
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RawImageRBlock{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
