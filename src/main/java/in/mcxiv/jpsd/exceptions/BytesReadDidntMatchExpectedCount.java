package in.mcxiv.jpsd.exceptions;

import java.io.IOException;

public class BytesReadDidntMatchExpectedCount extends IOException {

    public BytesReadDidntMatchExpectedCount(long expected, long start, long end) {
        this(expected, end - start);
    }

    public BytesReadDidntMatchExpectedCount(long expected, long actual) {
        super(getMessage(expected, actual));
    }

    private static String getMessage(long expected, long actual) {
        return String.format("Read too many bytes! (Read %d bytes instead of of %d)", actual, expected);
    }
}
