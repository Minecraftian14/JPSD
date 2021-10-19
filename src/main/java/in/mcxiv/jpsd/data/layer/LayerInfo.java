package in.mcxiv.jpsd.data.layer;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.layer.info.ChannelImageData;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;

import java.util.Arrays;

public class LayerInfo extends DataObject {

    private LayerRecord[] layerRecords;
    private ChannelImageData[] imageData; // Probably same sized as layerRecords

    public LayerInfo(LayerRecord[] layerRecords, ChannelImageData[] imageData) {
        this.layerRecords = layerRecords;
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "LayerInfo{" +
                "layerRecords=" + Arrays.toString(layerRecords) +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
