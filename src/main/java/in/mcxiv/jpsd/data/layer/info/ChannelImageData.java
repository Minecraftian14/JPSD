package in.mcxiv.jpsd.data.layer.info;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.Compression;

public class ChannelImageData extends DataObject {

    private Compression compression;

    /**
     * if compression == {@link Compression#Raw_Data}
     *      the image data is just the raw image data,
     *      whose size is calculated as
     *      (LayerBottom-LayerTop)* (LayerRight-LayerLeft)
     *      (Referenced from {@link LayerRecord#getContent()}
     *
     * if compression == {@link Compression#RLE_Compression}
     *      the image data starts with the byte counts for all
     *      the scan lines in the channel (LayerBottom-LayerTop),
     *      with each count stored as a two-byte value.
     *      Note, in case of PSB, each count stored as a four
     *      byte value.
     *      The RLE compressed data follows, with each scan line
     *      compressed separately. The RLE compression is the
     *      same compression algorithm used by the Macintosh
     *      ROM routine PackBits, and the TIFF standard.
     *      If the layer's size, and therefore the data, is odd,
     *      a pad byte will be inserted at the end of the row.
     *      If the layer is an adjustment layer, the channel
     *      data is undefined (probably all white.)
     *
     */
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
}
