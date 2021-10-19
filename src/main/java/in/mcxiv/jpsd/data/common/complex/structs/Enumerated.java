package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public class Enumerated extends Structure{

    private String name;
    private String classID;
    private String typeID;
    private String enumVal;


    public Enumerated() {
        super(StructureType.Enumerated);
    }
}
