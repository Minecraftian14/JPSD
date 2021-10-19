package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public class Alias extends Structure{

    private String value;

    public Alias() {
        super(StructureType.Alias);
    }
}
