package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.DataObject;

public class Rectangle extends DataObject {

    private int top;
    private int lef;
    private int bot;
    private int rig;

    public Rectangle() {
    }

    public Rectangle(int top, int lef, int bot, int rig) {
        this.top = top;
        this.lef = lef;
        this.bot = bot;
        this.rig = rig;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "top=" + top +
                ", lef=" + lef +
                ", bot=" + bot +
                ", rig=" + rig +
                '}';
    }
}
