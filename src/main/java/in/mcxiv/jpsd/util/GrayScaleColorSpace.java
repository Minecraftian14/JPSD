package in.mcxiv.jpsd.util;

import java.awt.color.ColorSpace;

public class GrayScaleColorSpace extends ColorSpace {
    public static final ColorSpace INSTANCE = new GrayScaleColorSpace();

    private GrayScaleColorSpace() {
        super(TYPE_GRAY, 1);
    }

    @Override
    public float[] toRGB(float[] floats) {
        return new float[]{floats[0], floats[0], floats[0]};
    }

    @Override
    public float[] fromRGB(float[] floats) {
        return new float[]{(floats[0] + floats[1] + floats[2]) / 3};
    }

    @Override
    public float[] toCIEXYZ(float[] floats) {
        return new float[]{
                0.31682333f * floats[0],
                0.33333337f * floats[0],
                0.36294332f * floats[0]
        };
    }

    @Override
    public float[] fromCIEXYZ(float[] floats) {
        return new float[]{0.25853685f * floats[0] + 0.014982935f * floats[1] + 0.06669442f * floats[2]};
    }
}
