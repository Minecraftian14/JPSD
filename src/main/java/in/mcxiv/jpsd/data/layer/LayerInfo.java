package in.mcxiv.jpsd.data.layer;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class LayerInfo extends DataObject {

    private boolean hasAlpha;
    private LayerRecord[] layerRecords;

    public LayerInfo(boolean hasAlpha, LayerRecord[] layerRecords) {
        this.hasAlpha = hasAlpha;
        this.layerRecords = layerRecords;
    }

    @Override
    public String toString() {
        return "LayerInfo{" +
                "hasAlpha=" + hasAlpha +
                ", layerRecords=" + Arrays.toString(layerRecords) +
                '}';
    }

    public LayerRecord[] getLayerRecords() {
        return layerRecords;
    }

    public LayerRecord getLayerRecord(int index) {
        return layerRecords[index];
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }
}
