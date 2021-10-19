package in.mcxiv.jpsd.data.addend;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;

public class UnknownAdditionalLayerInfo extends AdditionalLayerInfo {

    private byte[] data;

    public UnknownAdditionalLayerInfo(AdditionalInfoKey key, byte[] data, long length) {
        super(key, length);
        this.data = data;
    }
}
