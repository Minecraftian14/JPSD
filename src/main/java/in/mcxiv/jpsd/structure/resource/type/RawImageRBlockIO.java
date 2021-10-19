package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.RawImageRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class RawImageRBlockIO extends ImageResourceBlockIO<RawImageRBlock> {

    public RawImageRBlockIO() {
        super(true);
    }

    @Override
    public RawImageRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {
        return new RawImageRBlock(id, pascalString, blockLength, reader.readBytes((int) blockLength, true));
    }

    @Override
    public void write(DataWriter writer, RawImageRBlock data) {
        writer.writeBytes(data.getData());
    }
}
