package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.LayerAndMaskInfo;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.io.PSDConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LayerAndMaskData extends DataObject {

    private LayerInfo layerInfo;
    private GlobalLayerMaskInfo globalLayerMaskInfo;
    private List<AdditionalLayerInfo> additionalLayerInfo = new ArrayList<>();

    public LayerAndMaskData() {
        this(new LayerInfo(), null, new AdditionalLayerInfo[0]);
    }

    public LayerAndMaskData(LayerInfo layerInfo, GlobalLayerMaskInfo globalLayerMaskInfo, AdditionalLayerInfo... additionalLayerInfo) {
        this.layerInfo = layerInfo;
        this.globalLayerMaskInfo = globalLayerMaskInfo;
        this.additionalLayerInfo.addAll(Arrays.asList(additionalLayerInfo));
    }

    @Override
    public String toString() {
        return "LayerAndMaskData{" +
                "layerInfo=" + layerInfo +
                ", globalLayerMaskInfo=" + globalLayerMaskInfo +
                ", additionalLayerInfo=" + Arrays.toString(getAdditionalLayerInfo()) +
                '}';
    }

    public LayerInfo getLayerInfo() {
        return layerInfo;
    }

    public void setLayerInfo(LayerInfo layerInfo) {
        this.layerInfo = layerInfo;
    }

    public GlobalLayerMaskInfo getGlobalLayerMaskInfo() {
        return globalLayerMaskInfo;
    }

    public void addInfo(AdditionalLayerInfo info) {
        this.additionalLayerInfo.removeIf(i -> i.getKey().equals(info.getKey()));
        this.additionalLayerInfo.add(info);
    }

    public AdditionalLayerInfo getInfo(AdditionalInfoKey id) {
        for (AdditionalLayerInfo block : this.additionalLayerInfo)
            if (block.getKey().equals(id))
                return block;
        return null;
    }

    public AdditionalLayerInfo[] getAdditionalLayerInfo() {
        return additionalLayerInfo.toArray(new AdditionalLayerInfo[0]);
    }

    public LayerInfo justGetALayerInfo() {
        if (layerInfo != null) return layerInfo;
        for (AdditionalLayerInfo info : additionalLayerInfo)
            if (info instanceof LayerAndMaskInfo) {
                LayerAndMaskInfo lami = ((LayerAndMaskInfo) info);
                layerInfo = lami.getLayerInfo();
                additionalLayerInfo.remove(lami);
                return layerInfo;
            }
        if (PSDConnection.unknownBytesStrategy.action.equals(PSDConnection.UnknownBytesStrategy.Action.Quit))
            throw new RuntimeException("No layer info found!");
        return null;
    }

}
