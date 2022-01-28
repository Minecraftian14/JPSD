package in.mcxiv.jpsd.data.layer;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class LayerInfo extends DataObject {

    private boolean hasAlpha;
    private ArrayList<LayerRecord> layerRecords = new ArrayList<>();

    public LayerInfo() {
        this(true);
    }

    public LayerInfo(boolean hasAlpha, LayerRecord... layerRecords) {
        this.hasAlpha = hasAlpha;
        this.layerRecords.addAll(Arrays.asList(layerRecords));
    }

    /**
     * Retrieve the list of all the layers.
     *
     * Every layer and its related data is stored in one {@link LayerRecord}.
     *
     * @return the list of all {@link LayerRecord}.
     */
    public ArrayList<LayerRecord> getLayers() {
        return layerRecords;
    }

    public LayerRecord[] getLayerRecords() {
        return layerRecords.toArray(new LayerRecord[0]);
    }

    public void addLayer(LayerRecord record) {
        layerRecords.add(record);
    }

    public int getNumberOfLayers() {
        return layerRecords.size();
    }

    public void setAlpha(boolean hasAlpha) {
        this.hasAlpha = hasAlpha;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }

    @Override
    public String toString() {
        return "LayerInfo{" +
                "hasAlpha=" + hasAlpha +
                ", layerRecords=" + Arrays.toString(getLayerRecords()) +
                '}';
    }
}
