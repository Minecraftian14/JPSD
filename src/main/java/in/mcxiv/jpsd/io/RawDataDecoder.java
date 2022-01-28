package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.common.ImageMeta;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.data.sections.FileHeaderData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

public class RawDataDecoder {

    public static int[] decode(Compression compression, DataReader reader, FileHeaderData fhd) throws IOException {
        if (reader.stream.length() == -1) System.err.println("WHYYYYYYY!!!");
        byte[] data = new byte[(int) (reader.stream.length() - reader.stream.getStreamPosition())];
        reader.stream.readFully(data, 0, data.length);
        return decode(compression, data, fhd);
    }

    public static int[] decode(Compression compression, byte[] compressedData, FileHeaderData fhd) {
        return decode(compression, compressedData, fhd.toImageMeta());
    }

    public static int[] decode(Compression compression, byte[] compressedData, ImageMeta fhd) {
        int height = fhd.getHeight();
        int width = fhd.getWidth();
        DepthEntry depth = fhd.getDepthEntry();
        try {
            int[] data = new int[height * width * fhd.getChannels()];
            switch (compression) {
                case Raw_Data:
                    DataReader reader = new DataReader(compressedData);
                    for (int i = 0; i < data.length; i++)
                        data[i] = reader.readByBits(depth);
                    break;

                case RLE_Compression:
                    DataReader rleData = new DataReader(compressedData);

                    /* If we ever require scanLengths for a better algo towards reading raw data.
                    int[] scanLengths = new int[height];
                    if (fhd.isLarge())
                        for (int i = 0; i < height; scanLengths[i++] = rleData.stream.readInt()) ;
                    else for (int i = 0; i < height; scanLengths[i++] = rleData.stream.readUnsignedShort()) ;
                    */

                    if (fhd.isLarge())
                        for (int i = 0; i < height; i++) rleData.stream.readInt();
                    else for (int i = 0; i < height; i++) rleData.stream.readUnsignedShort();

                    DataWriter rawWriter = new DataWriter();

                    while (rleData.stream.getStreamPosition() < compressedData.length) {
                        int len = (byte) rleData.stream.read();
                        if (len >= 0) {
                            rawWriter.writeBytes(rleData.readBytes(++len, true));
                        } else if (len != -128) {
                            rawWriter.fill(1 - len, rleData.stream.readByte());
                        }
                    }

                    byte[] rawBytes = rawWriter.toByteArray();
                    DataReader rawData = new DataReader(rawBytes);

                    // Often there are extra bytes left unread at the end of the whole raw data block.
                    // **It's not about bytes at the end on each line, but in the extreme end of the file.**
                    // That's why we skip just as many bytes as extra. (else it creates a funny RGB->BGR effects for first ~1000 pixels along with a translation-ish effect in x-axis.)
                    int spareBytes = rawBytes.length - data.length * depth.getBytes();
                        PSDConnection.out.println("Spare bytes = " + spareBytes);
                    if (spareBytes > 0) {
                        rawData.stream.skipBytes(spareBytes);
                    } /*else {
                        System.out.println("Boom");
                    }*/

                    for (int i = 0; i < data.length; data[i++] = rawData.readByBits(depth)) ;
                    break;

                case ZIP:
                case ZIP_With_Prediction:

                    // TODO: I think we are near... Currently the data retrieved looks like a bunch of dots aligned by the edges.
                    InflaterInputStream stream = new InflaterInputStream(new ByteArrayInputStream(compressedData));
                    DataReader zipReader = new DataReader(stream);
                    for (int i = 0; i < data.length; data[i++] = zipReader.readByBits(depth)) ;
                    break;
            }

            return decode(data, fhd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    public static int[] decode(int[] data, ImageMeta fhd) {
        if (fhd.isInvertRequired())
            invert(data, fhd.getDepthEntry());

        return data;
    }

    public static byte[] encode(Compression compression, int[] data, FileHeaderData fhd) {
        return encode(compression, data, fhd.toImageMeta());
    }

    public static byte[] encode(Compression compression, int[] data, ImageMeta fhd) {
        int height = fhd.getHeight();
        int width = fhd.getWidth();
        DepthEntry depth = fhd.getDepthEntry();
        try {
            assert data.length == height * width * fhd.getChannels();
//            byte[] bytes = new byte[data.length * depth.getValue()];
            switch (compression) {
                case Raw_Data:
                    DataWriter writer = new DataWriter();
                    for (int i = 0; i < data.length; i++)
                        writer.writeByBits(depth, data[i]);
                    return writer.toByteArray();

                case RLE_Compression:
                case ZIP:
                case ZIP_With_Prediction:
                    throw new IllegalStateException();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private static void invert(int[] data, DepthEntry depth) {
        switch (depth) {
            case O:
                // I didn't understand the invert and iffset part
                break;
            case E:
                for (int i = 0; i < data.length; i++)
                    data[i] = (byte) (0xff - data[i] & 0xff);
                break;
            case S:
                for (int i = 0; i < data.length; i++)
                    data[i] = (short) (0xffff - data[i] & 0xffff);
                break;
            case T:
                for (int i = 0; i < data.length; i++)
                    data[i] = 0xffff_ffff - data[i];
                break;
        }
    }

}
