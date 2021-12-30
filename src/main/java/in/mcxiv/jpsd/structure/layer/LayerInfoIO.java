package in.mcxiv.jpsd.structure.layer;

import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.layer.info.LayerRecordIO;

import java.io.IOException;

public class LayerInfoIO extends SectionIO<LayerInfo> {

    private final FileVersion version;
    public final SectionIO<LayerRecord> LAYER_RECORD_IO;

    public LayerInfoIO(FileVersion version) {
        super(true);
        this.version = version;
        LAYER_RECORD_IO = new LayerRecordIO(version);
    }

    @Override
    public LayerInfo read(DataReader reader) throws IOException {

        long length;
        if (version.isLarge()) length = reader.stream.readLong();
        else length = reader.stream.readInt();

        return read(reader, length);

    }

    public LayerInfo read(DataReader reader, long length) throws IOException {

        if (length % 2 != 0)
            throw new RuntimeException("Hey MCXIV, shouldn't this be an even number? ((rounded up to a multiple of 2))");

        if (length == 0)
            return null;

        short layers = reader.stream.readShort();
        boolean hasAlpha = false;
        if (layers < 0) {
            layers *= -1;
            hasAlpha = true;
        }

        LayerRecord[] recordList = new LayerRecord[layers];

        for (int i = 0; i < layers; i++)
            recordList[i] = LAYER_RECORD_IO.read(reader);

        for (int i = 0; i < layers; i++) {
            for (ChannelInfo channelInfo : recordList[i].getChannelInfo()) {
                Compression compression = Compression.of(reader.stream.readShort());
                byte[] data = reader.readBytes((int) (channelInfo.getDataLength() - 2), true);
                channelInfo.setData(new ChannelImageData(compression, data));
            }
        }

        return new LayerInfo(hasAlpha, recordList);
    }

    @Override
    public void write(DataWriter writer, LayerInfo layerInfo) throws IOException {

        if (layerInfo == null) {
            if (version.isLarge()) writer.stream.writeLong(0);
            else writer.stream.writeInt(0);
            return;
        }

        DataWriter buffer = new DataWriter();

        LayerRecord[] recordList = layerInfo.getLayerRecords();
        short layers = (short) recordList.length;

        if (layerInfo.hasAlpha()) layers *= -1;

        buffer.stream.writeShort(layers);

        for (int i = 0; i < layers; i++)
            LAYER_RECORD_IO.write(buffer, recordList[i]);

        for (int i = 0; i < layers; i++) {
            for (ChannelInfo channelInfo : recordList[i].getChannelInfo()) {
                buffer.stream.writeShort(channelInfo.getData().getCompression().getValue());
                buffer.writeBytes(channelInfo.getData().getData());
            }
        }

        byte[] bytes = buffer.toByteArray();
        int length = bytes.length;
        boolean writeZero = length % 2 == 1;
        if (writeZero) length++;

        if (version.isLarge()) writer.stream.writeLong(length);
        else writer.stream.writeInt(length);

        writer.writeBytes(bytes);
        if (writeZero) writer.stream.writeByte(0);
    }
}
