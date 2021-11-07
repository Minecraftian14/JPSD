package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;
import in.mcxiv.jpsd.exceptions.IllegalVersionException;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataReader implements AutoCloseable, Closeable {

    private static final double something = 1. / (1 << 24);

    public final ImageInputStream stream;

    private final byte[] buffer0 = new byte[0];
    private final byte[] buffer2 = new byte[2];
    private final byte[] buffer3 = new byte[3];
    private final byte[] buffer4 = new byte[4];
    private final byte[] buffer6 = new byte[6];


    public DataReader(ImageInputStream stream) {
        this.stream = stream;
    }

    public DataReader(InputStream stream) {
        this(new MemoryCacheImageInputStream(stream));
    }

    public DataReader(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    public byte[] readBytes(int length, boolean createNew) throws IOException {
        if (createNew) {
            byte[] data = new byte[length];
            stream.readFully(data);
            return data;
        }
        switch (length) {
            case 0:
                return buffer0;
            case 2:
                stream.readFully(buffer2);
                return buffer2;
            case 3:
                stream.readFully(buffer3);
                return buffer3;
            case 4:
                stream.readFully(buffer4);
                return buffer4;
            case 6:
                stream.readFully(buffer6);
                return buffer6;
        }
        System.err.println("New byte buffer created of length " + length);
        return readBytes(length, true);
    }

    /**
     * After reading `length` bytes, this method can be used to read some `pad` bytes more,
     * such that `(length + pad) % multiple == 0`
     *
     * @param length   number of bytes read so far.
     * @param multiple length of one dividend block
     */
    public void skipToPadBy(long length, int multiple) throws IOException {
        int bytesReadExtra = (int) (length % multiple);
        if (bytesReadExtra == 0) return;
        int bytesRequiredMoreToCompleteBlock = multiple - bytesReadExtra;
        stream.skipBytes(bytesRequiredMoreToCompleteBlock);
    }

    public void skipAndPadBy(long length, int multiple) throws IOException {
        int extra = (int) (length % multiple);
        if (extra == 0) stream.skipBytes(length);
        else stream.skipBytes(length + multiple - extra);
    }

    public void skipAndPadBy4(long length) throws IOException {
        if ((length & 0b11) == 0) stream.skipBytes(length);
        else stream.skipBytes(length + (~length & 0b11) + 1);
    }

    public String readString(int length) throws IOException {
        return new String(readBytes(length, true), StandardCharsets.US_ASCII);
    }

    public byte[] verifySignature(byte[] signature) throws IOException {
        byte[] readings = readBytes(signature.length, false);
        if (!Arrays.equals(signature, readings))
            throw new IllegalSignatureException(signature, readings);
        return readings;
    }

    public byte[] verifySignature(byte[]... signatures) throws IOException {
        byte[] readings = readBytes(signatures[0].length, false);
        for (byte[] signature : signatures)
            if (Arrays.equals(signature, readings))
                return readings;
        throw new IllegalSignatureException(signatures, readings);
    }

    public String readPascalStringRaw() throws IOException {
        int stringLength = stream.readUnsignedByte();
        return new String(readBytes(stringLength, true), StandardCharsets.US_ASCII);
    }

    /**
     * @return reads padded string padded to make the length even.
     */
    public String readPascalStringEvenlyPadded() throws IOException {
        int stringLength = stream.readUnsignedByte();
        byte[] bytes = readBytes(stringLength, true);

        // As we are supposed to RW an even length string, we check if the string length was even or not.
        // Btw, if the length is even, we actually need to read one byte (else not) because we had also
        // read the size of the string as a byte, which makes the even length odd.
        if ((stringLength & 0b1) == 0) stream.skipBytes(1);

        return new String(bytes, StandardCharsets.US_ASCII);
    }

    public String readPascalStringPaddedTo4() throws IOException {
        int stringLength = stream.readUnsignedByte();
        byte[] bytes = readBytes(stringLength, true);

        skipToPadBy(stringLength + 1, 4);

        return new String(bytes, StandardCharsets.US_ASCII);
    }

    public String readUnicodeString() throws IOException {
        int stringLength = stream.readInt();
        if (stringLength == 0) return "";
        byte[] data = readBytes(2 * stringLength, true);
        return new String(data, StandardCharsets.UTF_16);
    }

    public double readFDouble() throws IOException {
        byte preserve_sign = stream.readByte();
        int integral_part = preserve_sign;
        stream.read(buffer4, 1, 3);
        int fractional_part = ByteBuffer.wrap(buffer4).getInt();
        return integral_part + fractional_part * something;
    }

    public float readFFloat() throws IOException {
        int bifVanX = stream.readInt();
        float bifVanA = (bifVanX & (65535 << 16)) >> 16;
        float bifVanB = (bifVanX & 65535) / 65535f;
        return bifVanA + bifVanB;
    }

    public int readByBits(DepthEntry depth) throws IOException {
        switch (depth) {
            default:
            case O:
                throw new UnsupportedOperationException();
            case E:
                return (byte) stream.readByte();
            case S:
                return (short) stream.readShort();
            case T:
                return stream.readInt();
        }
    }

    public int verifyShortVersion(final int possibility1, final int possibility2) throws IOException {
        int readings = stream.readUnsignedShort();
        if (possibility1 != readings && possibility2 != readings)
            throw new IllegalVersionException(readings, possibility1, possibility2);
        return readings;
    }

    public void verifyZeros(int length) throws IOException {
        byte[] bytes = readBytes(length, false);
        for (byte b : bytes)
            if (b != 0)
                throw new IOException("Next " + length + " bytes are reserved; should have all been 0. The reader found the following instead: " + Arrays.toString(bytes));
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
