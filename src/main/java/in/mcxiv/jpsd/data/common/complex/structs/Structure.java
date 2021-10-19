package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.StructureType;

public abstract class Structure {

    protected final StructureType structureType;

    public Structure(StructureType structureType) {
        this.structureType = structureType;
    }
}
