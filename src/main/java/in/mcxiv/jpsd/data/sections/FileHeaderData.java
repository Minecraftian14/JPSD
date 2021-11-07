package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.ImageMeta;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.primitive.IntEntry;
import in.mcxiv.jpsd.data.primitive.ShortEntry;

import java.util.Objects;

public class FileHeaderData extends DataObject {

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

    public short getNumberOfChannels() {
        return channels.channels;
    }

    public int getHeight() {
        return height.value;
    }

    public int getWidth() {
        return width.value;
    }

    public short getDepth() {
        return depth.getValue();
    }

    public DepthEntry getDepthEntry() {
        return depth;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public boolean isLarge() {
        return version.isLarge();
    }

    public boolean isInvertRequired() {
        // FIXME: Also include alpha :(
        return ColorMode.CMYK.equals(colorMode) && channels.channels < colorMode.components();
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

    public ImageMeta toImageMeta() {
        return new ImageMeta(getWidth(), getHeight(), isLarge(), getColorMode(), getNumberOfChannels(), getDepthEntry());
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
