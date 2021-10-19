package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.primitive.ShortEntry;

public enum Compression implements ShortEntry {
    Raw_Data, RLE_Compression, ZIP, ZIP_With_Prediction;

    public static Compression of(short val) {
        return ShortEntry.of(val, values());
    }

    @Override
    public short getValue() {
        return (short) ordinal();
    }

    @Override
    public String toString() {
        return "Compression:"+name();
    }
}
