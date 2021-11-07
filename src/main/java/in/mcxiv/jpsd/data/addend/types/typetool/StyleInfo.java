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
