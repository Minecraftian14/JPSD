package in.mcxiv.jpsd.data.layer;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.primitive.ByteEntry;

public class GlobalLayerMaskInfo extends DataObject {

    public enum Kind implements ByteEntry {
        ColorSelected_Inverted(0), ColorProtected(1), UseValueStoredPerLayer(128);

        private final byte value;

        Kind(int i) {
            value = (byte) i;
        }

        public static Kind of(byte value) {
            for (Kind kind : values()) if (kind.value == value) return kind;
            throw new RuntimeException("Unknown kind " + value);
        }

        @Override
        public byte getValue() {
            return value;
        }

    }

    private int length;
    private ColorComponents color;
    private short opacity;
    private Kind kind;

    public GlobalLayerMaskInfo(int length, ColorComponents color, short opacity, Kind kind) {
        this.length = length;
        this.color = color;
        this.opacity = opacity;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "GlobalLayerMaskInfo{" +
                "length=" + length +
                ", color=" + color +
                ", opacity=" + opacity +
                ", kind=" + kind +
                '}';
    }
}
