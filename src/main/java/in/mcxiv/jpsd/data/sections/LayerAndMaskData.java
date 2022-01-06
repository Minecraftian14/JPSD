package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.LayerAndMaskInfo;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.io.PSDFileReader;

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

    public LayerInfo getLayerInfo() {
        return layerInfo;
    }

    public GlobalLayerMaskInfo getGlobalLayerMaskInfo() {
        return globalLayerMaskInfo;
    }

    public AdditionalLayerInfo[] getAdditionalLayerInfo() {
        return additionalLayerInfo;
    }

    public LayerInfo justGetALayerInfo() {
        if (layerInfo != null) return layerInfo;
        for (AdditionalLayerInfo info : additionalLayerInfo)
            if (info instanceof LayerAndMaskInfo)
                return ((LayerAndMaskInfo) info).getLayerInfo();
        if (PSDFileReader.unknownBytesStrategy.action.equals(PSDFileReader.UnknownBytesStrategy.Action.Quit))
            throw new RuntimeException("No layer info found!");
            return null;
    }

}
