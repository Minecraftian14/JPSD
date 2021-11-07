package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.data.sections.FileHeaderData;

public enum FileVersion implements ShortEntry {
    PSD, PSB;

    private final short value;

    FileVersion() {
        this.value = (short) (ordinal() + 1);
    }

    public short getValue() {
        return value;
    }

    public boolean isLarge() {
        return value == 2;
    }

    public static FileVersion of(int version) {
        // int because we expect an input of unsigned short
        return ShortEntry.of((short) version,values());
    }
}
