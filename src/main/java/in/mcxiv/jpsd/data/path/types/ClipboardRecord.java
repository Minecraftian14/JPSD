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
}
