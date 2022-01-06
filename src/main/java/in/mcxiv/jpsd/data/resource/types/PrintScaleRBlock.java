package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PrintScaleRBlock extends ImageResourceBlock {

    public enum Style implements ShortEntry {
        CENTERED, SIZE_TO_FIT, USER_DEFINED;

        public static Style of(short value) {
            return ShortEntry.of(value, values());
        }

        @Override
        public short getValue() {
            return (short) ordinal();
        }
    }

    private Style style;
    private float xOffset;
    private float yOffset;
    private float scale;

    public PrintScaleRBlock(String pascalString, long length, Style style, float x_offset, float y_offset, float scale) {
        super(ImageResourceID.PrintScale, pascalString, length);
        this.style = style;
        this.xOffset = x_offset;
        this.yOffset = y_offset;
        this.scale = scale;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
