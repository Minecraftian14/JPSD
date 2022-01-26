package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.data.primitive.ShortEntry;

import java.awt.image.*;

public enum DepthEntry implements ShortEntry {
    O(1, DataBuffer.TYPE_UNDEFINED, null),
    E(8, DataBuffer.TYPE_BYTE, DataBufferByte.class),
    S(16, DataBuffer.TYPE_USHORT, DataBufferUShort.class),
    T(32, DataBuffer.TYPE_INT, DataBufferInt.class);
    private final short depth;
    private final short bytes;
    private final int dataType;
    private final Class<? extends DataBuffer> bufferType;

    DepthEntry(int depth, int dataType, Class<? extends DataBuffer> bufferType) {
        this.depth = (short) depth;
        this.bytes = (short) (depth / 8);
        this.dataType = dataType;
        this.bufferType = bufferType;
    }

    @Override
    public short getValue() {
        return depth;
    }

    public int getBytes() {
        return bytes;
    }

    public int getDataType() {
        return dataType;
    }

    public static DepthEntry of(short depth) {
        switch (depth) {                                                                     //@formatter:off
            case 1:  return O;
            case 8:  return E;
            case 16: return S;
            case 32: return T;
            default:
                throw new IllegalArgumentException("No such depth possible as " + depth);    //@formatter:on
        }
    }

    public static DepthEntry of(BufferedImage image) {
        Class<? extends DataBuffer> clazz = image.getRaster().getDataBuffer().getClass();

        if (image.getSampleModel() instanceof SinglePixelPackedSampleModel) {
//            assuming it's RGB (3 component only)
            if (clazz.equals(S.bufferType)) return E; // not really, right?
            if (clazz.equals(T.bufferType)) return E;
            throw new UnsupportedOperationException("Contact developer.");
        }

        if (clazz.equals(E.bufferType)) return E;
        if (clazz.equals(S.bufferType)) return S;
        if (clazz.equals(T.bufferType)) return T;
        return null;
    }

    @Override
    public String toString() {
        return "" + depth;
    }
}
