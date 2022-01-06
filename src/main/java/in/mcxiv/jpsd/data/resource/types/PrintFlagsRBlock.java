package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PrintFlagsRBlock extends ImageResourceBlock {

    private boolean labels;
    private boolean cropMasks;
    private boolean colorBars;
    private boolean registrationMarks;
    private boolean negative;
    private boolean flip;
    private boolean interpolate;
    private boolean caption;
    private boolean printFlags;

    public PrintFlagsRBlock(ImageResourceID identity, String pascalString, long length, boolean labels, boolean crop_masks, boolean color_bars, boolean registration_marks, boolean negative, boolean flip, boolean interpolate, boolean caption, boolean print_flags) {
        super(identity, pascalString, length);
        this.labels = labels;
        this.cropMasks = crop_masks;
        this.colorBars = color_bars;
        this.registrationMarks = registration_marks;
        this.negative = negative;
        this.flip = flip;
        this.interpolate = interpolate;
        this.caption = caption;
        this.printFlags = print_flags;
    }

    public boolean isLabels() {
        return labels;
    }

    public void setLabels(boolean labels) {
        this.labels = labels;
    }

    public boolean isCropMasks() {
        return cropMasks;
    }

    public void setCropMasks(boolean cropMasks) {
        this.cropMasks = cropMasks;
    }

    public boolean isColorBars() {
        return colorBars;
    }

    public void setColorBars(boolean colorBars) {
        this.colorBars = colorBars;
    }

    public boolean isRegistrationMarks() {
        return registrationMarks;
    }

    public void setRegistrationMarks(boolean registrationMarks) {
        this.registrationMarks = registrationMarks;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isInterpolate() {
        return interpolate;
    }

    public void setInterpolate(boolean interpolate) {
        this.interpolate = interpolate;
    }

    public boolean isCaption() {
        return caption;
    }

    public void setCaption(boolean caption) {
        this.caption = caption;
    }

    public boolean isPrintFlags() {
        return printFlags;
    }

    public void setPrintFlags(boolean printFlags) {
        this.printFlags = printFlags;
    }
}
