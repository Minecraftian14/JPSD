package in.mcxiv.jpsd.data.path;

import in.mcxiv.jpsd.data.primitive.ShortEntry;

public enum Selector implements ShortEntry {

    CLOSED_SUB_PATH_LENGTH_RECORD(true),
    CLOSED_SUB_PATH_BEZIER_KNOT_LINKED(true, true),
    CLOSED_SUB_PATH_BEZIER_KNOT_UNLINKED(true, false),
    OPEN_SUB_PATH_LENGTH_RECORD(false),
    OPEN_SUB_PATH_BEZIER_KNOT_LINKED(false, true),
    OPEN_SUB_PATH_BEZIER_KNOT_UNLINKED(false, false),
    PATH_FILL_RULE_RECORD(),
    CLIPBOARD_RECORD(),
    INITIAL_FILL_RULE_RECORD();

    boolean isClosed;
    boolean isLengthRec;
    boolean isBezierKnot;
    boolean isLinked;

    Selector() {
    }

    Selector(boolean isClosed) {
        this.isClosed = isClosed;
        this.isLengthRec = true;
    }

    Selector(boolean isClosed, boolean isLinked) {
        this.isClosed = isClosed;
        this.isBezierKnot = true;
        this.isLinked = isLinked;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public boolean isLengthRec() {
        return isLengthRec;
    }

    public boolean isBezierKnot() {
        return isBezierKnot;
    }

    public boolean isLinked() {
        return isLinked;
    }

    @Override
    public short getValue() {
        return (short) ordinal();
    }

    public static Selector of(short value) {
        return ShortEntry.of(value, values());
    }

}
