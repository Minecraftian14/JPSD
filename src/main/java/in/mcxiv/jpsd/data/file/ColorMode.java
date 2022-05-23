package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.util.CMYKColorSpace;
import in.mcxiv.jpsd.util.GrayScaleColorSpace;
import in.mcxiv.jpsd.util.LABColorSpace;

import java.awt.color.ColorSpace;
import java.util.Objects;

public enum ColorMode implements ShortEntry {
    Bitmap(0, -1, null),
    Grayscale(1, 1, GrayScaleColorSpace.INSTANCE),
    Indexed(2, -1, null),
    RGB(3, 3, ColorSpace.getInstance(ColorSpace.CS_sRGB)),
    CMYK(4, 4, CMYKColorSpace.INSTANCE),
    Multichannel(7, -1, null),
    Duotone(8, 1, null),
    Lab(9, 3, LABColorSpace.INSTANCE);

    private static final ColorSpace CIEXYZ = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
    private final short colorMode;
    private final short components;
    private final ColorSpace colorSpace;

    ColorMode(int colorMode, int components, ColorSpace colorSpace) {
        this.colorMode = (short) colorMode;
        this.components = (short) components;
        this.colorSpace = colorSpace;
    }

    @Override
    public short getValue() {
        return colorMode;
    }

    public short components() {
        return components;
    }

    public ColorSpace getColorSpace() {
        return colorSpace;
    }

    public static ColorMode of(short colorMode) {
        switch (colorMode) {                                                                         //@formatter:off
            case 0:  return Bitmap;
            case 1:  return Grayscale;
            case 2:  return Indexed;
            case 3:  return RGB;
            case 4:  return CMYK;
            case 7:  return Multichannel;
            case 8:  return Duotone;
            case 9:  return Lab;
            default:
                throw new IllegalArgumentException("No such color mode defined as " + colorMode);    //@formatter:on
        }
    }

    public static ColorMode of(ColorSpace colorSpace) {
        for (ColorMode value : values())
            if (Objects.equals(value.colorSpace, colorSpace))
                return value;
        return null;
    }
}
