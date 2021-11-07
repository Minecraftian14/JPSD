package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.DepthEntry;

public class ImageMeta {

    int width;
    int height;
    int channels;
    boolean isLarge;
    ColorMode colorMode;
    DepthEntry depth;

    public ImageMeta(int width, int height, boolean isLarge, ColorMode colorMode, int channels, DepthEntry depth) {
        this.width = width;
        this.height = height;
        this.isLarge = isLarge;
        this.channels = channels;
        this.colorMode = colorMode;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public boolean isLarge() {
        return isLarge;
    }

    public short getDepth() {
        return depth.getValue();
    }

    public DepthEntry getDepthEntry() {
        return depth;
    }

    public boolean isInvertRequired() {
        return ColorMode.CMYK.equals(colorMode) && channels < colorMode.components();
    }
}
