package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.Arrays;

public class GridAndGuidesRBlock extends ImageResourceBlock {

    public static class GuideSubBlock {
        private int location;
        private boolean direction; // false->vertical, true->horizontal

        public int getLocation() {
            return location;
        }

        public boolean getDirection() {
            return direction;
        }

        public GuideSubBlock(int location, boolean direction) {
            this.location = location;
            this.direction = direction;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int version;
    private int horizontal;
    private int vertical;

    private GuideSubBlock[] blocks;

    public GridAndGuidesRBlock(ImageResourceID identity, String pascalString, long length, int version, int horizontal, int vertical, GuideSubBlock[] blocks) {
        super(identity, pascalString, length);

        if (version != 1)
            throw new IllegalArgumentException("Version has to be 1! Don't ask me why... Provided " + version);

        this.version = version;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.blocks = blocks == null ? new GuideSubBlock[0] : blocks;
    }

    public int getGuideCount() {
        return blocks.length;
    }

    public int getVersion() {
        return version;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public GuideSubBlock[] getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "GridAndGuidesRBlock{" +
                "version=" + version +
                ", horizontal=" + horizontal +
                ", vertical=" + vertical +
                ", fGuideCount=" + blocks.length +
                ", blocks=" + Arrays.toString(blocks) +
                '}';
    }
}
