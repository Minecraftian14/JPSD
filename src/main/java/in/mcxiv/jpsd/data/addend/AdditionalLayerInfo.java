package in.mcxiv.jpsd.data.addend;

import in.mcxiv.jpsd.data.DataObject;

public class AdditionalLayerInfo extends DataObject {

    protected final AdditionalInfoKey key;
    private long length;

    public AdditionalLayerInfo(AdditionalInfoKey key, long length) {
        this.key = key;
        this.length = length;
    }

    public AdditionalInfoKey getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "AdditionalLayerInfo{" +
                "key=" + key +
                ", length=" + length +
                '}';
    }
}
