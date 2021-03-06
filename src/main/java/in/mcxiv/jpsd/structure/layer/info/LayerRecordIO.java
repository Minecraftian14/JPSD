package in.mcxiv.jpsd.structure.layer.info;

import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.Clipping;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.layer.info.record.LayerBlendingRanges;
import in.mcxiv.jpsd.data.layer.info.record.LayerMaskData;
import in.mcxiv.jpsd.data.layer.info.record.LayerRecordInfoFlag;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDConnection;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.addend.AdditionalLayerInfoIO;
import in.mcxiv.jpsd.structure.common.RectangleIO;
import in.mcxiv.jpsd.structure.layer.info.record.LayerBlendingRangesIO;
import in.mcxiv.jpsd.structure.layer.info.record.LayerMaskDataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LayerRecordIO extends SectionIO<LayerRecord> {

    public static final SectionIO<LayerMaskData> LAYER_MASK_DATA_IO = new LayerMaskDataIO();
    public static final SectionIO<LayerBlendingRanges> LAYER_BLENDING_RANGES_IO = new LayerBlendingRangesIO();
    public final SectionIO<AdditionalLayerInfo> ADDITIONAL_LAYER_RECORD_INFO;

    public final FileVersion version;

    public LayerRecordIO(FileVersion version) {
        super(true);
        this.version = version;
        ADDITIONAL_LAYER_RECORD_INFO = new AdditionalLayerInfoIO(this.version);
    }

    @Override
    public LayerRecord read(DataReader reader) throws IOException {

        Rectangle content = RectangleIO.INSTANCE.read(reader);
        short numberOfChannels = reader.stream.readShort();

        ChannelInfo[] info = new ChannelInfo[numberOfChannels];

        for (int i = 0; i < numberOfChannels; i++) {
            ChannelInfo.ChannelID id = ChannelInfo.ChannelID.of(reader.stream.readShort());
            long info_length;
            if (version.isLarge()) info_length = reader.stream.readLong();
            else info_length = reader.stream.readInt();
            info[i] = new ChannelInfo(id, info_length);
        }

        reader.verifySignature(PSDConnection.RESOURCE);
        BlendingMode blendingMode = BlendingMode.of(reader.readBytes(4, true));

        byte opacity = reader.stream.readByte();
        Clipping clipping = Clipping.of(reader.stream.readByte());
        LayerRecordInfoFlag layerRecordInfoFlag = new LayerRecordInfoFlag(reader.stream.readByte());
        byte filler = reader.stream.readByte();

        long fieldsLength = reader.stream.readInt(); // Total length of upcoming 5 fields
        long expectedEnd = reader.stream.getStreamPosition() + fieldsLength;

        LayerMaskData layerMaskData = LAYER_MASK_DATA_IO.read(reader);
        LayerBlendingRanges layerBlendingRanges = LAYER_BLENDING_RANGES_IO.read(reader);
        String layerName = reader.readPascalStringPaddedTo4();
        ArrayList<AdditionalLayerInfo> additionalLayerInfos = new ArrayList<>();

        while (reader.stream.getStreamPosition() < expectedEnd) {
            AdditionalLayerInfo additionalLayerInfo = ADDITIONAL_LAYER_RECORD_INFO.read(reader);
            if (additionalLayerInfo != null)
                additionalLayerInfos.add(additionalLayerInfo);
        }

        return new LayerRecord(content, new ArrayList<>(Arrays.asList(info)), blendingMode, opacity, clipping, layerRecordInfoFlag, filler, layerMaskData, layerBlendingRanges, layerName, additionalLayerInfos);
    }

    @Override
    public void write(DataWriter writer, LayerRecord layerRecord) throws IOException {

        RectangleIO.INSTANCE.write(writer, layerRecord.getContent());

        ArrayList<ChannelInfo> info = layerRecord.getInfo();
        short numberOfChannels = (short) info.size();

        writer.stream.writeShort(numberOfChannels);

        for (int i = 0; i < numberOfChannels; i++) {
            writer.stream.writeShort(info.get(i).getId().getValue());
            if (version.isLarge()) writer.stream.writeLong(info.get(i).getDataLength());
            else writer.stream.writeInt((int) info.get(i).getDataLength());
        }

        writer.sign(PSDConnection.RESOURCE);
        writer.writeEntry(layerRecord.getBlendingMode());

        writer.writeByte(layerRecord.getOpacity());
        writer.writeEntry(layerRecord.getClipping());
        writer.writeEntry(layerRecord.getLayerRecordInfoFlag());
        writer.writeByte(layerRecord.getFiller());

        DataWriter buffer = new DataWriter();

        LAYER_MASK_DATA_IO.write(buffer, layerRecord.getLayerMaskData());
        LAYER_BLENDING_RANGES_IO.write(buffer, layerRecord.getLayerBlendingRanges());
        buffer.writePascalStringPaddedTo4(layerRecord.getLayerName());

        for (AdditionalLayerInfo alis : layerRecord.getAdditionalLayerInfos()) {
            ADDITIONAL_LAYER_RECORD_INFO.write(buffer, alis);
        }

        byte[] bytes = buffer.toByteArray();
        writer.stream.writeInt(bytes.length);
        writer.writeBytes(bytes);
    }
}
