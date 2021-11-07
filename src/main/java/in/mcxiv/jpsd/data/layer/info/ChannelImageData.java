package in.mcxiv.jpsd.data.layer.info;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.Compression;

public class ChannelImageData extends DataObject {

    private Compression compression;
    private byte[] data;

    public ChannelImageData(Compression compression, byte[] data) {
        this.compression = compression;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChannelImageData{" +
                "compression=" + compression +
                ", data.length=" + data.length +
                '}';
    }

    public Compression getCompression() {
        return compression;
    }

    public byte[] getData() {
        return data;
    }

}
