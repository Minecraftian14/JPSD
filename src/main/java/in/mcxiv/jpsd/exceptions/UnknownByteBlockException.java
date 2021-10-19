package in.mcxiv.jpsd.exceptions;

import java.io.IOException;

public class UnknownByteBlockException extends IOException {
    public UnknownByteBlockException(String context) {
        super("Unknown type of block encountered while reading " + context);
    }
}
