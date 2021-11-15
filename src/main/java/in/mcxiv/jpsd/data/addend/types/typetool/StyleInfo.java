package in.mcxiv.jpsd.data.addend.types.typetool;

public class StyleInfo {

    private short markValue;
    private short faceMarkValue;
    private int size;
    private int trackingValue;
    private int kerningValue;
    private int leadingValue;
    private int baseShiftValue;
    private boolean autoKern;
    private boolean something;
    private boolean upOrDown;

    public StyleInfo(short markValue, short faceMarkValue, int size, int trackingValue, int kerningValue, int leadingValue, int baseShiftValue, boolean autoKern, boolean something, boolean upOrDown) {
        this.markValue = markValue;
        this.faceMarkValue = faceMarkValue;
        this.size = size;
        this.trackingValue = trackingValue;
        this.kerningValue = kerningValue;
        this.leadingValue = leadingValue;
        this.baseShiftValue = baseShiftValue;
        this.autoKern = autoKern;
        this.something = something;
        this.upOrDown = upOrDown;
    }

    public short getMarkValue() {
        return markValue;
    }

    public void setMarkValue(short markValue) {
        this.markValue = markValue;
    }

    public short getFaceMarkValue() {
        return faceMarkValue;
    }

    public void setFaceMarkValue(short faceMarkValue) {
        this.faceMarkValue = faceMarkValue;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTrackingValue() {
        return trackingValue;
    }

    public void setTrackingValue(int trackingValue) {
        this.trackingValue = trackingValue;
    }

    public int getKerningValue() {
        return kerningValue;
    }

    public void setKerningValue(int kerningValue) {
        this.kerningValue = kerningValue;
    }

    public int getLeadingValue() {
        return leadingValue;
    }

    public void setLeadingValue(int leadingValue) {
        this.leadingValue = leadingValue;
    }

    public int getBaseShiftValue() {
        return baseShiftValue;
    }

    public void setBaseShiftValue(int baseShiftValue) {
        this.baseShiftValue = baseShiftValue;
    }

    public boolean isAutoKern() {
        return autoKern;
    }

    public void setAutoKern(boolean autoKern) {
        this.autoKern = autoKern;
    }

    public boolean isSomething() {
        return something;
    }

    public void setSomething(boolean something) {
        this.something = something;
    }

    public boolean isUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(boolean upOrDown) {
        this.upOrDown = upOrDown;
    }

    @Override
    public String toString() {
        return "StyleInfo{" +
                "markValue=" + markValue +
                ", faceMarkValue=" + faceMarkValue +
                ", size=" + size +
                ", trackingValue=" + trackingValue +
                ", kerningValue=" + kerningValue +
                ", leadingValue=" + leadingValue +
                ", baseShiftValue=" + baseShiftValue +
                ", autoKern=" + autoKern +
                ", something=" + something +
                ", upOrDown=" + upOrDown +
                '}';
    }
}
