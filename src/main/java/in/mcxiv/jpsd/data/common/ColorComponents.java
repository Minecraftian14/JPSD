package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.SamplersResourceSubBlock.ColorSpace;

public class ColorComponents extends DataObject {

    protected ColorSpace colorSpace;
    protected short colorComponent1;
    protected short colorComponent2;
    protected short colorComponent3;
    protected short colorComponent4;

    public ColorComponents(ColorSpace colorSpace, short colorComponent1, short colorComponent2, short colorComponent3, short colorComponent4) {
        this.colorSpace = colorSpace;
        this.colorComponent1 = colorComponent1;
        this.colorComponent2 = colorComponent2;
        this.colorComponent3 = colorComponent3;
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
