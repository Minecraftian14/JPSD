package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.data.sections.FileHeaderData;

public enum DepthEntry implements ShortEntry {
    O(1), E(8), S(16), T(32);
    private final short depth;
    private final short bytes;

    DepthEntry(int depth) {
        this.depth = (short) depth;
        this.bytes = (short) (depth / 8);
    }

    @Override
    public short getValue() {
        return depth;
    }

    public int getBytes() {
        return bytes;
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

    @Override
    public String toString() {
        return "" + depth;
    }
}
