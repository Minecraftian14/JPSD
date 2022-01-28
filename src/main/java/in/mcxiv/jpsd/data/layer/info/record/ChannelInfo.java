package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.primitive.ShortEntry;

public class ChannelInfo {

    public enum ChannelID implements ShortEntry {
        Channel1(0), Channel2(1), Channel3(2), Channel4(3), Channel5(4), Channel6(5),
        TransparencyMask(-1), UserSuppliedMask(-2), RealUserSuppliedMask(-3);

        private final short value;

        ChannelID(int value) {
            this.value = (short) value;
        }

        @Override
        public short getValue() {
            return value;
        }

        public static ChannelID of(short value) {
            return ShortEntry.of(value, values());
        }

        @Override
        public String toString() {
            return "ChannelID:" + name();
        }

        public boolean isColor() {
            return value > -2;
        }

        public boolean isAlpha() {
            return value == -1;
        }
    }

    private ChannelID id;
    private long dataLength; // i PSD 4 bytes e i PSB 8 bytes
    private ChannelImageData data;

    public ChannelInfo(ChannelID id, long dataLength) {
        this.id = id;
        this.dataLength = dataLength;
    }

    public ChannelInfo(ChannelID id, ChannelImageData data) {
        this.id = id;
        this.data = data;
        this.dataLength = data.getData().length + 2;
    }

    public long getDataLength() {
        return dataLength;
    }

    public void setData(ChannelImageData data) {
        this.data = data;
    }

    public ChannelID getId() {
        return id;
    }

    public ChannelImageData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "id=" + id +
                ", dataLength=" + dataLength +
                ", data=" + data +
                '}';
    }
}
