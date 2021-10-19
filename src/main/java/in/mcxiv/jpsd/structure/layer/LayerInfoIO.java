package in.mcxiv.jpsd.structure.layer;

import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.layer.info.LayerRecordIO;

import java.io.IOException;

public class LayerInfoIO extends SectionIO<LayerInfo> {

    private final FileHeaderData.FileVersion version;
    public final SectionIO<LayerRecord> LAYER_RECORD_IO;

    public LayerInfoIO(FileHeaderData.FileVersion version) {
        super(true);
        this.version = version;
        LAYER_RECORD_IO = new LayerRecordIO(version);
    }

    @Override
    public LayerInfo read(DataReader reader) throws IOException {

        long length;
        if(version.isLarge()) length = reader.stream.readLong();
        else length = reader.stream.readInt();

        if (length % 2 != 0)
            throw new RuntimeException("Hey MCXIV, shouldn't this be an even number? ((rounded up to a multiple of 2))");

        if (length == 0)
            return null;

        short layers = reader.stream.readShort();
        if (layers < 0) {
            System.err.println("Hmm... Why is number of layers negative?");
            layers *= -1;
        }

        LayerRecord[] recordList = new LayerRecord[layers];
        ChannelImageData[] channelImageDataList = new ChannelImageData[layers];

        for (int i = 0; i < layers; i++)
            recordList[i] = LAYER_RECORD_IO.read(reader);
        for (int i = 0; i < layers; i++) {
            for (ChannelInfo channelInfo : recordList[i].getChannelInfo()) {
                Compression compression = Compression.of(reader.stream.readShort());
                byte[] data = reader.readBytes((int) (channelInfo.getDataLength() - 2), true);//TODO, was -2 good?
                channelInfo.setData(new ChannelImageData(compression, data));
            }
        }

        return new LayerInfo(recordList, channelImageDataList);
    }

    @Override
    public void write(DataWriter writer, LayerInfo layerInfo) {

    }
}
