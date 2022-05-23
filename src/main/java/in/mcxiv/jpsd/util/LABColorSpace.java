package in.mcxiv.jpsd.util;

import java.awt.color.ColorSpace;

/**
 * Adapted using https://stackoverflow.com/a/5021831/18800689 as a reference.
 */
public class LABColorSpace extends ColorSpace {

    public static final ColorSpace INSTANCE = new LABColorSpace();
    private static final ColorSpace CIEXYZ = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);

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
        double i = (colorvalue[0] + 16.0) * (1.0 / 116.0);
        double X = fInv(i + colorvalue[1] * (1.0 / 500.0));
        double Y = fInv(i);
        double Z = fInv(i - colorvalue[2] * (1.0 / 200.0));
        return new float[]{(float) X, (float) Y, (float) Z};
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        double l = f(colorvalue[1]);
        double L = 116.0 * l - 16.0;
        double a = 500.0 * (f(colorvalue[0]) - l);
        double b = 200.0 * (l - f(colorvalue[2]));
        return new float[]{(float) L, (float) a, (float) b};
    }

    @Override
    public float getMaxValue(int component) {
        return 128f;
    }

    @Override
    public float getMinValue(int component) {
        return (component == 0) ? 0f : -128f;
    }

    @Override
    public String getName(int idx) {
        return String.valueOf("Lab".charAt(idx));
    }

    private static double f(double x) {
        if (x > 216.0 / 24389.0) {
            return Math.cbrt(x);
        } else {
            return (841.0 / 108.0) * x + N;
        }
    }

    private static double fInv(double x) {
        if (x > 6.0 / 29.0) {
            return x * x * x;
        } else {
            return (108.0 / 841.0) * (x - N);
        }
    }

    private static final double N = 4.0 / 29.0;
}
