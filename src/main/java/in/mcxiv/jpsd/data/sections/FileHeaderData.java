package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.primitive.IntEntry;
import in.mcxiv.jpsd.data.primitive.ShortEntry;

import java.awt.color.ColorSpace;
import java.util.Objects;

public class FileHeaderData extends DataObject {

    public enum FileVersion implements ShortEntry {
        PSD, PSB;

        private final short value;

        FileVersion() {
            this.value = (short) (ordinal() + 1);
        }

        public short getValue() {
            return value;
        }

        public boolean isLarge() {
            return value == 2;
        }

        public static FileVersion of(int version) {
            // int because we expect an input of unsigned short
            return ShortEntry.of((short) version, values());
        }
    }

    public static class ChannelsEntry implements ShortEntry {
        private final short channels;

        public ChannelsEntry(short channels) {
            if (channels < 1 || channels > 56)
                throw new IllegalArgumentException("Supported range of channels is only 1 to 56 inclusive while the requested channels are " + channels);
            this.channels = channels;
        }

        public short getValue() {
            return channels;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChannelsEntry that = (ChannelsEntry) o;
            return channels == that.channels;
        }

        @Override
        public int hashCode() {
            return Objects.hash(channels);
        }

        @Override
        public String toString() {
            return "" + channels;
        }
    }

    public static class DimensionalEntry implements IntEntry {

        private final int value;

        public DimensionalEntry(FileVersion version, int value) {
            if (value < 1)
                throw new IllegalArgumentException("Image dimensions cannot be less than 1! Given value " + value);
            if (version.isLarge()) {
                if (value > 300_000)
                    throw new IllegalArgumentException("Large Image dimensions cannot be greater than 300,000! Given value " + value);
            } else if (value > 30_000)
                throw new IllegalArgumentException("Image dimensions cannot be greater than 30,000! Given value " + value);
            this.value = value;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DimensionalEntry that = (DimensionalEntry) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public enum DepthEntry implements ShortEntry {
        O(1), E(8), S(16), T(32);
        private final short depth;

        DepthEntry(int depth) {
            this.depth = (short) depth;
        }

        @Override
        public short getValue() {
            return depth;
        }

        static DepthEntry of(short depth) {
            switch (depth) {                                                                     //@formatter:off
                case 1:  return O;
                case 8:  return E;
                case 16: return S;
                case 32: return T;
                default:
                    throw new IllegalArgumentException("No such depth possible as " + depth);    //@formatter:on
            }
        }

        @Override
        public String toString() {
            return "" + depth;
        }
    }

    public enum ColorMode implements ShortEntry {
        Bitmap(0, null),
        Grayscale(1, null),
        Indexed(2, null),
        RGB(3, ColorSpace.getInstance(ColorSpace.CS_sRGB)),
        CMYK(4, null),
        Multichannel(7, null),
        Duotone(8, null),
        Lab(9, null);

        private final short colorMode;
        private final ColorSpace colorSpace;

        ColorMode(int colorMode, ColorSpace colorSpace) {
            this.colorMode = (short) colorMode;
            this.colorSpace = colorSpace;
        }

        @Override
        public short getValue() {
            return colorMode;
        }

        public ColorSpace getColorSpace() {
            return colorSpace;
        }

        static ColorMode of(short colorMode) {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private FileVersion version;
    private ChannelsEntry channels;
    private DimensionalEntry height;
    private DimensionalEntry width;
    private DepthEntry depth;
    private ColorMode colorMode;

    public FileHeaderData(int version, int channels, int height, int width, int depth, int colorMode) {
        this((short) version, (short) channels, height, width, (short) depth, (short) colorMode);
    }

    public FileHeaderData(short version, short channels, int height, int width, short depth, short colorMode) {
        this(FileVersion.of(version), channels, height, width, depth, colorMode);
    }

    public FileHeaderData(FileVersion version, short channels, int height, int width, short depth, short colorMode) {
        this(
                version,
                new ChannelsEntry(channels),
                new DimensionalEntry(version, height),
                new DimensionalEntry(version, width),
                DepthEntry.of(depth),
                ColorMode.of(colorMode)
        );
    }

    public FileHeaderData(FileVersion version, ChannelsEntry channels, DimensionalEntry height, DimensionalEntry width, DepthEntry depth, ColorMode colorMode) {
        this.version = version;
        this.channels = channels;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.colorMode = colorMode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public FileVersion getVersion() {
        return version;
    }

    public short getChannels() {
        return channels.channels;
    }

    public int getHeight() {
        return height.value;
    }

    public int getWidth() {
        return width.value;
    }

    public short getDepth() {
        return depth.depth;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileHeaderData that = (FileHeaderData) o;
        return version == that.version && Objects.equals(channels, that.channels) && Objects.equals(height, that.height) && Objects.equals(width, that.width) && depth == that.depth && colorMode == that.colorMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, channels, height, width, depth, colorMode);
    }

    @Override
    public String toString() {
        return "FileHeaderData{" +
                "version=" + version +
                ", channels=" + channels +
                ", height=" + height +
                ", width=" + width +
                ", depth=" + depth +
                ", colorMode=" + colorMode +
                '}';
    }

}
