package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.complex.Descriptor;

public class FillSetting extends AdditionalLayerInfo {

    int version;
    Descriptor descriptor;

    public FillSetting(AdditionalInfoKey key, int version, Descriptor descriptor) {
        this(key, -1, version, descriptor);
    }

    public FillSetting(AdditionalInfoKey key, long length, int version, Descriptor descriptor) {
        super(key, length);

        if (key != AdditionalInfoKey.SOLID_COLOR_SHEET_SETTING &&
            key != AdditionalInfoKey.GRADIENT_FILL_SETTING &&
            key != AdditionalInfoKey.PATTERN_FILL_SETTING)
            throw new IllegalArgumentException();

        this.version = version;
        this.descriptor = descriptor;
    }
}
