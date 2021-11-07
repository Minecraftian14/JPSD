package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.data.primitive.ShortEntry;

public enum ColorMode implements ShortEntry {
    Bitmap(0, -1),
    Grayscale(1, 1),
    Indexed(2, -1),
    RGB(3, 3),
    CMYK(4, 4),
    Multichannel(7, -1),
    Duotone(8, 1),
    Lab(9, 3);

    private final short colorMode;
    private final short components;

    ColorMode(int colorMode, int components) {
        this.colorMode = (short) colorMode;
        this.components = (short) components;
    }

    @Override
    public short getValue() {
        return colorMode;
    }

    public short components() {
        return components;
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
}
