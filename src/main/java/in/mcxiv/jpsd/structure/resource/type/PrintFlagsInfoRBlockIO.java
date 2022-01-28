package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.PrintFlagsInfoRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class PrintFlagsInfoRBlockIO extends ImageResourceBlockIO<PrintFlagsInfoRBlock> {

    public PrintFlagsInfoRBlockIO() {
        super(false);
    }

    @Override
    public PrintFlagsInfoRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        //@formatter:off
        int  version    = reader.stream.  readUnsignedShort ();
        byte cropMasks  = reader.stream.  readByte          ();
                          reader.stream.  skipBytes         (1);
        long bleedWidth = reader.stream.  readUnsignedInt   ();
        int  bleedScale = reader.stream.  readUnsignedShort ();
        //@formatter:on

        return new PrintFlagsInfoRBlock(pascalString, blockLength, version, cropMasks, bleedWidth, bleedScale);
    }

    @Override
    public void write(DataWriter writer, PrintFlagsInfoRBlock printFlagsInfoRBlock) throws IOException {
        writer.stream.writeInt(printFlagsInfoRBlock.getVersion());
        writer.stream.writeByte(printFlagsInfoRBlock.getCropMarks());
        writer.fillZeros(1);
        writer.stream.writeLong(printFlagsInfoRBlock.getBleedWidth());
        writer.stream.writeInt(printFlagsInfoRBlock.getBleedScale());
    }
}
