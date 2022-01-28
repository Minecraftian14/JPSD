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
        if (actual > expected)
            return String.format("Read too many bytes! (Read %d bytes more than an expected of %d)", actual - expected, expected);
        else
            return String.format("Read less bytes! (Read %d bytes less than an expected of %d)", expected - actual, expected);
    }
}
