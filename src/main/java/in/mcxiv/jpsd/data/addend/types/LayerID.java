package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;

public class LayerID extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.LAYER_ID_KEY;

    private int id;

    public LayerID(int id, long length) {
        super(KEY, length);
        this.id = id;
    }

    @Override
    public String toString() {
        return "LayerID{" +
                "key=" + key +
                ", id=" + id +
                '}';
    }
}
