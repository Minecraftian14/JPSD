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
        float k = Math.min(Math.min(1 - floats[0], 1 - floats[1]), 1 - floats[2]);
        return new float[]{
                1 - floats[0] - k,
                1 - floats[1] - k,
                1 - floats[2] - k,
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
