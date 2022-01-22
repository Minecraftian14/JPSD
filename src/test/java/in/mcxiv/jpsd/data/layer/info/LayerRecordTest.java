package in.mcxiv.jpsd.data.layer.info;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class LayerRecordTest {
    @Test
    void extractChan() {
        Color col = new Color(50, 100, 150, 200);
        System.out.println(Integer.toHexString(col.getRGB()));
        assertEquals((byte) 150, LayerRecord.extractChannel(0, getCol(col))[0]);
        assertEquals((byte) 100, LayerRecord.extractChannel(1, getCol(col))[0]);
        assertEquals((byte) 50, LayerRecord.extractChannel(2, getCol(col))[0]);
        assertEquals((byte) 200, LayerRecord.extractChannel(3, getCol(col))[0]);
    }

    BufferedImage getCol(Color c) {
        BufferedImage image = new BufferedImage(1, 1, 2);
        image.setRGB(0, 0, c.getRGB());
        return image;
    }
}