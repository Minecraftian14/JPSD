package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.FileHeaderData.FileVersion;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

/**
 * Total bytes = 26
 * <p>
 * 4 bytes Signature *= 8BPS
 * 2 bytes Version   *= 1 or 2. If 1 file format is PSD else if 2 file format is PSB.
 * 6 bytes Reserved  *= all are 0
 * 2 bytes #Channels  = [1-56]
 * 4 bytes Height     = If PSD [1, 30k] else If PSB [1, 300k]
 * 4 bytes Width      = If PSD [1, 30k] else If PSB [1, 300k]
 * 2 bytes Depth      = {1, 8, 16, 32} // The number of bits per channel.
 * 2 bytes Color Mode = {Bitmap:0, Grayscale:1, Indexed:2, RGB:3, CMYK:4, Multichannel:7, Duotone:8, Lab:9}
 */
public class FileHeaderSectionIO extends SectionIO<FileHeaderData> {

    public static final int BYTES = 26;

    public FileHeaderSectionIO() {
        super(false);
    }

    public FileHeaderData read(DataReader reader) throws IOException {

        //@formatter:off
                          reader.verifySignature       (PSDFileReader.FILE_SIGNATURE_8BPS);
        int   version   = reader.verifyShortVersion    (FileVersion.PSD.getValue(), FileVersion.PSB.getValue());
                          reader.verifyZeros           (6);
        short channels  = reader.stream.readShort      ();
        int   height    = reader.stream.readInt        ();
        int   width     = reader.stream.readInt        ();
        short depth     = reader.stream.readShort      ();
        short colorMode = reader.stream.readShort      ();
        //@formatter:on

        checkBytesCount(BYTES, reader.stream.getStreamPosition());

        FileVersion fileVersion = FileVersion.of(version);
        return new FileHeaderData(fileVersion, channels, height, width, depth, colorMode);
    }

    @Override
    public void write(DataWriter writer, FileHeaderData data) {

        //@formatter:off

        writer.writeBytes  (PSDFileReader.FILE_SIGNATURE_8BPS);
        writer.writeShort  (data.getVersion().getValue());
        writer.writeZeros  (6);
        writer.writeShort  (data.getChannels());
        writer.writeInt    (data.getHeight());
        writer.writeInt    (data.getWidth());
        writer.writeShort  (data.getDepth());
        writer.writeShort  (data.getColorMode().getValue());

        //@formatter:on

    }

}
