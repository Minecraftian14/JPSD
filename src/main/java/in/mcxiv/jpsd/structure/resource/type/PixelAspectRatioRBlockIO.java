package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.PixelAspectRatioRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class PixelAspectRatioRBlockIO extends ImageResourceBlockIO<PixelAspectRatioRBlock> {

    public PixelAspectRatioRBlockIO() {
        super(false);
    }

    @Override
    public PixelAspectRatioRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        int version = reader.stream.readInt();
        double ratio = reader.stream.readDouble();

        return new PixelAspectRatioRBlock(pascalString, blockLength, version, ratio);
    }

    @Override
    public void write(DataWriter writer, PixelAspectRatioRBlock pixelAspectRatioRBlock) throws IOException {
        writer.stream.writeInt(pixelAspectRatioRBlock.getVersion());
        writer.stream.writeDouble(pixelAspectRatioRBlock.getRatio());
    }
}
