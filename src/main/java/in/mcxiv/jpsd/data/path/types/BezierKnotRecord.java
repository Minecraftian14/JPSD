package in.mcxiv.jpsd.data.path.types;

import in.mcxiv.jpsd.data.common.PointData;
import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.path.Selector;

public class BezierKnotRecord extends PathRecord {

    private PointData preceding_control;
    private PointData anchor;
    private PointData leaving_control;

    public BezierKnotRecord(Selector selector, PointData preceding_control, PointData anchor, PointData leaving_control) {
        super(selector);
        this.preceding_control = preceding_control;
        this.anchor = anchor;
        this.leaving_control = leaving_control;
        if(!selector.isBezierKnot())
            throw new IllegalArgumentException();
    }

    public PointData getPreceding_control() {
        return preceding_control;
    }

    public void setPreceding_control(PointData preceding_control) {
        this.preceding_control = preceding_control;
    }

    public PointData getAnchor() {
        return anchor;
    }

    public void setAnchor(PointData anchor) {
        this.anchor = anchor;
    }

    public PointData getLeaving_control() {
        return leaving_control;
    }

    public void setLeaving_control(PointData leaving_control) {
        this.leaving_control = leaving_control;
    }
}
