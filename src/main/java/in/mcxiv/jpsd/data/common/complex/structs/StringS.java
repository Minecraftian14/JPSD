package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public class StringS extends Structure {

    private String value; // unicode

    public StringS() {
        super(StructureType.String);
    }
}
