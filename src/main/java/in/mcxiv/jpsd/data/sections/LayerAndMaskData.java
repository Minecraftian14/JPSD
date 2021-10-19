package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.data.layer.LayerInfo;

import java.util.Arrays;

public class LayerAndMaskData extends DataObject {

    private LayerInfo layerInfo;
    private GlobalLayerMaskInfo globalLayerMaskInfo;
    private AdditionalLayerInfo[] additionalLayerInfo;

    public LayerAndMaskData(LayerInfo layerInfo, GlobalLayerMaskInfo globalLayerMaskInfo, AdditionalLayerInfo[] additionalLayerInfo) {
        this.layerInfo = layerInfo;
        this.globalLayerMaskInfo = globalLayerMaskInfo;
        this.additionalLayerInfo = additionalLayerInfo;
    }

    @Override
    public String toString() {
        return "LayerAndMaskData{" +
                "layerInfo=" + layerInfo +
                ", globalLayerMaskInfo=" + globalLayerMaskInfo +
                ", additionalLayerInfo=" + Arrays.toString(additionalLayerInfo) +
                '}';
    }
}
