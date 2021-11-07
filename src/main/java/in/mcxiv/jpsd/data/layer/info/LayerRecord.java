package in.mcxiv.jpsd.data.layer.info;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.Clipping;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.layer.info.record.LayerBlendingRanges;
import in.mcxiv.jpsd.data.layer.info.record.LayerMaskData;
import in.mcxiv.jpsd.data.layer.info.record.LayerRecordInfoFlag;
import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.ImageData;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class LayerRecord extends DataObject {

    /**
     * (lef, top) as point -> coords of top-left point of this layer's bounding box.  <br />
     * (rig, bot) as point -> coords of bot-right point of this layer's bounding box. <br />
     * where (0, 0) as point -> top-left point of base layer.
     */
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

    public int getChannelsCount() {
        return info.length;
    }

    /**
     * @return the number of {@link ChannelInfo} which contains data regarding color, including transparency.
     * In other words, it doesn't count channels which provide mask realted data.
     */
    public int getColorChannelsCount() {
        int count = 0;
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().isColor())
                count++;
        return count;
    }

    public ChannelInfo[] getInfo() {
        return info;
    }

    public ChannelInfo getInfo(int id) {
        return getInfo(ChannelInfo.ChannelID.of((short) id));
    }

    public ChannelInfo getInfo(ChannelInfo.ChannelID id) {
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().equals(id))
                return channelInfo;
        throw new NoSuchElementException("No channel present with the id "+id);
    }

    public BlendingMode getBlendingMode() {
        return blendingMode;
    }

    public byte getOpacity() {
        return opacity;
    }

    public Clipping getClipping() {
        return clipping;
    }

    public LayerRecordInfoFlag getLayerRecordInfoFlag() {
        return layerRecordInfoFlag;
    }

    public byte getFiller() {
        return filler;
    }

    public LayerMaskData getLayerMaskData() {
        return layerMaskData;
    }

    public LayerBlendingRanges getLayerBlendingRanges() {
        return layerBlendingRanges;
    }

    public String getLayerName() {
        return layerName;
    }

    public AdditionalLayerInfo[] getAdditionalLayerInfos() {
        return additionalLayerInfos;
    }

    public int getWidth() {
        return content.getRig() - content.getLef();
    }

    public int getHeight() {
        return content.getBot() - content.getTop();
    }

    public boolean hasAlpha() {
        return getColorChannelsCount() > 3;
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
