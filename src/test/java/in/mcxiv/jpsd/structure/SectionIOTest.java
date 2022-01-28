package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.PSDDocument;
import in.mcxiv.jpsd.data.common.ImageMeta;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.ImageMakerStudio;
import in.mcxiv.jpsd.io.PSDConnection;
import in.mcxiv.jpsd.io.RawDataDecoder;
import in.mcxiv.jpsd.structure.sections.ImageDataIO;
import open.OpenSimplexNoise;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class githSectionIOTest {

    public static String[] resources = {
            /*0*/ "/2x2_Red.psd",
            /*1*/ "/2x2_Green.psd",
            /*2*/ "/2x2_Yellow_(Reg+Green).psd",
            /*3*/ "/2x2_Yellow_over_2x4_Red_centered_(2_layers).psd",
            /*4*/ "/custom rgb depth8.psd",
            /*5*/ "/custom rgb depth16.psd",
            /*6*/ "/custom rgb depth32.psd",
            /*7*/ "/IUGFCJCFUJG.psd",
            /*8*/ "/ImageWithMask.psd",
            /*9*/ "/test_files/custom.psd",
            /*10*/ "/test_files/10x11.psb",
    };

    public static FileImageInputStream get(String res) throws IOException {
        return new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data" + res));
    }

    public static FileImageOutputStream put(String res) throws IOException {
        return new FileImageOutputStream(new File(System.getProperty("user.dir") + "/src/test/test_data/out" + res));
    }

    public static FileImageInputStream get(Path res) throws IOException {
        return new FileImageInputStream(res.toFile());
    }

    public static String file(String resnm) {
        String s = System.getProperty("user.dir") + "/src/test/test_data" + resnm;
        System.err.println(s);
        return s;
    }

    @BeforeAll
    static void beforeAll() {
        PSDConnection.setDebuggingMode(true);
    }

    @Test
    void testThatWeCanRetrieveResUsingThisMethod() throws IOException {
        get(resources[3]);
    }

    @Test
    void reading() throws IOException {
        FileImageInputStream in = get(resources[8]);
        PSDConnection con = new PSDConnection(in);
        pj(con.toString());
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
                .map(iis -> get(() -> new PSDConnection(iis)))
                .filter(Objects::nonNull)
                .map(psdFileReader -> get(() -> ImageMakerStudio.toImage(psdFileReader)))
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
                .map(iis -> get(() -> new PSDConnection(iis)))
                .filter(Objects::nonNull)
                .map(psdFileReader -> new SimpleEntry<>(psdFileReader, psdFileReader.getLayerAndMaskData()))
//                .forEach(System.out::println);
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().justGetALayerInfo()))
                .filter(pair -> pair.getValue() != null)
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().getLayerRecords()))
                .flatMap(pair -> Stream.of(pair.getValue()).map(record -> new SimpleEntry<>(pair.getKey(), record)))
                .map(pair -> ImageMakerStudio.toImage(pair.getValue(), pair.getKey()))
                .forEach(image -> run(() -> ImageIO.write(image, "PNG", new File(file("/out/" + getName(builder) + "_" + count.getAndIncrement() + ".png")))));
    }

    @Test
    void readingMaskData() throws IOException {

        FileImageInputStream in = get(resources[8]);
        PSDConnection con = new PSDConnection(in);

        LayerRecord layerRecord = con.getLayerAndMaskData().getLayerInfo().getLayerRecords()[1];

        int w = layerRecord.getWidth();
        int h = layerRecord.getHeight();
        ImageMeta meta = new ImageMeta(w, h, con.getFileHeaderData().isLarge(), ColorMode.RGB, 1, con.getFileHeaderData().getDepthEntry());
        for (ChannelInfo channelInfo : layerRecord.getChannelInfo())
            System.out.printf("%30s : %d\n", channelInfo.getId().toString(), RawDataDecoder.decode(channelInfo.getData().getCompression(), channelInfo.getData().getData(), meta).length);

        ImageIO.write(ImageMakerStudio.toImageMask(layerRecord, con), "PNG", new File(file("/out/ImageWithMask_TheMask.png")));

    }

    private String getName(StringBuilder builder) {
        return builder.substring(builder.lastIndexOf("\\") + 1, builder.lastIndexOf("."));
    }

    @Test
    void writingASimpleImage() throws IOException {
        BufferedImage image = new BufferedImage(10, 20, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getHeight(); i++)
            for (int j = 0; j < image.getWidth(); j++)
                image.setRGB(j, i, new Color(i * 1f / image.getHeight(), (image.getHeight() - i) * 1f / image.getHeight(), j * 1f / image.getWidth(), (image.getWidth() - j) * 1f / image.getWidth()).getRGB());
        ImageIO.write(image, "PNG", new File(file("/out/writingASimpleImage.png")));
        PSDConnection reader = ImageMakerStudio.fromImage(image);
//        reader.getColorModeData().setData(new byte[]{104, 100, 114, 116, 0, 0, 0, 3, 62, 107, -123, 31, 0, 0, 0, 2, 0, 0, 0, 8, 0, 68, 0, 101, 0, 102, 0, 97, 0, 117, 0, 108, 0, 116, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, -1, 0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 65, -128, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 63, -128, 0, 0, 104, 100, 114, 97, 0, 0, 0, 6, 0, 0, 0, 0, 65, -96, 0, 0, 65, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, -128, 0, 0, 0, 0, 0, 0, 0, 0});
        pj(reader.toString());
        reader.write(put("/writingASimpleImage.psd"));
        new PSDConnection(put("/writingASimpleImage.psd"));
    }

    @Test
    void writingABilayeredImage() throws IOException {
        BufferedImage layer1 = new BufferedImage(10, 20, BufferedImage.TYPE_USHORT_555_RGB);
        for (int i = 0; i < layer1.getHeight(); i++)
            for (int j = 0; j < layer1.getWidth(); j++)
                layer1.setRGB(j, i, ((i + j) / 2) % 2 == 0 ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
        BufferedImage layer2 = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < layer2.getHeight(); i++)
            for (int j = 0; j < layer2.getWidth(); j++)
                layer2.setRGB(j, i, new Color(i * 1f / layer2.getHeight(), (layer2.getHeight() - i) * 1f / layer2.getHeight(), j * 1f / layer2.getWidth(), (layer2.getWidth() - j) * 1f / layer2.getWidth()).getRGB());

        LayerRecord layerRecord1 = new LayerRecord(0, 0, "BGHI", layer1);
        LayerRecord layerRecord2 = new LayerRecord(0, 5, "REDT", layer2);

        ImageIO.write(layer1, "PNG", new File(file("/out/writingABilayeredImage_l1.png")));
        ImageIO.write(layer2, "PNG", new File(file("/out/writingABilayeredImage_2.png")));

        PSDConnection reader = ImageMakerStudio.fromImage(layer1);
        reader.getLayerAndMaskData().setLayerInfo(new LayerInfo(true, new LayerRecord[]{layerRecord1, layerRecord2}));
        reader.write(put("/writingABilayeredImage.psd"));
        new PSDConnection(put("/writingABilayeredImage.psd"));
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

    private static final double scl = 20;
    private static final int delta = 5;

    static OpenSimplexNoise noise = new OpenSimplexNoise(new Random().nextLong());

    private static int eval255(int x, int y, int z) {
        return Math.max(0, Math.min(255, (int) (noise.eval(x / scl, y / scl, z / scl) * 128 + 128)));
    }

    public static BufferedImage getRandom(int w, int h, boolean alpha) {
        noise = new OpenSimplexNoise(new Random().nextLong());
        BufferedImage image = new BufferedImage(w, h, alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int r = eval255(x, y, 0);
                int g = eval255(x, y, delta);
                int b = eval255(x, y, 2 * delta);
                int a = alpha ? eval255(x, y, 3 * delta) : 255;
                int i = y * w + x;
                data[i] = (a << 24) | (r << 16) | (g << 8) | b;
//                image.setRGB(x, y, new Color(0, g, b).getRGB());
            }
        }
        return image;
    }

}