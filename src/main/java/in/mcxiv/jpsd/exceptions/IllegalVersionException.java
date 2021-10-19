package in.mcxiv.jpsd.exceptions;

import java.io.IOException;

public class IllegalVersionException extends IOException {

    public IllegalVersionException(int actual, int expected1, int expected2) {
        super(String.format("There is no such version as '%d' (Supported versions include only %d and %d)", actual, expected1, expected2));
    }

}
