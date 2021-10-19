package in.mcxiv.jpsd.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataWriter implements AutoCloseable, Closeable {

    protected final OutputStream stream;

    private final byte[] buffer2 = new byte[2];
    private final byte[] buffer4 = new byte[4];
    private final byte[] buffer6 = new byte[6];

    public DataWriter(OutputStream stream) {
        this.stream = stream;
    }

    public void writeByte(byte b) {
        try {
            stream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeByte(int b) {
        writeByte((byte) b);
    }

    public void writeBytes(byte[] bytes) {
        try {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeZeros(int length) {
        switch (length) {
            case 2:
                writeBytes(zeros(buffer2));
                return;
            case 4:
                writeBytes(zeros(buffer4));
                return;
            case 6:
                writeBytes(zeros(buffer6));
                return;
        }
        System.err.println("New byte buffer created of length " + length);
        writeBytes(zeros(new byte[length]));
    }

    public void writeString(String string) {
        writeBytes(string.getBytes(StandardCharsets.US_ASCII));
    }

    public void writePascalStringRaw(String pascalString) {
        writeByte((byte) pascalString.length());
        // TODO: Is the encoding supposed to be configurable? ie asked through a constructor?
        writeBytes(pascalString.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * writes a padded string padded to make the length even.
     */
    public void writePascalStringEvenlyPadded(String pascalString) {
        byte length = (byte) pascalString.length();
        writeByte(length);
        // TODO: Is the encoding supposed to be configurable? ie asked through a constructor?
        writeBytes(pascalString.substring(0, length).getBytes(StandardCharsets.US_ASCII));
        // As we are supposed to RW an even length string, we check if the string length was even or not.
        // Btw, if the length is even, we actually need to write one byte (else not) because we had also
        // written the size of the string as a byte, which makes the even length odd.
        if (length % 2 == 0)
            writeZeros(1);
    }

    public void writeUnicodeString(String unicodeString) {
        writeInt(unicodeString.length());
        writeBytes(unicodeString.getBytes(StandardCharsets.UTF_16BE));
    }

    public void writeShort(short value) {
        writeBytes(ByteBuffer.allocate(Short.BYTES).putShort(value).array());
    }

    public void writeInt(int value) {
        writeBytes(ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
    }

    public void writeByteBoolean(boolean b) {
        writeByte(b ? 1 : 0);
    }

    @Override
    public void close() {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] zeros(byte[] array) {
        Arrays.fill(array, (byte) 0);
        return array;
    }

    public void writeFFloat(float I_just_wanna_cry_straight_for_an_hour) {

    }
}
