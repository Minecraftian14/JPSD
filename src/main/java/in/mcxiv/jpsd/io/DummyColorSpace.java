package in.mcxiv.jpsd.io;

import java.awt.color.ColorSpace;

public class DummyColorSpace extends ColorSpace {

    public DummyColorSpace(int type, int numcomponents) {
        super(type + 2000, numcomponents);
    }

    @Override
    public float[] toRGB(float[] floats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] fromRGB(float[] floats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] toCIEXYZ(float[] floats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] fromCIEXYZ(float[] floats) {
        throw new UnsupportedOperationException();
    }

}
