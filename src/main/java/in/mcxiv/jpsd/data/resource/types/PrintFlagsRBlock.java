package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PrintFlagsRBlock extends ImageResourceBlock {

    private boolean labels;
    private boolean crop_masks;
    private boolean color_bars;
    private boolean registration_marks;
    private boolean negative;
    private boolean flip;
    private boolean interpolate;
    private boolean caption;
    private boolean print_flags;

    public PrintFlagsRBlock(ImageResourceID identity, String pascalString, long length, boolean labels, boolean crop_masks, boolean color_bars, boolean registration_marks, boolean negative, boolean flip, boolean interpolate, boolean caption, boolean print_flags) {
        super(identity, pascalString, length);
        this.labels = labels;
        this.crop_masks = crop_masks;
        this.color_bars = color_bars;
        this.registration_marks = registration_marks;
        this.negative = negative;
        this.flip = flip;
        this.interpolate = interpolate;
        this.caption = caption;
        this.print_flags = print_flags;
    }
}
