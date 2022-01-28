package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public class LargeInteger extends Structure {

    private long value;

    public LargeInteger() {
        super(StructureType.LargeInteger);
    }
}
