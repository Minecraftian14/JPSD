package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.primitive.BitDataEntry;

public class LayerRecordInfoFlag extends BitDataEntry {

    //@formatter:off
    public static final byte TRANSPARENCY_PROTECTED = 0b0000_0001;
    public static final byte VISIBLE                = 0b0000_0010;
    public static final byte OBSOLETE               = 0b0000_0100;
    public static final byte HAS_FOURTH             = 0b0000_1000;
    public static final byte PIXEL_DATA             = 0b0001_0000;
    //@formatter:on

    public LayerRecordInfoFlag(byte value) {
        super(value);
        remove(PIXEL_DATA);
        if (has(HAS_FOURTH))
            if (((value >> 4) & 1) != 0)
                add(PIXEL_DATA);
    }

    @Override
    public String toString() {
        return "LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=" + has(TRANSPARENCY_PROTECTED) +
                ", VISIBLE=" + has(VISIBLE) +
                ", OBSOLETE=" + has(OBSOLETE) +
                ", HAS_FOURTH=" + has(HAS_FOURTH) +
                ", PIXEL_DATA=" + has(PIXEL_DATA) + "}";
    }
}
