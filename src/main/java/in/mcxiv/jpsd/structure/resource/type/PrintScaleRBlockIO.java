package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.PrintScaleRBlock;
import in.mcxiv.jpsd.data.resource.types.PrintScaleRBlock.Style;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class PrintScaleRBlockIO extends ImageResourceBlockIO<PrintScaleRBlock> {

    public PrintScaleRBlockIO() {
        super(false);
    }

    @Override
    public PrintScaleRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        //@formatter:off
        Style style    = Style.of(reader.stream.readShort ());
        float x_offset = reader.stream.         readFloat ();
        float y_offset = reader.stream.         readFloat ();
        float scale    = reader.stream.         readFloat ();
        //@formatter:on

        return new PrintScaleRBlock(pascalString, blockLength, style, x_offset, y_offset, scale);

    }

    @Override
    public void write(DataWriter writer, PrintScaleRBlock printScaleRBlock) throws IOException {
        writer.writeEntry(printScaleRBlock.getStyle());
        writer.stream.writeFloat(printScaleRBlock.getXOffset());
        writer.stream.writeFloat(printScaleRBlock.getYOffset());
        writer.stream.writeFloat(printScaleRBlock.getScale());
    }
}
