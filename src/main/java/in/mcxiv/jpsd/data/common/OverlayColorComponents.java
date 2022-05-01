package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.SamplersResourceSubBlock.ColorSpace;

public class OverlayColorComponents extends DataObject {

    protected short colorSpace;
    protected short colorComponent1;
    protected short colorComponent2;
    protected short colorComponent3;
    protected short colorComponent4;

    public OverlayColorComponents(short colorSpace, short colorComponent1, short colorComponent2, short colorComponent3, short colorComponent4) {
        this.colorSpace = colorSpace;
        this.colorComponent1 = colorComponent1;
        this.colorComponent2 = colorComponent2;
        this.colorComponent3 = colorComponent3;
        this.colorComponent4 = colorComponent4;
    }

    public OverlayColorComponents(int colorSpace, int colorComponent1, int colorComponent2, int colorComponent3, int colorComponent4) {
        this((short) colorSpace, (short) colorComponent1, (short) colorComponent2, (short) colorComponent3, (short) colorComponent4);
    }

    public short getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(short colorSpace) {
        this.colorSpace = colorSpace;
    }

    public short getColorComponent1() {
        return colorComponent1;
    }

    public void setColorComponent1(short colorComponent1) {
        this.colorComponent1 = colorComponent1;
    }

    public short getColorComponent2() {
        return colorComponent2;
    }

    public void setColorComponent2(short colorComponent2) {
        this.colorComponent2 = colorComponent2;
    }

    public short getColorComponent3() {
        return colorComponent3;
    }

    public void setColorComponent3(short colorComponent3) {
        this.colorComponent3 = colorComponent3;
    }

    public short getColorComponent4() {
        return colorComponent4;
    }

    public void setColorComponent4(short colorComponent4) {
        this.colorComponent4 = colorComponent4;
    }

    @Override
    public String toString() {
        return "ColorComponents{" +
               "colorSpace=" + colorSpace +
               ", colorComponent1=" + colorComponent1 +
               ", colorComponent2=" + colorComponent2 +
               ", colorComponent3=" + colorComponent3 +
               ", colorComponent4=" + colorComponent4 +
               '}';
    }

}
