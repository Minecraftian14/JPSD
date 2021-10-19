package in.mcxiv.jpsd.data.resource;

import in.mcxiv.jpsd.data.DataObject;

public abstract class ImageResourceBlock extends DataObject {

    private ImageResourceID identity;
    private String pascalString;
    private long length;
//  private byte[] data;  // We'll just parse the specific data in their specific instances of ImageResourceBlock.

    public ImageResourceBlock(ImageResourceID identity, String pascalString, long length) {
        this.identity = identity;
        this.pascalString = pascalString;
        this.length = length;
    }

    public ImageResourceID getIdentity() {
        return identity;
    }

    public String getPascalString() {
        return pascalString;
    }

    @Override
    public String toString() {
        return "ImageResourceBlock{" +
                "identity=" + identity +
                ", pascalString='" + pascalString + '\'' +
                ", length when read (if read)=" + length +
                '}';
    }
}
