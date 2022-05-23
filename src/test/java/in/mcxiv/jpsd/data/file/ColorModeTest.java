package in.mcxiv.jpsd.data.file;

import in.mcxiv.jpsd.util.CMYKColorSpace;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ColorModeTest {

    public static void main(String[] args) throws IOException {
        System.out.println("HW");
        int w = 100, h = 120;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                float[] floats = ColorMode.CMYK.getColorSpace().toRGB(new float[]{i < w / 2 ? 1 : 0, j < h / 2 ? 1 : 0, i > w / 2 ? 1 : 0, j > h / 2 ? 1 : 0});
                System.out.println(Arrays.toString(floats));
                image.setRGB(i, j, new Color(floats[0], floats[1], floats[2]).getRGB());
            }
        }
        ImageIO.write(image, "PNG", new File("D:\\outM.png"));
    }

//    @Test
    @RepeatedTest(4)
    void testCustomImplementationsOfColorSpaces() {
        ColorSpace rgb = ColorMode.RGB.getColorSpace();
        float[] original = getFloats(rgb.getNumComponents());
        assertArrayEquals(original, rgb.fromRGB(original), 0.001f);

        ColorSpace gsc = ColorMode.Grayscale.getColorSpace();
        assertEquals(avg(original), avg(gsc.toRGB(gsc.fromRGB(original))), 0.001f);

        ColorSpace cmy = ColorMode.CMYK.getColorSpace();
        assertEquals(avg(original), avg(cmy.toRGB(cmy.fromRGB(original))), 0.001f);

        ColorSpace lab = ColorMode.Lab.getColorSpace();
        assertEquals(avg(original), avg(lab.toRGB(lab.fromRGB(original))), 0.001f);
    }

    private float avg(float[] floats) {
        float avg = 0;
        for (float v : floats) avg += v / floats.length;
        return avg;
    }

    private float[] getFloats(int numComponents) {
        float[] floats = new float[numComponents];
        for (int i = 0; i < numComponents; i++)
            floats[i] = (float) Math.random();
        return floats;
    }
}