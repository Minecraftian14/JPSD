package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;

public class UnicodeLayerName extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.UNICODE_LAYER_NAME_KEY;

    private String name;

    public UnicodeLayerName(String name, long length) {
        super(KEY, length);
        this.name = name;
    }

    @Override
    public String toString() {
        return "UnicodeLayerName{" +
                "key=" + key +
                ", name='" + name + '\'' +
                '}';
    }
}
