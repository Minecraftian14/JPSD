package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.UnknownRBlock;
import in.mcxiv.jpsd.data.resource.types.*;
import in.mcxiv.jpsd.data.sections.ImageResourcesData;
import in.mcxiv.jpsd.exceptions.UnknownByteBlockException;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;
import in.mcxiv.jpsd.structure.resource.type.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Total bytes = 4 or 4+ <br />
 * <p> <br />
 * 4 bytes Section size s= W <br />
 * s bytes IRD Blocks    = Blocks of bytes representing the different types of {@link ImageResourcesData}. Note, that a list of all possible types is included in {@link ImageResourceID}. However, only a few are supported by the program. <br />
 * <p> <br />
 * Every {@link ImageResourcesData} consists of <br />
 * <p> <br />
 * 4 bytes Signature *= 8BIM <br />
 * 2 bytes Identity   = A unique identifier as contained in {@link ImageResourceID} <br />
 * x bytes Padding    = A pascal string added to make the size of block even. Note, that the shortest pascal string consists of 2 bytes. Therefore x = N - {1} <br />
 * 4 bytes IRD Size  s= Length of upcoming bytes contsining the byte data for this block. <br />
 * s bytes IRD bytes  = The data enclosed by the specialised IRD. <br />
 */
public class ImageResourcesSectionIO extends SectionIO<ImageResourcesData> {

    //@formatter:off
    private static final ImageResourceBlockIO<  AlphaChannelsNamesRBlock    > ALPHA_CHANNELS_IO         = new AlphaChannelsNamesRBlockIO();
    private static final ImageResourceBlockIO<  ColorSamplersRBlock         > COLOR_SAMPLERS_IO         = new ColorSamplersRBlockIO     ();
    private static final ImageResourceBlockIO<  DisplayInfoRBlock           > DISPLAY_INFO_IO           = new DisplayInfoRBlockIO       ();
    private static final ImageResourceBlockIO<  GridAndGuidesRBlock         > GRID_AND_GUIDES_R_IO      = new GridAndGuidesRBlockIO     ();
    private static final ImageResourceBlockIO<  PixelAspectRatioRBlock      > PIXEL_ASPECT_RATIO_IO     = new PixelAspectRatioRBlockIO  ();
    private static final ImageResourceBlockIO<  PrintFlagsInfoRBlock        > PRINT_FLAGS_INFO_IO       = new PrintFlagsInfoRBlockIO    ();
    private static final ImageResourceBlockIO<  PrintFlagsRBlock            > PRINT_FLAGS_IO            = new PrintFlagsRBlockIO        ();
    private static final ImageResourceBlockIO<  PrintScaleRBlock            > PRINT_SCALE_IO            = new PrintScaleRBlockIO        ();
    private static final ImageResourceBlockIO<  RawImageRBlock              > RAW_IMAGE_IO              = new RawImageRBlockIO          ();
    private static final ImageResourceBlockIO<  ResolutionRBlock            > RESOLUTION_IO             = new ResolutionRBlockIO        ();
    private static final ImageResourceBlockIO<  ThumbnailRBlock             > THUMBNAIL_4_IO            = new ThumbnailRBlockIO         (true);
    private static final ImageResourceBlockIO<  ThumbnailRBlock             > THUMBNAIL_5_IO            = new ThumbnailRBlockIO         (false);
    private static final ImageResourceBlockIO<  UnicodeAlphaNamesRBlock     > UNICODE_ALPHA_NAMES_IO    = new UnicodeAlphaNamesRBlockIO ();
    private static final ImageResourceBlockIO<  VersionInfoRBlock           > VERSION_INFO_IO           = new VersionInfoRBlockIO       ();
    //@formatter:on


    public ImageResourcesSectionIO() {
        super(true);
    }

