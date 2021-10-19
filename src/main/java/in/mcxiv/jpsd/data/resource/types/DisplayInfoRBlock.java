package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class DisplayInfoRBlock extends ImageResourceBlock {

    private ColorComponents color;
    private short opacity;
    byte kind; // If kind==0 'selected' else if kind==1 'protected'

    public DisplayInfoRBlock(String pascalString, long length, ColorComponents color, short opacity, byte kind) {
        super(ImageResourceID.DisplayInfo, pascalString, length);
        this.color = color;
        this.opacity = opacity;
        this.kind = kind;
    }
}
