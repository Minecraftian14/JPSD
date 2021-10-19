package in.mcxiv.jpsd.data.layer.info.record.mask;

import in.mcxiv.jpsd.data.primitive.BitDataEntry;

public class LayerMaskInfoFlag extends BitDataEntry {

    //@formatter:off
    public static final byte POS_RELATIVE_TO_LAYER    = 0b0000_0001;
    public static final byte LAYER_MASK_DISABLED      = 0b0000_0010;
    public static final byte INVERT_LAYER_MASK        = 0b0000_0100;
    public static final byte CAME_FROM_RENDERING_DATA = 0b0000_1000;
    public static final byte HAVE_PARAMETERS_APPLIED   = 0b0001_0000;
    //@formatter:on

    public LayerMaskInfoFlag(byte value) {
        super(value);
    }

    @Override
    public String toString() {
        return "LayerMaskInfoFlag{POS_RELATIVE_TO_LAYER=" + has(POS_RELATIVE_TO_LAYER) +
                ", LAYER_MASK_DISABLED=" + has(LAYER_MASK_DISABLED) +
                ", INVERT_LAYER_MASK=" + has(INVERT_LAYER_MASK) +
                ", CAME_FROM_RENDERING_DATA=" + has(CAME_FROM_RENDERING_DATA) +
                ", HAVE_PARAMETERS_APPLIED=" + has(HAVE_PARAMETERS_APPLIED) + "}";
    }
}
