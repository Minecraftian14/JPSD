package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.ImageMakerStudio;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.sections.ImageDataIO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
        return new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data" + res));
    }

    static FileImageInputStream get(Path res) throws IOException {
        return new FileImageInputStream(res.toFile());
    }

    static String file(String resnm) {
        String s = System.getProperty("user.dir") + "/src/test/test_data" + resnm;
        System.err.println(s);
        return s;
    }

    @BeforeAll
    static void beforeAll() {
        PSDFileReader.setDebuggingMode(true);
    }

    @Test
    void testThatWeCanRetrieveResUsingThisMethod() throws IOException {
        get(resources[3]);
    }

    @Test
    void reading() throws IOException {

        FileImageInputStream in = get(resources[3]);

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

        SectionIO<ImageData> IMAGE_DATA_IO = new ImageDataIO(fhd);
        ImageData id = IMAGE_DATA_IO.read(reader);
        pj(id.toString());

        System.out.println("reader.getPosition() = " + reader.stream.getStreamPosition());

    }

    @Test
    @Disabled("It's disabled because, duh I have somewhere like 12 GBs of PSD files to be tested!")
    void readAllCompositionsDecoderMethod() throws IOException {
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder();

        Files.walk(new File(file("")).toPath(), 1)
                .filter(path -> path.toString().endsWith(".psd") || path.toString().endsWith(".psb"))
                .map(path -> {
                    builder.setLength(0);
                    System.out.println("Current file: " + builder.append(path));
                    return get(() -> get(path));
                })
                .filter(Objects::nonNull)
                .map(iis -> get(() -> new PSDFileReader(iis)))
                .filter(Objects::nonNull)
                .map(psdFileReader -> get(() -> ImageMakerStudio.createImage(psdFileReader)))
//                .forEach(ints -> System.out.println(Arrays.toString(ints)));
                .filter(Objects::nonNull)
                .forEach(image -> run(() -> ImageIO.write(image, "PNG", new File(file("/out/" + getName(builder) + "_" + count.getAndIncrement() + ".png")))));
    }

    @Test
    @Disabled("It's disabled because, duh I have somewhere like 12 GBs of PSD files to be tested!")
    void readAllLayersDecoderMethod() throws IOException {
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder();

        Files.walk(new File(file("")).toPath(), 1)
                .filter(path -> path.toString().endsWith(".psd") || path.toString().endsWith(".psb"))
                .map(path -> {
                    builder.setLength(0);
                    System.out.println("Current file: " + builder.append(path));
                    return get(() -> get(path));
                })
                .filter(Objects::nonNull)
                .map(iis -> get(() -> new PSDFileReader(iis)))
                .filter(Objects::nonNull)
                .map(psdFileReader -> new SimpleEntry<>(psdFileReader, psdFileReader.getLayerAndMaskData()))
//                .forEach(System.out::println);
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().justGetALayerInfo()))
                .filter(pair -> pair.getValue() != null)
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().getLayerRecords()))
                .flatMap(pair -> Stream.of(pair.getValue()).map(record -> new SimpleEntry<>(pair.getKey(), record)))
                .map(pair -> ImageMakerStudio.createImage(pair.getValue(), pair.getKey()))
                .forEach(image -> run(() -> ImageIO.write(image, "PNG", new File(file("/out/" + getName(builder) + "_" + count.getAndIncrement() + ".png")))));
    }

    private String getName(StringBuilder builder) {
        return builder.substring(builder.lastIndexOf("\\") + 1, builder.lastIndexOf("."));
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

    public static <T> T get(DangerousSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public static void run(DangerousRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public interface DangerousSupplier<T> {
        T get() throws Throwable;
    }

    public interface DangerousRunnable {
        void run() throws Throwable;
    }

}