    @Override
    public ImageResourcesData read(DataReader reader) throws IOException {

        // Length of the bytes which contain all the blocks piled up.
        long sectionLength = reader.stream.readUnsignedInt();

        // Here that mess starts (the very next byte is the first byte of the mess)
        long mark = reader.stream.getStreamPosition();

        // Here is the last byte belonging to that mess.
        long endPosition = mark + sectionLength;

        // We might just maintain an array to store upcoming blocks too
        List<ImageResourceBlock> blocks = new ArrayList<>();

        // While (we can still expect some data coming up)...
        while (reader.stream.getStreamPosition() < endPosition) {
            // ...we should try reading a block.

            // NOTE that now we are not reading the resource data section
            // We are reading the data which is generic/common to every resource data block inside this section.
            reader.verifySignature(PSDFileReader.RESOURCE, PSDFileReader.RESOURCE_IMAGE_READY, PSDFileReader.RESOURCE_PHOTO_DELUXE, PSDFileReader.RESOURCE_LIGHT_ROOM, PSDFileReader.RESOURCE_DCSR);

            short id_value = reader.stream.readShort();
            String pascalString = reader.readPascalStringEvenlyPadded();

            long blockLength = reader.stream.readUnsignedInt();

            boolean paddingRequired = blockLength % 2 == 1;

            // Look out for special cases which cant be solved using the enum ImageResourceID
            if (id_value >= 2000 && id_value <= 2997) {

                PathInformationRBlockIO path_IO = new PathInformationRBlockIO(id_value);
                blocks.add(path_IO.read(reader, ImageResourceID.PathInformation, pascalString, blockLength));

                // It should mostly be always false.
                System.err.println(paddingRequired);
                if (paddingRequired)
                    reader.stream.readByte();

                continue;
            }

            ImageResourceID id = ImageResourceID.of(id_value);

            switch (id) {
                case AlphaChannelNames:
                    blocks.add(ALPHA_CHANNELS_IO.read(reader, id, pascalString, blockLength));
                    break;
                case ColorSamplersResource:
                    blocks.add(COLOR_SAMPLERS_IO.read(reader, id, pascalString, blockLength));
                    break;
                case DisplayInfo:
                    blocks.add(DISPLAY_INFO_IO.read(reader, id, pascalString, blockLength));
                    break;
                case GridAndGuides:
                    blocks.add(GRID_AND_GUIDES_R_IO.read(reader, id, pascalString, blockLength));
                    break;
                case PixelAspectRatio:
                    blocks.add(PIXEL_ASPECT_RATIO_IO.read(reader, id, pascalString, blockLength));
                    break;
                case PrintFlagsInfo:
                    blocks.add(PRINT_FLAGS_INFO_IO.read(reader, id, pascalString, blockLength));
                    break;
                case PrintFlags:
                    blocks.add(PRINT_FLAGS_IO.read(reader, id, pascalString, blockLength));
                    break;
                case PrintScale:
                    blocks.add(PRINT_SCALE_IO.read(reader, id, pascalString, blockLength));
                    break;
                case RawImageMode:
                    blocks.add(RAW_IMAGE_IO.read(reader, id, pascalString, blockLength));
                    break;
                case Resolution:
                    blocks.add(RESOLUTION_IO.read(reader, id, pascalString, blockLength));
                    break;
                case Thumbnail4:
                    blocks.add(THUMBNAIL_4_IO.read(reader, id, pascalString, blockLength));
                    break;
                case Thumbnail5:
                    blocks.add(THUMBNAIL_5_IO.read(reader, id, pascalString, blockLength));
                    break;
                case UnicodeAlphaNames:
                    blocks.add(UNICODE_ALPHA_NAMES_IO.read(reader, id, pascalString, blockLength));
                    break;
                case VersionInfo:
                    blocks.add(VERSION_INFO_IO.read(reader, id, pascalString, blockLength));
                    break;

//              Cases defined in specs exceptionally
//                case Slices:
//                case Vanishing Points?

//              And a few more cases for which I need a separate parser!
//                case IPTC_NAA_Record:
//                case ICCProfile:
//                case EXIFData1:
//                case XMPMetadata:

                default:
                    switch (unknownBytesStrategy.action) {
                        case ReadAll:
                            blocks.add(new UnknownRBlock(id, pascalString, blockLength, reader.readBytes((int) blockLength, true)));
                            break;
                        case ExcludeData:
                            blocks.add(new UnknownRBlock(id, pascalString, blockLength, null));
                        case Skip:
                            reader.stream.skipBytes(blockLength);
                            break;
                        case Quit:
                            throw new UnknownByteBlockException("Image Resource Section> Image Resource Block> " + id);
                    }
            }

            if (paddingRequired)
                reader.stream.readByte();

        }

        checkBytesCount(sectionLength, mark, reader.stream.getStreamPosition());

        return new ImageResourcesData(blocks.toArray(new ImageResourceBlock[0]));
    }

    @Override
    public void write(DataWriter writer, ImageResourcesData imageResourcesData) throws IOException {

        // Issue another writer. This is a highlevel writer.
        // We store just everything in here,
        // We keep this as separate so that we have to
        // write the data in the actual writer, we can write
        // the length before it.
        ByteArrayOutputStream allData = new ByteArrayOutputStream();
        DataWriter allDataWriter = new DataWriter(allData);

        for (ImageResourceBlock block : imageResourcesData.getBlocks()) {

            // Similar to allDataWriter, we create another write;
            // a low level writer to write data specific to only
            // the IRB blocks. And again, we do this to store the
            // length beforehand.
            ByteArrayOutputStream blockData = new ByteArrayOutputStream();
            DataWriter blockWriter = new DataWriter(blockData);

            switch (block.getIdentity()) {
                case GridAndGuides:
                    GRID_AND_GUIDES_R_IO.write(blockWriter, block);
                    break;
                case Thumbnail4:
                    THUMBNAIL_4_IO.write(blockWriter, block);
                    break;
                case Thumbnail5:
                    THUMBNAIL_5_IO.write(blockWriter, block);
                    break;
                case ColorSamplersResource:
                    COLOR_SAMPLERS_IO.write(blockWriter, block);
                    break;

                case VersionInfo:
                    VERSION_INFO_IO.write(blockWriter, block);
                    break;
                case UnicodeAlphaNames:
                    UNICODE_ALPHA_NAMES_IO.write(blockWriter, block);
                    break;
                case Resolution:
                    RESOLUTION_IO.write(blockWriter, block);
                    break;
                case AlphaChannelNames:
                    ALPHA_CHANNELS_IO.write(blockWriter, block);
                    break;
                case RawImageMode:
                    RAW_IMAGE_IO.write(blockWriter, block);
                    break;

                default:
                    continue;
            }
            byte[] blockBytes = blockData.toByteArray();

            allDataWriter.writeBytes(PSDFileReader.RESOURCE);
            allDataWriter.writeShort(block.getIdentity().getIdentity());
            allDataWriter.writePascalStringEvenlyPadded(block.getPascalString());
            allDataWriter.writeInt(blockBytes.length);
            allDataWriter.writeBytes(blockBytes);
            if (blockBytes.length % 2 == 1) allDataWriter.fillZeros(1);

        }

        byte[] addDataBytes = allData.toByteArray();

        writer.writeInt(addDataBytes.length);
        writer.writeBytes(addDataBytes);

    }
}
