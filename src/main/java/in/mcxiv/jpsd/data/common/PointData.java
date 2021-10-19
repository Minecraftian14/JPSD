package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.DataObject;

public class PointData extends DataObject {
    private double position_x;
    private double position_y;

    public PointData(double position_x, double position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }
}
