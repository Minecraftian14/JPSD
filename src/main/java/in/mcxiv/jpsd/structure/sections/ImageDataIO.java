package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.common.Compression;
import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.ImageData;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.RawDataDecoder;
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
        int[] imgData = RawDataDecoder.decode(compression, reader, header);

        return new ImageData(imgData);
    }

    @Override
    public void write(DataWriter writer, ImageData imageData) throws IOException {

    }

}
