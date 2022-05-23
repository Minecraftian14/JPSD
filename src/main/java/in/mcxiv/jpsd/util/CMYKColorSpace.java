package in.mcxiv.jpsd.util;

import java.awt.color.ColorSpace;

public class CMYKColorSpace extends ColorSpace {

    public static final ColorSpace INSTANCE = new CMYKColorSpace();
    private static final ColorSpace CIEXYZ = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);

    private CMYKColorSpace() {
        super(TYPE_CMYK, 4);
    }

    @Override
    public float[] toRGB(float[] floats) {
        return new float[]{
                (1 - floats[0]) * (1 - floats[3]),
                (1 - floats[1]) * (1 - floats[3]),
                (1 - floats[2]) * (1 - floats[3])
        };
    }

    @Override
    public float[] fromRGB(float[] floats) {
        float k = Math.max(Math.max(floats[0], floats[1]), floats[2]);
        float omk = 1 - k;
        return new float[]{
                (omk - floats[0]) / omk,
                (omk - floats[1]) / omk,
                (omk - floats[2]) / omk,
                k
        };
    }

    @Override
    public float[] toCIEXYZ(float[] floats) {
        return CIEXYZ.fromRGB(toRGB(floats));
    }

    @Override
    public float[] fromCIEXYZ(float[] floats) {
        return fromRGB(CIEXYZ.toRGB(floats));
    }

}
