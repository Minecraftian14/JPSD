package in.mcxiv.jpsd.data.layer.info.record.mask;

import in.mcxiv.jpsd.data.primitive.BitDataEntry;

public class MaskParameterFlag extends BitDataEntry {

    //@formatter:off
    public static final byte USER_MASK_DENSITY   = 0b0000_0001;
    public static final byte USER_MASK_FEATHER   = 0b0000_0010;
    public static final byte VECTOR_MASK_DENSITY = 0b0000_0100;
    public static final byte VECTOR_MASK_FEATHER = 0b0000_1000;
    //@formatter:on

    public MaskParameterFlag(byte value) {
        super(value);
    }

    @Override
    public String toString() {
        return "MaskParameterFlag{USER_MASK_DENSITY=" + has(USER_MASK_DENSITY) +
                ", USER_MASK_FEATHER=" + has(USER_MASK_FEATHER) +
                ", VECTOR_MASK_DENSITY=" + has(VECTOR_MASK_DENSITY) +
                ", VECTOR_MASK_FEATHER=" + has(VECTOR_MASK_FEATHER) + "}";
    }
}
