package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PrintFlagsInfoRBlock extends ImageResourceBlock {

    int version;
    byte crop_marks;
    long bleed_width;
    int bleed_scale;

    public PrintFlagsInfoRBlock(String pascalString, long length, int version, byte crop_marks, long bleed_width, int bleed_scale) {
        super(ImageResourceID.PrintFlagsInfo, pascalString, length);
        this.version = version;
        this.crop_marks = crop_marks;
        this.bleed_width = bleed_width;
        this.bleed_scale = bleed_scale;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte getCropMarks() {
        return crop_marks;
    }

    public void setCropMarks(byte crop_marks) {
        this.crop_marks = crop_marks;
    }

    public long getBleedWidth() {
        return bleed_width;
    }

    public void setBleedWidth(long bleed_width) {
        this.bleed_width = bleed_width;
    }

    public int getBleedScale() {
        return bleed_scale;
    }

    public void setBleedScale(int bleed_scale) {
        this.bleed_scale = bleed_scale;
    }
}
