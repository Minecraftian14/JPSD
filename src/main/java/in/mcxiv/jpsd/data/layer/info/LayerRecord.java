package in.mcxiv.jpsd.data.layer.info;

import in.mcxiv.jpsd.PSDDocument;
import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.UnicodeLayerName;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.Clipping;
import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.layer.info.record.LayerBlendingRanges;
import in.mcxiv.jpsd.data.layer.info.record.LayerMaskData;
import in.mcxiv.jpsd.data.layer.info.record.LayerRecordInfoFlag;
import in.mcxiv.jpsd.data.layer.info.record.mask.LayerMaskInfoFlag;
import in.mcxiv.jpsd.io.ImageMakerStudio;
import in.mcxiv.jpsd.io.PSDConnection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LayerRecord extends DataObject {

    /**
     * (lef, top) as point -> coords of top-left point of this layer's bounding box.  <br />
     * (rig, bot) as point -> coords of bot-right point of this layer's bounding box. <br />
     * where (0, 0) as point -> top-left point of base layer.
     */
    private Rectangle content;
    private ArrayList<ChannelInfo> info; // info.length == numberOfChannels
    private BlendingMode blendingMode;
    private byte opacity;
    private Clipping clipping;
    private LayerRecordInfoFlag layerRecordInfoFlag;
    private byte filler;
    private LayerMaskData layerMaskData;
    private LayerBlendingRanges layerBlendingRanges;
    private String layerName;
    private ArrayList<AdditionalLayerInfo> additionalLayerInfos;

    public static byte[] extractChannel(int dta, BufferedImage image) {
        if (!(image.getRaster().getDataBuffer() instanceof DataBufferInt)) return __extractChannelBackup(dta, image);
        int offset = dta * 8, mask = 255 << offset;
        int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        byte[] chl = new byte[data.length];
        for (int i = 0; i < chl.length; i++)
            chl[i] = (byte) ((data[i] & mask) >> offset);
        return chl;
    }

    private static byte[] __extractChannelBackup(int dta, BufferedImage image) {
        int offset = dta * 8, mask = 255 << offset;
        byte[] chl = new byte[image.getWidth() * image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++)
            for (int j = 0; j < image.getHeight(); j++)
                chl[i + j * image.getWidth()] = (byte) ((image.getRGB(i, j) & mask) >> offset);
        return chl;
    }

    public static ArrayList<ChannelInfo> createDefaultChannelInfo(BufferedImage image) {
        ArrayList<ChannelInfo> list = new ArrayList<>();
        list.add(new ChannelInfo(ChannelInfo.ChannelID.Channel1, new ChannelImageData(Compression.Raw_Data, extractChannel(2, image))));
        list.add(new ChannelInfo(ChannelInfo.ChannelID.Channel2, new ChannelImageData(Compression.Raw_Data, extractChannel(1, image))));
        list.add(new ChannelInfo(ChannelInfo.ChannelID.Channel3, new ChannelImageData(Compression.Raw_Data, extractChannel(0, image))));
        return list;
    }

    public static ArrayList<ChannelInfo> createChannelInfo(BufferedImage image) {
        ArrayList<ChannelInfo> list = createDefaultChannelInfo(image);
        if (image.getTransparency() == Transparency.OPAQUE) return list;
        list.add(0, new ChannelInfo(ChannelInfo.ChannelID.TransparencyMask, new ChannelImageData(Compression.Raw_Data, extractChannel(3, image))));
        return list;
    }

    public static ArrayList<ChannelInfo> createChannelInfo(BufferedImage image, BufferedImage mask) {
        ArrayList<ChannelInfo> list = createChannelInfo(image);
        list.add(new ChannelInfo(ChannelInfo.ChannelID.UserSuppliedMask, new ChannelImageData(Compression.Raw_Data, extractChannel(0, mask))));
        return list;
    }

    public LayerRecord(int topLefX, int topLefY, String layerName, BufferedImage image) {
        this(topLefX, topLefY, topLefX + image.getWidth(), topLefY + image.getHeight(), layerName, image);
    }

    public LayerRecord(int topLefX, int topLefY, int botRhtX, int botRhtY, String layerName, BufferedImage image) {
        this(
                new Rectangle(topLefY, topLefX, botRhtY, botRhtX),
                createChannelInfo(image),
                BlendingMode.NORMAL,
                (byte) -1,
                Clipping.Base,
                new LayerRecordInfoFlag(LayerRecordInfoFlag.HAS_FOURTH),
                (byte) 0,
                null,
                LayerBlendingRanges.DEFAULT,
                layerName,
                new ArrayList<>()
        );
    }

    public LayerRecord(int topLefX, int topLefY, int botRhtX, int botRhtY, String layerName, BufferedImage image, BufferedImage mask) {
        this(
                new Rectangle(topLefY, topLefX, botRhtY, botRhtX),
                createChannelInfo(image, mask),
                BlendingMode.NORMAL,
                (byte) -1,
                Clipping.Base,
                new LayerRecordInfoFlag(LayerRecordInfoFlag.HAS_FOURTH),
                (byte) 0,
                null,
                LayerBlendingRanges.DEFAULT,
                layerName,
                new ArrayList<>()
        );
    }

    /**
     * A Layer Record is a collection of data representing a single layer in PS.
     *
     * @param content              A Rectangle defining the position of this Layer's contents. The origin is kept at Top-Left corner of the background layer.
     * @param info                 An array of channel information, each element representing one color component.
     * @param blendingMode         The way how an image blends with lower layers. Check out all the possible blends in {@link BlendingMode}.
     * @param opacity              A layer specific record of transparency.
     * @param clipping             Default value is {@link Clipping}.Base. //TODO
     * @param layerRecordInfoFlag  A BitDataEntry containing few bits of information regarding {@link LayerRecordInfoFlag}.
     * @param filler               This probably is just a filler to make bytes even.
     * @param layerMaskData        Information regarding how masks are placed on the layer.
     * @param layerBlendingRanges  Default value is {@link LayerBlendingRanges}.DEFAULT. //TODO
     * @param layerName            Name of this layer.
     * @param additionalLayerInfos Additional layer information blocks. Refer subclasses of {@link AdditionalLayerInfo}.
     */
    public LayerRecord(Rectangle content, ArrayList<ChannelInfo> info, BlendingMode blendingMode, byte opacity, Clipping clipping, LayerRecordInfoFlag layerRecordInfoFlag, byte filler, LayerMaskData layerMaskData, LayerBlendingRanges layerBlendingRanges, String layerName, ArrayList<AdditionalLayerInfo> additionalLayerInfos) {
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
        this.additionalLayerInfos.add(new UnicodeLayerName(layerName, 0));
    }

    public void setContent(Rectangle content) {
        this.content = content;
    }

    public void setBlendingMode(BlendingMode blendingMode) {
        this.blendingMode = blendingMode;
    }

//    public void setOpacity(int opacity) {
//        if (opacity < 0 || opacity > 100)
//            throw new IllegalArgumentException("Opacity must be in the range [0, 255]");
//        setOpacity((byte) (opacity * 255 / 100d));
//    }

    public void setOpacity(float opacity) {
        if (opacity < 0 || opacity > 1)
            throw new IllegalArgumentException("Opacity must be in the range [0, 1]");
        setOpacity((byte) (opacity * 255));
    }

    public void setOpacity(byte opacity) {
        this.opacity = opacity;
    }

    public void setClipping(Clipping clipping) {
        this.clipping = clipping;
    }

    public void setLayerRecordInfoFlag(LayerRecordInfoFlag layerRecordInfoFlag) {
        this.layerRecordInfoFlag = layerRecordInfoFlag;
    }

    public void setFiller(byte filler) {
        this.filler = filler;
    }

    public void setLayerMaskData(LayerMaskData layerMaskData) {
        this.layerMaskData = layerMaskData;
    }

    public void setLayerBlendingRanges(LayerBlendingRanges layerBlendingRanges) {
        this.layerBlendingRanges = layerBlendingRanges;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public Rectangle getContent() {
        return content;
    }

    public ArrayList<ChannelInfo> getChannelInfo() {
        return info;
    }

    public int getChannelsCount() {
        return info.size();
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

    public ArrayList<ChannelInfo> getInfo() {
        return info;
    }

    public ChannelInfo getInfo(int id) {
        return getInfo(ChannelInfo.ChannelID.of((short) id));
    }

    public boolean hasInfo(ChannelInfo.ChannelID id) {
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().equals(id))
                return true;
        return false;
    }

    public ChannelInfo getInfo(ChannelInfo.ChannelID id) {
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().equals(id))
                return channelInfo;
        throw new NoSuchElementException("No channel present with the id " + id);
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

    public ArrayList<AdditionalLayerInfo> getAdditionalLayerInfos() {
        return additionalLayerInfos;
    }

    public BufferedImage getImage(PSDDocument document) {
        return getImage(document.getConnection());
    }

    public BufferedImage getImage(PSDConnection connection) {
        return ImageMakerStudio.toImage(this, connection);
    }

    public boolean hasMask() {
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().equals(ChannelInfo.ChannelID.UserSuppliedMask))
                return true;
        return false;
    }

    public void setMask(BufferedImage mask) {
        info.removeIf(channelInfo -> channelInfo.getId().equals(ChannelInfo.ChannelID.UserSuppliedMask));
        info.add(new ChannelInfo(ChannelInfo.ChannelID.UserSuppliedMask, new ChannelImageData(Compression.Raw_Data, extractChannel(0, mask))));
    }

    public ChannelInfo getMask() {
        for (ChannelInfo channelInfo : info)
            if (channelInfo.getId().equals(ChannelInfo.ChannelID.UserSuppliedMask))
                return channelInfo;
        return null;
    }

    public BufferedImage getMaskImage(PSDDocument document) {
        return getMaskImage(document.getConnection());
    }

    public BufferedImage getMaskImage(PSDConnection connection) {
        if (hasMask())
            return ImageMakerStudio.toImageMask(this, connection);
        return null;
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

    public void setLayerVisible(boolean value) {
        getLayerRecordInfoFlag().set(value, LayerRecordInfoFlag.VISIBLE);
    }

    public void setInvertMask(boolean value) {
        getLayerMaskData().getLayerMaskInfoFlag().set(value, LayerMaskInfoFlag.INVERT_LAYER_MASK);
    }

    public void setMaskDisabled(boolean value) {
        getLayerMaskData().getLayerMaskInfoFlag().set(value, LayerMaskInfoFlag.LAYER_MASK_DISABLED);
    }

    public void setMaskPositionRelativeToLayer(boolean value) {
        getLayerMaskData().getLayerMaskInfoFlag().set(value, LayerMaskInfoFlag.POS_RELATIVE_TO_LAYER);
    }

    @Override
    public String toString() {
        return "LayerRecord{" +
                "content=" + content +
                ", info=" + info +
                ", blendingMode=" + blendingMode +
                ", opacity=" + opacity +
                ", clipping=" + clipping +
                ", layerRecordInfoFlag=" + layerRecordInfoFlag +
                ", filler=" + filler +
                ", layerMaskData=" + layerMaskData +
                ", layerBlendingRanges=" + layerBlendingRanges +
                ", layerName='" + layerName + '\'' +
                ", additionalLayerInfos=" + additionalLayerInfos +
                '}';
    }
}
