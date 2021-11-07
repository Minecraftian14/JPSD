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

    @Deprecated
    public Rectangle(int width, int height) {
        this(0, 0, height, width);
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

    public int getTop() {
        return top;
    }

    public int getLef() {
        return lef;
    }

    public int getBot() {
        return bot;
    }

    public int getRig() {
        return rig;
    }
}
