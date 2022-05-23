package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.layer.LayerInfo;

public class LayerAndMaskInfo extends AdditionalLayerInfo {

    private LayerInfo layerInfo;

    public LayerAndMaskInfo(AdditionalInfoKey key, LayerInfo layerInfo) {
        this(key, -1, layerInfo);
    }

    public LayerAndMaskInfo(AdditionalInfoKey key, long length, LayerInfo layerInfo) {
        super(key, length);

        if (key != AdditionalInfoKey.LAYER_AND_MASK_INFO_16 && key != AdditionalInfoKey.LAYER_AND_MASK_INFO)
            throw new IllegalArgumentException();

        this.layerInfo = layerInfo;
    }

    @Override
    public String toString() {
        return "LayerAndMaskInfo{" +
               "key=" + key +
               ", layerInfo=" + layerInfo +
               '}';
    }

    public LayerInfo getLayerInfo() {
        return layerInfo;
    }
}
