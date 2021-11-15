package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.exceptions.IllegalVersionException;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataWriter implements AutoCloseable, Closeable {

    private static final double INVERSE_CONSTANT = (1 << 24);

    private final byte[] buffer0 = new byte[0];
    private final byte[] buffer2 = new byte[2];
    private final byte[] buffer3 = new byte[3];
    private final byte[] buffer4 = new byte[4];
    private final byte[] buffer6 = new byte[6];

    public final ImageOutputStream stream;

    public DataWriter(ImageOutputStream stream) {
        this.stream = stream;
    }

    public DataWriter(OutputStream stream) {
        this(new MemoryCacheImageOutputStream(stream));
    }

    public DataWriter() {
        this(new ByteArrayOutputStream());
    }

    public int writeBytes(byte[] bytes) throws IOException {
        stream.write(bytes);
        return bytes.length;
    }

    public void writeByte(byte b) throws IOException {
        stream.write(b);
    }

    public void writeByte(int b) throws IOException {
        writeByte((byte) b);
    }

    public void fill(int length, byte b) throws IOException {
        for (int i = 0; i < length; i++)
            stream.write(b);
    }

    public void fillZeros(int length) throws IOException {
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
        fill(length, (byte) 0);
    }

    public void writeString(String string) throws IOException {
        stream.write(string.getBytes(StandardCharsets.US_ASCII));
    }

    public void sign(byte[] signature) throws IOException {
        stream.write(signature);
    }

    public void writePascalStringRaw(String pascalString) throws IOException {
        stream.write(pascalString.length());
        stream.write(pascalString.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * writes a padded string padded to make the length even.
     */
    public void writePascalStringEvenlyPadded(String pascalString) throws IOException {
        int length = ((byte) pascalString.length());
        stream.write(length);
        stream.write(pascalString.substring(0, length).getBytes(StandardCharsets.US_ASCII));
        if (length % 2 == 0) stream.write(0);
    }

    public void writePascalStringPaddedTo4(String pascalString) throws IOException {
        int length = ((byte) pascalString.length());
        stream.write(length);
        stream.write(pascalString.substring(0, length).getBytes(StandardCharsets.US_ASCII));
        int extra = 4 - (length % 4);
        if (extra != 4) fillZeros(extra);
    }

    public void writeUnicodeString(String unicodeString) throws IOException {
        stream.writeInt(unicodeString.length());
        stream.write(unicodeString.getBytes(StandardCharsets.UTF_16BE));
    }

    public void writeFDouble(double value) throws IOException {
        int integral_part = (int) Math.floor(value);
        int fractional_part = (int) ((value - integral_part) * INVERSE_CONSTANT);
        stream.write(((byte) integral_part));
//        stream.write((fractional_part >> 24) & 0xFF);
//        stream.write((fractional_part >> 16) & 0xFF);
//        stream.write((fractional_part) & 0xFF);
        ByteBuffer.wrap(zeros(buffer4)).putInt(fractional_part);
        stream.write(buffer4, 1, 3);
    }

    public void writeFFloat(float bifVan) throws IOException {
        int bifVanA = ((int) Math.floor(bifVan)) & 65535;
        int bifVanB = ((int) ((bifVan - bifVanA) * 65535)) & 65535;
        int bifVanX = (bifVanA << 16) | bifVanB;
        stream.writeInt(bifVanX);
    }

    public void writeByBits(DepthEntry depth, int val) throws IOException {
        switch (depth) {
            default:
            case O:
                throw new UnsupportedOperationException();
            case E:
                stream.writeByte(val);
                break;
            case S:
                stream.writeShort(val);
                break;
            case T:
                stream.writeInt(val);
                break;
        }
    }

    public void shortSign(final int sign) throws IOException {
        stream.writeShort(sign);
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

    public byte[] toByteArray() {
        if (stream instanceof ByteArrayOutputStream) return ((ByteArrayOutputStream) stream).toByteArray();
        throw new UnsupportedOperationException();
    }
}
