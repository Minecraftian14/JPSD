package in.mcxiv.jpsd.structure.resource;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;

import java.io.IOException;

public abstract class ImageResourceBlockIO<BlockData extends ImageResourceBlock> {

    private final boolean hasVariableLength;

    public ImageResourceBlockIO(boolean hasVariableLength) {
        this.hasVariableLength = hasVariableLength;
    }

    public abstract BlockData read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException;

    public abstract void write(DataWriter writer, BlockData data);

    @SuppressWarnings("unchecked")
    public final void write(DataWriter writer, Object data) {
        if (!(data instanceof ImageResourceBlock))
            throw new RuntimeException("Cant use a " + data.getClass().getSimpleName() + " on an IRBIO " + getClass().getSimpleName());
        write(writer, (BlockData) data);
    }

    public boolean hasVariableLength() {
        return hasVariableLength;
    }
}
