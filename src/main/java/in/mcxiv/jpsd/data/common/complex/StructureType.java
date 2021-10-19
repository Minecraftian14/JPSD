package in.mcxiv.jpsd.data.common.complex;

import in.mcxiv.jpsd.data.primitive.BytesEntry;

import java.nio.charset.StandardCharsets;

public enum StructureType implements BytesEntry {

    Reference("obj "),
    Descriptor("Objc"),
    List("VlLs"),
    Double("doub"),
    UnitFloat("UntF"),
    String("TEXT"),
    Enumerated("enum"),
    Integer("long"),
    LargeInteger("comp"),
    Boolean("bool"),
    GlobalObject("GlbO"), // possibly another Descriptor
    Class("type"),
    GlobalClass("GlbC"),
    Alias("alis"),
    RawData("tdta");

    private final byte[] value;

    StructureType(String value) {
        this.value = value.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public byte[] getValue() {
        return value;
    }
}
