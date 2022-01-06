package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.ThumbnailRBlock;
import in.mcxiv.jpsd.data.resource.types.ThumbnailRBlock.Format;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class ThumbnailRBlockIO extends ImageResourceBlockIO<ThumbnailRBlock> {

    /**
     * @see ThumbnailRBlock#isOldFormat
     */
    public final ImageResourceID id;

    public ThumbnailRBlockIO(boolean isOldFormat) {
        super(true);
        this.id = isOldFormat ? ImageResourceID.Thumbnail4 : ImageResourceID.Thumbnail5;
    }

    @Override
    public ThumbnailRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        if (this.id != id)
            throw new IllegalSignatureException("Given id must be one of ImageResourceID#Thumbnail4 or ImageResourceID#Thumbnail5");

        //@formatter:off
        Format  format          = Format.of(reader.stream.readInt());
        int     width           =           reader.stream.readInt();
        int     height          =           reader.stream.readInt();
        int     widthBytes      =           reader.stream.readInt();
        int     totalSize       =           reader.stream.readInt();
        int     compressedSize  =           reader.stream.readInt();
        int     bitsPerPixel    =           reader.stream.readUnsignedShort();
        int     numberOfPlanes  =           reader.stream.readUnsignedShort();
        byte[]  imageData       =           reader       .readBytes((int) (blockLength - ThumbnailRBlock.HEADER_SIZE), true);
        //@formatter:on

        return new ThumbnailRBlock(id, pascalString, blockLength, format, width, height, widthBytes, totalSize, compressedSize, bitsPerPixel, numberOfPlanes, imageData);
    }

    @Override
    public void write(DataWriter writer, ThumbnailRBlock data) throws IOException {
        writer.stream.writeInt(data.getFormat().getValue());
        writer.stream.writeInt(data.getWidth());
        writer.stream.writeInt(data.getHeight());
        writer.stream.writeInt(data.getWidthBytes());
        writer.stream.writeInt(data.getTotalSize());
        writer.stream.writeInt(data.getCompressedSize());
        writer.stream.writeShort(data.getBitsPerPixel());
        writer.stream.writeShort(data.getNumberOfPlanes());
        writer.writeBytes(data.getImageData());
    }
}
