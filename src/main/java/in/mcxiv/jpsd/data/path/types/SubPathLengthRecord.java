package in.mcxiv.jpsd.data.path.types;

import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.path.Selector;

public class SubPathLengthRecord extends PathRecord {

    private short length;

    public SubPathLengthRecord(Selector selector, short length) {
        super(selector);
        this.length = length;

        if (!selector.isLengthRec())
            throw new IllegalArgumentException();
    }

    public short getLength() {
        return length;
    }
}
