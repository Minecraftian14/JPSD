package in.mcxiv.jpsd.data.resource;

public class UnknownRBlock extends ImageResourceBlock {

    private byte[] data;

    public UnknownRBlock(ImageResourceID identity, String pascalString, long length, byte[] data) {
        super(identity, pascalString, length);
        this.data = data;
    }

}
