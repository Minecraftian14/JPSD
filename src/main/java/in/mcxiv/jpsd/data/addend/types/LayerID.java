package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;

public class LayerID extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.LAYER_ID_KEY;
    private static int AUTO_ID_THING = 1;

    private int id;

    public LayerID(int id) {
        this(id, -1);
    }

    public LayerID(int id, long length) {
        super(KEY, length);
        this.id = id;
//        if (this.id < AUTO_ID_THING)
//            throw new IllegalArgumentException("Please provide an ID greater than " + AUTO_ID_THING);
        if (id >= AUTO_ID_THING)
            AUTO_ID_THING = id + 1;
    }

    public LayerID() {
        this(-1);
    }

    public LayerID(long length) {
        super(KEY, length);
        this.id = AUTO_ID_THING++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LayerID{" +
               "key=" + key +
               ", id=" + id +
               '}';
    }
}
