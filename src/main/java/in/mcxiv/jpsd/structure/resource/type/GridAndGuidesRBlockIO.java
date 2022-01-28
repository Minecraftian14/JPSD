package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.GridAndGuidesRBlock;
import in.mcxiv.jpsd.data.resource.types.GridAndGuidesRBlock.GuideSubBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class GridAndGuidesRBlockIO extends ImageResourceBlockIO<GridAndGuidesRBlock> {

    public GridAndGuidesRBlockIO() {
        super(false);
    }

    @Override
    public GridAndGuidesRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        int version = reader.stream.readInt();
        int horizontal = reader.stream.readInt();
        int vertical = reader.stream.readInt();
        int fGuideCount = reader.stream.readInt();

        GuideSubBlock[] blocks = new GuideSubBlock[fGuideCount];

        for (int i = 0; i < fGuideCount; i++) {
            int location = reader.stream.readInt();
            boolean direction = reader.stream.readBoolean();
            blocks[i] = new GuideSubBlock(location, direction);
        }

        return new GridAndGuidesRBlock(id, pascalString, blockLength, version, horizontal, vertical, blocks);
    }

    @Override
    public void write(DataWriter writer, GridAndGuidesRBlock data) throws IOException {
        writer.stream.writeInt(data.getVersion());
        writer.stream.writeInt(data.getHorizontal());
        writer.stream.writeInt(data.getVertical());
        writer.stream.writeInt(data.getGuideCount());

        for (GuideSubBlock block : data.getBlocks()) {
            writer.stream.writeInt(block.getLocation());
            writer.stream.writeBoolean(block.getDirection());
        }
    }
}
