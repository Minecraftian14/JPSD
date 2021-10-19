package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.primitive.ByteEntry;

public enum Clipping implements ByteEntry {
    Base, Non_Base;

    public static Clipping of(byte readByte) {
        return values()[readByte];
    }

    @Override
    public byte getValue() {
        return (byte) ordinal();
    }
}
