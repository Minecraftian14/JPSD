package in.mcxiv.jpsd.exceptions;

import java.io.IOException;
import java.util.Arrays;

public class IllegalSignatureException extends IOException {

    public IllegalSignatureException(String message) {
        super(message);
    }

    public IllegalSignatureException(byte[] expected, byte[] actual) {
        super(String.format("Expected %s but found %s(%s)", new String(expected), Arrays.toString(actual), new String(actual)));
    }

    public IllegalSignatureException(byte[][] expected, byte[] actual) {
        super("Expected one of " + Arrays.toString(Arrays.stream(expected).map(String::new).toArray()) + " but found " + Arrays.toString(actual) + " " + new String(actual));
    }
}
