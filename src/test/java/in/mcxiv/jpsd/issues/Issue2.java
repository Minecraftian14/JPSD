package in.mcxiv.jpsd.issues;

import in.mcxiv.jpsd.PSDDocument;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.structure.SectionIOTest;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Issue2 {

    public static File file(String name) {
        return new File(System.getProperty("user.dir") + "/src/test/test_data/issues/2/" + name);
    }

    @Test
    void testCreateASimplePSDFile() throws IOException {
        BufferedImage layer1 = new BufferedImage(684, 912, BufferedImage.TYPE_INT_RGB);
        Graphics g = layer1.createGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 684, 912);
        g.dispose();

        BufferedImage layer2 = new BufferedImage(684 / 2, 912 / 2, BufferedImage.TYPE_INT_ARGB);
        g = layer2.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 684 / 2, 912 / 2);
        g.dispose();

        BufferedImage layer2_mask = new BufferedImage(684 / 2, 912 / 2, BufferedImage.TYPE_INT_ARGB);
        g = layer2_mask.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 684 / 2, 912 / 2);
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, 342, 456);
        g.dispose();

        PSDDocument document = new PSDDocument(684, 912);

        ArrayList<LayerRecord> layers = document.getLayers();
        layers.add(new LayerRecord(0, 0, "Layer Ek", layer1));
        layers.add(new LayerRecord(/*fit to bottom left*/ 0, 912 / 2, "Layer Do", layer2) {{
            setMask(layer2_mask,0,912/2);
        }});

        document.writeTo(file("SimpleByJPSD.psd"));
    }

    @Test
    void compare_PSD_and_JPSD_workings() throws IOException {
        PSDDocument jpsd = new PSDDocument(file("SimpleByJPSD.psd"));
        PSDDocument psd_ = new PSDDocument(file("SimpleByPSD.psd"));

        SectionIOTest.pj(jpsd.toString());
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        SectionIOTest.pj(psd_.toString());

    }

}
