package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.complex.Descriptor;

public class FillSetting extends AdditionalLayerInfo {

    int version;
    Descriptor descriptor;

    public FillSetting(AdditionalInfoKey key, long length, int version, Descriptor descriptor) {
        super(key, length);
        this.version = version;
        this.descriptor = descriptor;
    }
}
