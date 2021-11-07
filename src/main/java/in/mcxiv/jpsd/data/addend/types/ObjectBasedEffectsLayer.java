package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.complex.Descriptor;

// TODO
public class ObjectBasedEffectsLayer extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.OBJECT_BASED_EFFECTS_LAYER_KEY;

    private int version;
    private int descriptorVersion;
    private Descriptor descriptor;

    public ObjectBasedEffectsLayer(String signature, long length) {
        super(KEY, length);
    }
}
