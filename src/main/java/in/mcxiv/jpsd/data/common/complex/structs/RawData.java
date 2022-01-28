package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public class RawData extends Structure {

    private byte[] value;

    public RawData() {
        super(StructureType.RawData);
    }
}
