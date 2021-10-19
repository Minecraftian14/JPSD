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
    public void write(DataWriter writer, ThumbnailRBlock data) {
//        writer.writeInt(data.getFormat().getValue());
//        writer.writeInt(data.getWidth());
//        writer.writeInt(data.getHeight());
//        writer.writeInt(data.getWidthBytes());
//        writer.writeInt(data.getTotalSize());
//        writer.writeInt(data.getCompressedSize());
//        writer.writeShort(data.getBitsPerPixel());
//        writer.writeShort(data.getNumberOfPlanes());
//        writer.writeBytes(data.getImageData());
    }
}
