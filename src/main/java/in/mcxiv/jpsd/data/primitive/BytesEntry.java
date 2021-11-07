package in.mcxiv.jpsd.data.primitive;

import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public interface BytesEntry {

    int getLength();

    byte[] getValue();

    static <T extends BytesEntry> T of(byte[] value, T[] collection) throws IllegalSignatureException {
        if (value.length != collection[0].getLength()) throw new IllegalArgumentException("Length not matching.");
        for (T entry : collection)
            if (Arrays.equals(entry.getValue(), value))
                return entry;
        throw new IllegalSignatureException(Arrays.stream(collection).map(BytesEntry::getValue).toArray(byte[][]::new), value);
    }

    static <T extends BytesEntry> T of(String strValue, T[] collection) throws IllegalSignatureException {
        byte[] value = strValue.getBytes(StandardCharsets.US_ASCII);
        return of(value, collection);
    }

}
