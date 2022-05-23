package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.ImageDisplayUtility;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.sections.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static in.mcxiv.jpsd.structure.SectionIOTest.put;

class ImageMakerStudio2Test {

    public static void main(String[] args) throws IOException {
//        new ImageMakerStudio2Test().testCompositeToImage(ColorMode.RGB);
        new ImageMakerStudio2Test().testWTH32BitImagesReallyAre();
    }

    @ParameterizedTest
    @EnumSource(value = ColorMode.class, names = {"Grayscale", "CMYK", "Lab", "RGB"})
    void testCompositeToImage(ColorMode colorMode) {

        int version = 1, channels = colorMode.components(), height = 12, width = 15, depth = 8;

        FileHeaderData fhd = new FileHeaderData(version, channels, height, width, depth, colorMode.getValue());

        byte[] bytes = new byte[height * width * channels * (depth == 1 ? 1 : depth / 8)];
        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = (byte) Double.doubleToLongBits(Math.random());
            bytes[i] = (byte) (Math.random() > .5 ? 1 : 0);
        }

        ImageData id = new ImageData(bytes);

        PSDConnection connection = new PSDConnection(
                fhd,
                new ColorModeData(),
                new ImageResourcesData(),
                new LayerAndMaskData(),
                id);

        BufferedImage image = ImageMakerStudio2.toImage(connection);
        ImageDisplayUtility.display("IMS", image, 0, 0);
    }

    @Test
    void testWTH32BitImagesReallyAre() throws IOException {

        int height = 12, width = 15;

        FileHeaderData fhd_src = new FileHeaderData(
                1, ColorMode.RGB.components(), height, width, 8, ColorMode.RGB.getValue());
        FileHeaderData fhd_des = new FileHeaderData(
                1, ColorMode.RGB.components(), height, width, 32, ColorMode.RGB.getValue());

        byte[] bytes = new byte[height * width * ColorMode.RGB.components()];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte) Double.doubleToLongBits(Math.random());
        ImageData id = new ImageData(bytes);

        PSDConnection connection = new PSDConnection(
                fhd_src,
                new ColorModeData(),
                new ImageResourcesData(),
                new LayerAndMaskData(),
                id);

        BufferedImage src = ImageMakerStudio2.toImage(connection);

//        connection.write(put("/testWTH32BitImagesReallyAre_8b.psd"));
        connection = new PSDConnection(fhd_des, new ColorModeData(), new ImageResourcesData(), new LayerAndMaskData(), new ImageData(new byte[height * width * ColorMode.RGB.components() * fhd_des.getDepthEntry().getBytes()]));
//        connection.write(put("/testWTH32BitImagesReallyAre_32b.psd"));

        BufferedImage des = ImageMakerStudio2.toImage(connection);
        Graphics2D graphics = des.createGraphics();
        graphics.drawImage(src, 0, 0, null);
        graphics.dispose();

        ImageDisplayUtility.display("IMS 8-bit", src, 0, 0);
        ImageDisplayUtility.display("IMS 32-bit", des, 1, 0);
    }
}