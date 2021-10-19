package in.mcxiv.jpsd.data.primitive;

public interface ShortEntry {
    short getValue();

    static <T extends ShortEntry> T of(short value, T[] collection) {
        for (T t : collection)
            if (t.getValue() == value)
                return t;
        throw new IllegalArgumentException(value + " not found!");
    }

}
