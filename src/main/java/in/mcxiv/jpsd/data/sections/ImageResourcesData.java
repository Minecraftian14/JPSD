package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.GridAndGuidesRBlock;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock;
import in.mcxiv.jpsd.data.resource.types.VersionInfoRBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageResourcesData extends DataObject {

    private List<ImageResourceBlock> blocks = new ArrayList<>();

    public ImageResourcesData(ImageResourceBlock... blocks) {
        this.blocks.addAll(Arrays.asList(blocks));
        if (this.blocks.stream().noneMatch(irb -> irb.getIdentity().equals(ImageResourceID.Resolution)))
            this.blocks.add(new ResolutionRBlock(ImageResourceID.Resolution, "", -1, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches));
        if (this.blocks.stream().noneMatch(irb -> irb.getIdentity().equals(ImageResourceID.GridAndGuides)))
            this.blocks.add(new GridAndGuidesRBlock(ImageResourceID.GridAndGuides, "", -1, 1, 576, 576, null));
        if (this.blocks.stream().noneMatch(irb -> irb.getIdentity().equals(ImageResourceID.VersionInfo)))
            this.blocks.add(new VersionInfoRBlock(ImageResourceID.VersionInfo, "", -1, 1, true, "JPSD", "JPSD", 1));
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
