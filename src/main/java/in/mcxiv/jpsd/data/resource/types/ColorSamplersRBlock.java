package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.primitive.IntEntry;
import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.Arrays;

public class ColorSamplersRBlock extends ImageResourceBlock {

    public enum Version implements IntEntry {
        None, V1, V2, V3;

        @Override
        public int getValue() {
            return ordinal();
        }

        public static Version of(int version) {
            return values()[version];
        }
    }

    public static class SamplersResourceSubBlock {

        public enum ColorSpace implements ShortEntry {
            colorCodeDummy, RGB, HSB, CMYK, Pantone, Focoltone, Trumatch, Toyo, Lab, Gray, WideCMYK, HKS, DIC, TotalInk, MonitorRGB, Duotone, Opacity, Web, GrayFloat, RGBFloat, OpacityFloat;

            @Override
            public short getValue() {
                return (short) (ordinal() - 1);
            }

            public static ColorSpace of(int version) {
                return values()[version + 1];
            }

        }

        private int version;
        private int horizontal; // Position
        private int vertical;   // Position
        private ColorSpace colorSpace;
        private short depth;

        public int getVersion() {
            return version;
        }

        public int getHorizontal() {
            return horizontal;
        }

        public int getVertical() {
            return vertical;
        }

        public ColorSpace getColorSpace() {
            return colorSpace;
        }

        public short getDepth() {
            return depth;
        }

        public SamplersResourceSubBlock(int version, int horizontal, int vertical, ColorSpace colorSpace, short depth) {
            this.version = version;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.colorSpace = colorSpace;
            this.depth = depth;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Version version;

    private SamplersResourceSubBlock[] blocks;

    public ColorSamplersRBlock(ImageResourceID identity, String pascalString, long length, Version version, SamplersResourceSubBlock[] blocks) {
        super(identity, pascalString, length);
        this.version = version;
        this.blocks = blocks;
    }

    public Version getVersion() {
        return version;
    }

    public int getNumberOfColorSamples() {
        return blocks.length;
    }

    public SamplersResourceSubBlock[] getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "ColorSamplersRBlock{" +
                "version=" + version +
                ", numberOfColorSamples=" + blocks.length +
                ", blocks=" + Arrays.toString(blocks) +
                '}';
    }

}
