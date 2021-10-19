package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.primitive.ShortEntry;

public class ChannelInfo {

    public enum ChannelID implements ShortEntry {
        Red(0), Green(1), Blue(2),
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
            return "ChannelID:"+name();
        }
    }

    private ChannelID id;
    private long dataLength; // i PSD 4 bytes e i PSB 8 bytes
    private ChannelImageData data;

    public ChannelInfo(ChannelID id, long dataLength) {
        this.id = id;
        this.dataLength = dataLength;
    }

    public long getDataLength() {
        return dataLength;
    }

    public void setData(ChannelImageData data) {
        this.data = data;
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
