package in.mcxiv.jpsd.data.layer.info;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.Clipping;
import in.mcxiv.jpsd.data.layer.info.record.*;
import in.mcxiv.jpsd.data.common.Rectangle;

import java.util.Arrays;

public class LayerRecord extends DataObject {

    public static final String BLENDING_MODE_SIGNATURE = "8BIM";

    private Rectangle content;
    private ChannelInfo[] info; // info.length == numberOfChannels
    private BlendingMode blendingMode;
    private byte opacity;
    private Clipping clipping;
    private LayerRecordInfoFlag layerRecordInfoFlag;
    private byte filler;
    private LayerMaskData layerMaskData;
    private LayerBlendingRanges layerBlendingRanges;
    private String layerName;
    private AdditionalLayerInfo[] additionalLayerInfos;

    public LayerRecord(Rectangle content, ChannelInfo[] info, BlendingMode blendingMode, byte opacity, Clipping clipping, LayerRecordInfoFlag layerRecordInfoFlag, byte filler, LayerMaskData layerMaskData, LayerBlendingRanges layerBlendingRanges, String layerName, AdditionalLayerInfo[] additionalLayerInfos) {
        this.content = content;
        this.info = info;
        this.blendingMode = blendingMode;
        this.opacity = opacity;
        this.clipping = clipping;
        this.layerRecordInfoFlag = layerRecordInfoFlag;
        this.filler = filler;
        this.layerMaskData = layerMaskData;
        this.layerBlendingRanges = layerBlendingRanges;
        this.layerName = layerName;
        this.additionalLayerInfos = additionalLayerInfos;
    }

    public Rectangle getContent() {
        return content;
    }

    public ChannelInfo[] getChannelInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "LayerRecord{" +
                "content=" + content +
                ", info=" + Arrays.toString(info) +
                ", blendingMode=" + blendingMode +
                ", opacity=" + opacity +
                ", clipping=" + clipping +
                ", layerRecordInfoFlag=" + layerRecordInfoFlag +
                ", filler=" + filler +
                ", layerMaskData=" + layerMaskData +
                ", layerBlendingRanges=" + layerBlendingRanges +
                ", layerName='" + layerName + '\'' +
                ", additionalLayerInfos=" + Arrays.toString(additionalLayerInfos) +
                '}';
    }
}
