package in.mcxiv.jpsd.util;

import java.awt.color.ColorSpace;

/**
 * Adapted using https://imagej.nih.gov/ij/plugins/download/Color_Space_Converter.java as a reference.
 */
public class LABColorSpace extends ColorSpace {

    public static final ColorSpace INSTANCE = new LABColorSpace();
    private static final ColorSpace CIEXYZ = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
    private static final double N = 4.0 / 29.0;

    private LABColorSpace() {
        super(TYPE_Lab, 3);
    }

    @Override
    public float[] toRGB(float[] colorvalue) {
        return CIEXYZ.toRGB(toCIEXYZ(colorvalue));
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        return fromCIEXYZ(CIEXYZ.fromRGB(rgbvalue));
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        float y = (colorvalue[0] + 16.0f) / 116.0f;
        float y3 = y * y * y;
        float x = (colorvalue[1] / 500.0f) + y;
        float x3 = x * x * x;
        float z = y - (colorvalue[2] / 200.0f);
        float z3 = z * z * z;

        if (y3 > 0.008856f) y = y3;
        else y = (y - (16.0f / 116.0f)) / 7.787f;

        if (x3 > 0.008856f) x = x3;
        else x = (x - (16.0f / 116.0f)) / 7.787f;

        if (z3 > 0.008856f) z = z3;
        else z = (z - (16.0f / 116.0f)) / 7.787f;

        return new float[]{
                x * 94.9722f,
                y * 100.0f,
                z * 122.6394f
        };
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        float x = colorvalue[0] / 94.9722f;
        float y = colorvalue[1] / 100.0f;
        float z = colorvalue[2] / 122.6394f;

        if (x > 0.008856) x = (float) Math.pow(x, 1.0 / 3.0);
        else x = (7.787f * x) + (16.0f / 116.0f);
        if (y > 0.008856) y = (float) Math.pow(y, 1.0 / 3.0);
        else y = (7.787f * y) + (16.0f / 116.0f);
        if (z > 0.008856f) z = (float) Math.pow(z, 1.0 / 3.0);
        else z = (7.787f * z) + (16.0f / 116.0f);

        return new float[]{
                (116.0f * y) - 16.0f,
                500.0f * (x - y),
                200.0f * (y - z)
        };
    }

    @Override
    public float getMaxValue(int component) {
        return 128f;
    }

    @Override
    public float getMinValue(int component) {
        return 0f;
    }

    @Override
    public String getName(int idx) {
        return String.valueOf("Lab".charAt(idx));
    }
}
