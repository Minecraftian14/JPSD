package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.DisplayInfoRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
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

        PSDFileReader.out.println("DisplayInfoRBlock is an untested part! There have been doubts about the number of bytes to be read and number of bytes to be skipped (padding).");
        // FIXME: why skip bytes?
        long remainingBytes = blockLength - 14;

        if (remainingBytes > 0) {
            PSDFileReader.out.println("Bytes skipped" + remainingBytes);
            reader.stream.skipBytes(remainingBytes);
        }

        return new DisplayInfoRBlock(pascalString, blockLength, color, opacity, kind);
    }

    @Override
    public void write(DataWriter writer, DisplayInfoRBlock info) throws IOException {
        ColorComponentsIO.INSTANCE.write(writer, info.getColor());
        writer.stream.writeShort(info.getOpacity());
        writer.stream.writeByte(info.getKind());
        writer.fillZeros(1);
    }
}
