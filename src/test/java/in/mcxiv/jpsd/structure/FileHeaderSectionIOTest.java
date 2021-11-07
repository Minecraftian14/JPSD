package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.sections.FileHeaderSectionIO;
import org.junit.jupiter.api.Test;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileHeaderSectionIOTest {

    @Test
    void testAllProperties() throws IOException {
        SectionIO<FileHeaderData> io = SectionIO.FILE_HEADER_SECTION;
        FileImageInputStream stream = new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data/2x4_Red.psd"));

        FileHeaderData data = io.read(new DataReader(stream));

        assertEquals(data.getVersion(), FileVersion.PSD);
        assertEquals(data.getNumberOfChannels(), 3);
        assertEquals(data.getHeight(), 2);
        assertEquals(data.getWidth(), 4);
        assertEquals(data.getDepth(), 8);
        assertEquals(data.getColorMode(), ColorMode.RGB);
    }

    @Test
    void testDimensions() throws IOException {
        FileHeaderSectionIO io = new FileHeaderSectionIO();
        FileImageInputStream stream = new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data/2x4_Red.psd"));
        FileHeaderData data = io.read(new DataReader(stream));

        assertEquals(data.getHeight(), 2);
        assertEquals(data.getWidth(), 2);
    }

    @Test
    void testIO() {
        SectionIO<FileHeaderData> io = SectionIO.FILE_HEADER_SECTION;

        // Writing to cache
        FileHeaderData data = new FileHeaderData(2, 39, 1234, 4321, 32, 9);
        ByteArrayOutputStream cache = new ByteArrayOutputStream();
        io.write(new DataWriter(cache), data);

        // Reading from cache
//        FileHeaderData new_data = io.read(new DataReader(new ByteArrayInputStream(cache.toByteArray())));

        // Test
//        assertEquals(data, new_data);

    }
}