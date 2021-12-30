package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.VersionInfoRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class VersionInfoRBlockIO extends ImageResourceBlockIO<VersionInfoRBlock> {

    public VersionInfoRBlockIO() {
        super(false);
    }

    @Override
    public VersionInfoRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        //@formatter:off
        int     version       = reader.stream.readInt          ();
        boolean hasMergedData = reader.stream.readBoolean      ();
        String  readerName    = reader       .readUnicodeString();
        String  writerName    = reader       .readUnicodeString();
        int     fileVersion   = reader.stream.readInt          ();
        //@formatter:on

        return new VersionInfoRBlock(id, pascalString, blockLength, version, hasMergedData, readerName, writerName, fileVersion);
    }

    @Override
    public void write(DataWriter writer, VersionInfoRBlock data) throws IOException {
        writer.writeInt(data.getVersion());
        writer.writeByteBoolean(data.hasMergedData());
        writer.writeUnicodeString(data.getReaderName());
        writer.writeUnicodeString(data.getWriterName());
        writer.writeInt(data.getFileVersion());
    }
}
