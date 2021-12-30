package in.mcxiv.jpsd.data.path.types;

import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.path.Selector;

public class ClipboardRecord extends PathRecord {

    private float top;
    private float lef;
    private float bot;
    private float rig;
    private float res;

    public ClipboardRecord(float top, float lef, float bot, float rig, float res) {
        super(Selector.CLIPBOARD_RECORD);
        this.top = top;
        this.lef = lef;
        this.bot = bot;
        this.rig = rig;
        this.res = res;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLef() {
        return lef;
    }

    public void setLef(float lef) {
        this.lef = lef;
    }

    public float getBot() {
        return bot;
    }

    public void setBot(float bot) {
        this.bot = bot;
    }

    public float getRig() {
        return rig;
    }

    public void setRig(float rig) {
        this.rig = rig;
    }

    public float getRes() {
        return res;
    }

    public void setRes(float res) {
        this.res = res;
    }
}
