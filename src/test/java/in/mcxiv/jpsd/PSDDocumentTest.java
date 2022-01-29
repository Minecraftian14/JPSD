package in.mcxiv.jpsd;

import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.structure.SectionIOTest;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static in.mcxiv.jpsd.structure.SectionIOTest.*;

class PSDDocumentTest {

    @Test
    void testReadSingleLayerPSD() throws IOException {
        FileImageInputStream stream = get(resources[0]);
        PSDDocument document = new PSDDocument(stream);
        BufferedImage image = document.getCompositeImage();
        ImageIO.write(image, "PNG", file("testReadSingleLayerPSD.png"));
    }

    @Test
    void testReadMultiLayerPSD() throws IOException {
        PSDDocument document = new PSDDocument(get(SectionIOTest.resources[3]));

        ImageIO.write(document.getCompositeImage(), "PNG", file("testReadMultiLayerPSD_Composite.png"));

        ArrayList<LayerRecord> layers = document.getLayers();

        // Total number of layers
        int nol = layers.size();

        // Get first layer
        LayerRecord layerRecord = layers.get(0);

        // Get the image
        BufferedImage image = layerRecord.getImage(document);

        // Change the blending mode
        layerRecord.setBlendingMode(BlendingMode.LIGHTEN);

        // Set opacity
        layerRecord.setOpacity(1);

        // Set a mask
//        layerRecord.setMask(mask);



        for (LayerRecord layer : layers)
            ImageIO.write(layer.getImage(document), "PNG",
                    file(String.format("testReadSingleLayerPSD_layer_%s.png", layer.getLayerName())));
    }

    @Test
    void testReadMultiLayerWithMaskPSD() throws IOException {
        PSDDocument document = new PSDDocument(get(SectionIOTest.resources[8]));

        ImageIO.write(document.getCompositeImage(), "PNG", file("testReadMultiLayerWithMaskPSD_Composite.png"));

        ArrayList<LayerRecord> layers = document.getLayers();

        for (LayerRecord layer : layers) {
            ImageIO.write(layer.getImage(document), "PNG",
                    file(String.format("testReadMultiLayerWithMaskPSD_layer_%s.png", layer.getLayerName())));
            if (layer.hasMask())
                ImageIO.write(layer.getMaskImage(document), "PNG",
                        file(String.format("testReadMultiLayerWithMaskPSD_layer_%s_mask.png", layer.getLayerName())));
        }
    }

    @Test
    void testWriteSingleLayerPSD() throws IOException {
        BufferedImage image = getRandom(150, 100, false);
        ImageIO.write(image, "PNG", file("testWriteSingleLayerPSD.png"));

        PSDDocument document = new PSDDocument(image);

        document.writeTo(put("/testWriteSingleLayerPSD.psd"));
    }

    @Test
    void testWriteMultiLayerPSD() throws IOException {
        BufferedImage layer1 = getRandom(300, 200, false);
        BufferedImage layer2 = getRandom(200, 200, true);
        BufferedImage layer3 = getRandom(100, 200, true);
        ImageIO.write(layer1, "PNG", file("testWriteSingleLayerPSD_layer_1.png"));
        ImageIO.write(layer2, "PNG", file("testWriteSingleLayerPSD_layer_2.png"));
        ImageIO.write(layer3, "PNG", file("testWriteSingleLayerPSD_layer_3.png"));

        PSDDocument document = new PSDDocument(200, 300);
        List<LayerRecord> layers = document.getLayers();

        LayerRecord record1 = new LayerRecord(0, 0, "BackGround", layer1);
        LayerRecord record2 = new LayerRecord(50, 0, "Layer One", layer2);
        LayerRecord record3 = new LayerRecord(100, 0, "Layer Two", layer3);

        record2.setBlendingMode(BlendingMode.DARKEN);
        record3.setBlendingMode(BlendingMode.LIGHTER_COLOR);
        record3.setOpacity((byte) 127);

        layers.add(record1);
        layers.add(record2);
        layers.add(record3);

        document.writeTo(put("/testWriteMultiLayerPSD.psd"));
    }

    @Test
    void testWriteMultiLayerWithMaskPSD() throws IOException {
        BufferedImage layer1 = getRandom(300, 200, false);
        BufferedImage layer2 = getRandom(200, 200, true);
        BufferedImage mask = getRandom(200, 200, true);
        ImageIO.write(layer1, "PNG", file("testWriteMultiLayerWithMaskPSD_layer_1.png"));
        ImageIO.write(layer2, "PNG", file("testWriteMultiLayerWithMaskPSD_layer_2.png"));
        ImageIO.write(mask, "PNG", file("testWriteMultiLayerWithMaskPSD_layer_2_mask.png"));

        PSDDocument document = new PSDDocument(200, 300);
        List<LayerRecord> layers = document.getLayers();

        LayerRecord record1 = new LayerRecord(0, 0, "BackGround", layer1);
        LayerRecord record2 = new LayerRecord(50, 0, "Layer One", layer2);
        record2.setMask(mask);

        record2.setBlendingMode(BlendingMode.DARKER_COLOR);

        layers.add(record1);
        layers.add(record2);

        document.writeTo(put("/testWriteMultiLayerWithMaskPSD.psd"));
    }

    public static File file(String name) {
        return new File(SectionIOTest.file("/out/" + name));
    }
}