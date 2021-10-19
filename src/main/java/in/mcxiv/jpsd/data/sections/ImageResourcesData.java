package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;

import java.util.Arrays;

public class ImageResourcesData extends DataObject {

    // Image Resources Block
//  private int length;  // We don't keep it because actual data can change, and we need to calculate it again when writing.
//  private byte[] blocksInBytes; // We'll just parse and keep the blocks instead!

    private ImageResourceBlock[] blocks;

    public ImageResourcesData(ImageResourceBlock[] blocks) {
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        return "ImageResourcesData{" +
                "blocks=" + Arrays.toString(blocks) +
                '}';
    }

    public void setBlocks(ImageResourceBlock[] blocks) {
        this.blocks = blocks;
    }

    public ImageResourceBlock[] getBlocks() {
        return blocks;
    }
}
