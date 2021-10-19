package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.common.complex.Item;
import in.mcxiv.jpsd.data.common.complex.StructureType;

public class ListS extends Structure{

    private int numberOfItems;
    private Item[] items;

    public ListS() {
        super(StructureType.List);
    }
}
