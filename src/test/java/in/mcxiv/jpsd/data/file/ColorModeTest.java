package in.mcxiv.jpsd.data.file;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.awt.color.ColorSpace;

import static org.junit.jupiter.api.Assertions.*;

class ColorModeTest {

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