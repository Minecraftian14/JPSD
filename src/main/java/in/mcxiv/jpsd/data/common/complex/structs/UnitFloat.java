package in.mcxiv.jpsd.data.common.complex.structs;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.data.common.complex.StructureType;

import java.nio.charset.StandardCharsets;

public class UnitFloat extends Structure {

    public enum Unit implements BytesEntry {
        /**
         * tagged unit value
         */
        Points("#Pnt"),
        /**
         * tagged unit value
         */
        Millimeters("#Mlm"),
        /**
         * base degrees
         */
        Angle("#Ang"),
        /**
         * base per inch
         */
        Density("#Rsl"),
        /**
         * base 72ppi
         */
        Distance("#Rlt"),
        /**
         * coerced
         */
        None("#Nne"),
        /**
         * unit value
         */
        Percent("#Prc"),
        /**
         * tagged unit value
         */
        Pixels("#Pxl");

        private final byte[] value;

        Unit(String value) {
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

    private Unit unit;
    private double value;

    public UnitFloat() {
        super(StructureType.UnitFloat);
    }
}
