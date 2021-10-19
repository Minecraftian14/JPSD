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
    private float x_offset;
    private float y_offset;
    private float scale;

    public PrintScaleRBlock(String pascalString, long length, Style style, float x_offset, float y_offset, float scale) {
        super(ImageResourceID.PrintScale, pascalString, length);
        this.style = style;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.scale = scale;
    }
}
