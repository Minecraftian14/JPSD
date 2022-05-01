package in.mcxiv.jpsd.data.layer;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.OverlayColorComponents;
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
    private OverlayColorComponents color;
    private short opacity;
    private Kind kind;

    public GlobalLayerMaskInfo(int length, OverlayColorComponents color, short opacity, Kind kind) {
        this.length = length;
        this.color = color;
        this.opacity = opacity;
        this.kind = kind;
    }

    public OverlayColorComponents getColor() {
        return color;
    }

    public void setColor(OverlayColorComponents color) {
        this.color = color;
    }

    public short getOpacity() {
        return opacity;
    }

    public void setOpacity(short opacity) {
        this.opacity = opacity;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
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
