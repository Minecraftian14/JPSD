package in.mcxiv.jpsd.data.path.types;

import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.path.Selector;

public class InitialFillRuleRecord extends PathRecord {

    /**
     * if fillStart == 1: the fill starts with all pixels
     */
    private short fillStart;

    public InitialFillRuleRecord(short fillStart) {
        super(Selector.INITIAL_FILL_RULE_RECORD);
        this.fillStart = fillStart;
    }

    public short getFillStart() {
        return fillStart;
    }

    public void setFillStart(short fillStart) {
        this.fillStart = fillStart;
    }
}
