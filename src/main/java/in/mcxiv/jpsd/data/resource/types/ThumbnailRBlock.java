package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.primitive.IntEntry;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class ThumbnailRBlock extends ImageResourceBlock {

    public enum Format implements IntEntry {
        kRawRGB,
        kJpegRGB;

        public static Format of(int format) {
            return values()[format];
        }

        @Override
        public int getValue() {
            return ordinal();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int HEADER_SIZE = 28;

    /**
     * Adobe Photoshop (version 5.0 and later) stores thumbnail using a JFIF thumbnail in RGB (red, green, blue) order for both Macintosh and Windows.
     * However, Adobe Photoshop (version 4.0) stored the thumbnail in the same format except the data section is BGR (blue, green, red).
     * <p>
     * Therefore, we have two separate resource ids to represent each format.
     * <p>
     * if isOldFormat -> Adobe Photoshop 4.0  -> {@link ImageResourceID#Thumbnail4} -> 1033
     * else           -> Adobe Photoshop 5.0+ -> {@link ImageResourceID#Thumbnail5} -> 1036
     */
    public final boolean isOldFormat;

    private Format format;
    private int width;
    private int height;
    private int widthBytes; // Padded row bytes = (width * bits per pixel + 31) / 32 * 4.
    private int totalSize; // Total size = widthBytes * height * planes
    private int compressedSize; // Size after compression. Used for consistency check.
    private int bitsPerPixel = 24;
    private int numberOfPlanes = 1;
    private byte[] imageData;

    public ThumbnailRBlock(ImageResourceID identity, String pascalString, long length, Format format, int width, int height, int widthBytes, int totalSize, int compressedSize, int bitsPerPixel, int numberOfPlanes, byte[] imageData) {
        super(identity, pascalString, length);
        switch (identity) {
            //@formatter:off
            case Thumbnail4: isOldFormat=true; break;
            case Thumbnail5:isOldFormat=false; break;
            default: throw new IllegalArgumentException("This is not a thumbnail ID "+identity.name());
            //@formatter:on
        }
        this.format = format;
        this.width = width;
        this.height = height;
        this.widthBytes = widthBytes;
        this.totalSize = totalSize;
        this.compressedSize = compressedSize;
        this.bitsPerPixel = bitsPerPixel;
        this.numberOfPlanes = numberOfPlanes;
        this.imageData = imageData;
    }

    public boolean isOldFormat() {
        return isOldFormat;
    }

    public Format getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidthBytes() {
        return widthBytes;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getCompressedSize() {
        return compressedSize;
    }

    public int getBitsPerPixel() {
        return bitsPerPixel;
    }

    public int getNumberOfPlanes() {
        return numberOfPlanes;
    }

    public byte[] getImageData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "ThumbnailRBlock{" +
                "isOldFormat=" + isOldFormat +
                ", format=" + format +
                ", width=" + width +
                ", height=" + height +
                ", widthBytes=" + widthBytes +
                ", totalSize=" + totalSize +
                ", compressedSize=" + compressedSize +
                ", bitsPerPixel=" + bitsPerPixel +
                ", numberOfPlanes=" + numberOfPlanes +
                ", imageData.length=" + imageData.length +
                '}';
    }
}
