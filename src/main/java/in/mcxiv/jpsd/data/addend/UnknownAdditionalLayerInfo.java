package in.mcxiv.jpsd.data.addend;

public class UnknownAdditionalLayerInfo extends AdditionalLayerInfo {

    private byte[] data;

    public UnknownAdditionalLayerInfo(AdditionalInfoKey key, byte[] data) {
        this(key, data, -1);
    }

    public UnknownAdditionalLayerInfo(AdditionalInfoKey key, byte[] data, long length) {
        super(key, length);
        this.data = data;
    }
}
