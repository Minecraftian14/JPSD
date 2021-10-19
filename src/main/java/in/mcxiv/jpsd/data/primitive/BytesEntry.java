package in.mcxiv.jpsd.data.primitive;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public interface BytesEntry {

    int getLength();

    byte[] getValue();

    static <T extends BytesEntry> T of(byte[] value, T[] collection) {
        if (value.length != collection[0].getLength()) throw new IllegalArgumentException("Length not matching.");
        for (T entry : collection)
            if (Arrays.equals(entry.getValue(), value))
                return entry;
        throw new IllegalArgumentException(new String(value, StandardCharsets.US_ASCII) + " not found!");
    }

    static <T extends BytesEntry> T of(String strValue, T[] collection) {
        byte[] value = strValue.getBytes(StandardCharsets.US_ASCII);
        return of(value, collection);
    }

}
