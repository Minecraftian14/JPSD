package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.data.common.complex.StructureType;

import java.nio.charset.StandardCharsets;

public class Reference extends Structure {

    public enum ReferenceType implements BytesEntry {
        Property("prop"),
        Class("Clss"),
        EnumeratedReference("Enmr"),
        Offset("rele"),
        Identifier("Idnt"),
        Index("indx"),
        Name("name");

        private final byte[] value;

        ReferenceType(String value) {
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

    public static class Item {

        private ReferenceType type;
        //private sometig

    }

    private int numberOfItems;
    private Item[] items;

    public Reference() {
        super(StructureType.Reference);
    }
}
