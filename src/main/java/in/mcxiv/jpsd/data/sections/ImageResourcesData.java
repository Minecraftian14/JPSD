package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageResourcesData extends DataObject {

    private List<ImageResourceBlock> blocks = new ArrayList<>();

    public ImageResourcesData(ImageResourceBlock... blocks) {
        this.blocks.addAll(Arrays.asList(blocks));
    }

    public void addBlock(ImageResourceBlock block) {
        blocks.removeIf(b -> b.getIdentity().equals(block.getIdentity()));
        blocks.add(block);
    }

    public ImageResourceBlock getBlock(ImageResourceID id) {
        for (ImageResourceBlock block : blocks)
            if (block.getIdentity().equals(id))
                return block;
        return null;
    }

    public ImageResourceBlock[] getBlocks() {
        return blocks.toArray(new ImageResourceBlock[0]);
    }

    @Override
    public String toString() {
        return "ImageResourcesData{" +
                "blocks=" + Arrays.toString(getBlocks()) +
                '}';
    }
}
