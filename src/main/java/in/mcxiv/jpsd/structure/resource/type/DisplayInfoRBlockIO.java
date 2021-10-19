package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.DisplayInfoRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.common.ColorComponentsIO;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class DisplayInfoRBlockIO extends ImageResourceBlockIO<DisplayInfoRBlock> {

    public DisplayInfoRBlockIO() {
        super(false);
    }

    @Override
    public DisplayInfoRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        ColorComponents color = ColorComponentsIO.INSTANCE.read(reader);
        short opacity = reader.stream.readShort();
        byte kind = reader.stream.readByte();
        reader.stream.skipBytes(1); // Pad to make even.

        // FIXME: umm? did we read blockLength's worth bytes?
        System.err.println("WHY ARE WE SKIPPING BYTES LIKE THAT?" + (blockLength - 14));
        reader.stream.skipBytes(blockLength - 14);

        return new DisplayInfoRBlock(pascalString, blockLength, color, opacity, kind);
    }

    @Override
    public void write(DataWriter writer, DisplayInfoRBlock displayInfoRBlock) {

    }
}
