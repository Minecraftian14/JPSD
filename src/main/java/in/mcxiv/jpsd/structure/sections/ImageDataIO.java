package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.ImageData;
import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class ImageDataIO extends SectionIO<ImageData> {

    private FileHeaderData header;

    public ImageDataIO(FileHeaderData header) {
        super(true);
        this.header = header;
    }

    @Override
    public ImageData read(DataReader reader) throws IOException {

        Compression compression = Compression.of(reader.stream.readShort());
        int height = header.getHeight();
        int width = header.getWidth();
        short numberOfChannels = header.getChannels();

        byte[][] imgData = new byte[numberOfChannels][];

        switch (compression) {
            case RLE_Compression:
                int numberOfLines = height * numberOfChannels;
                short[] scanLengths = new short[numberOfLines];
                for (int i = 0; i < numberOfLines; scanLengths[i++] = reader.stream.readShort()) ;


                for (int channelIdx = 0; channelIdx < numberOfChannels; channelIdx++) {

                    byte[] pixData = new byte[width * height];
                    int pixelsRead = 0; // Total pixels filled in pixData till now.

                    byte[] buffer = new byte[2 * width]; // Just a buffer to be able to read the "variable" lengths of an RLE scan line.
                    int lineIndex = channelIdx * height; //

                    for (int rowIdx = 0; rowIdx < height; rowIdx++) {

                        int scanLength = scanLengths[lineIndex++];
                        reader.stream.read(buffer, 0, scanLength);

                        uncompressRLEScannedLine(buffer, 0, pixData, pixelsRead, scanLength);
                        pixelsRead += width;

                    }

                    imgData[channelIdx] = pixData;
                }
                break;

            default:
                for (int channelIdx = 0; channelIdx < numberOfChannels; channelIdx++)
                    imgData[channelIdx] = reader.readBytes(width * height, true);

        }

        return new ImageData(imgData);
    }

    @Override
    public void write(DataWriter writer, ImageData imageData) {

    }

    public static void uncompressRLEScannedLine(byte[] source, int sourceOffset, byte[] destination, int destinationOffset, int length) {

        for (int
             srcIdx = sourceOffset,
             dstIdx = destinationOffset,
             endCap = srcIdx + length;
             srcIdx < endCap; ) {

            byte count = source[srcIdx++]; // get the number of repetitions and then increment srcIdx for further reading.
            byte value = source[srcIdx++]; // get the byte to be repeated 'count' times and then increment srcIdx for next loop round.

            for (int repIdx = 0; repIdx < count; repIdx++, destination[dstIdx++] = value) ;
        }
    }

}
