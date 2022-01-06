package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PixelAspectRatioRBlock extends ImageResourceBlock {

    private int version;
    private double ratio;

    public PixelAspectRatioRBlock(String pascalString, long length, int version, double ratio) {
        super(ImageResourceID.PixelAspectRatio, pascalString, length);
        this.version = version;
        this.ratio = ratio;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
