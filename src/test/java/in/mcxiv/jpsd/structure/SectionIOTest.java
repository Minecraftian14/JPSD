package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.data.sections.ColorModeData;
import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.ImageResourcesData;
import in.mcxiv.jpsd.data.sections.LayerAndMaskData;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.PSDFileReader;
import org.junit.jupiter.api.Test;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SectionIOTest {

    static String[] resources = {
            "/2x2_Red.psd",
            "/2x2_Green.psd",
            "/2x2_Yellow_(Reg+Green).psd",
            "/2x2_Yellow_over_2x4_Red_centered_(2_layers).psd",
            "/test_files/custom.psd",
            "/test_files/10x11.psb",
    };

    static FileImageInputStream get(String res) throws IOException {
        try {
            return new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data" + res));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void testThatWeCanRetrieveResUsingThisMethod() throws IOException {
        get(resources[3]);
    }

    @Test
    void reading() throws IOException {

        FileImageInputStream in = get(resources[5]);

        System.out.println("in.available() = " + in.length());

        DataReader reader = new DataReader(in);

        PSDFileReader.unknownBytesStrategy.action = PSDFileReader.UnknownBytesStrategy.Action.ExcludeData;

        FileHeaderData fhd = SectionIO.FILE_HEADER_SECTION.read(reader);
        pj(fhd.toString());

        ColorModeData cmd = SectionIO.COLOR_MODE_DATA_SECTION.read(reader);
        pj(cmd.toString());

        ImageResourcesData ird = SectionIO.IMAGE_RESOURCES_DATA_SECTION.read(reader);
        pj(ird.toString());

        LayerAndMaskData lmd;
        if (fhd.getVersion().isLarge())
            lmd = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSB.read(reader);
        else lmd = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSD.read(reader);
        pj(lmd.toString());

//        SectionIO<ImageData> IMAGE_DATA_IO = new ImageDataIO(fhd);
//        ImageData id = IMAGE_DATA_IO.read(reader);
//        pj(id.toString());

        System.out.println("reader.getPosition() = " + reader.stream.getStreamPosition());

    }

    @Test
    void writing() {
/*

        InputStream in = FileHeaderSectionIOTest.class.getResourceAsStream("/2x2_Yellow_(Reg+Green).psd");
        DataReader reader = new DataReader(in);

        FileHeaderData fhd = null;
        try {
            fhd = SectionIO.FILE_HEADER_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ColorModeData cmd = null;
        try {
            cmd = SectionIO.COLOR_MODE_DATA_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResourcesData ird = null;
        try {
            ird = SectionIO.IMAGE_RESOURCES_DATA_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataWriter writer = new DataWriter(out);

        SectionIO.FILE_HEADER_SECTION.write(writer, fhd);
        SectionIO.COLOR_MODE_DATA_SECTION.write(writer, cmd);
        SectionIO.IMAGE_RESOURCES_DATA_SECTION.write(writer, ird);

        in = new ByteArrayInputStream(out.toByteArray());
        reader = new DataReader(in);

        FileHeaderData fhd2 = null;
        try {
            fhd2 = SectionIO.FILE_HEADER_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ColorModeData cmd2 = null;
        try {
            cmd2 = SectionIO.COLOR_MODE_DATA_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResourcesData ird2 = null;
        try {
            ird2 = SectionIO.IMAGE_RESOURCES_DATA_SECTION.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(fhd.toString(), fhd2.toString());
        Assertions.assertEquals(cmd.toString(), cmd2.toString());
        Assertions.assertEquals(ird.toString(), ird2.toString());
*/

    }

    public static void pj(String str) {
        char[] chars = str.toCharArray();
        StringBuilder b = new StringBuilder();
        int depth = 0;

        for (char c : chars) {
            if (c == '{' || c == '[') {
                b.append(" {\n");
                depth++;
                for (int i = 0; i < depth; i++, b.append("  ")) ;
            } else if (c == '}' || c == ']') {
                b.append("\n");
                depth--;
                for (int i = 0; i < depth; i++, b.append("  ")) ;
                b.append("}");
            } else if (c == ',') {
                b.append("\n");
                for (int i = 0; i < depth - 1; i++, b.append("  ")) ;
                b.append(" ");
            } else {
                b.append(c);
            }
        }

        System.out.println(b);
    }

}