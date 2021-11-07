package in.mcxiv.jpsd.data.addend.types.typetool;

public class LineInfo {

    private int charCount;
    private short orientation;
    private short alignment;
    private String actualCharacter; // actualCharacter.length === 1
    private short style;

    public LineInfo(int charCount, short orientation, short alignment, String actualCharacter, short style) {
        this.charCount = charCount;
        this.orientation = orientation;
        this.alignment = alignment;
        this.actualCharacter = actualCharacter;
        this.style = style;
    }

    @Override
    public String toString() {
        return "LineInfo{" +
                "charCount=" + charCount +
                ", orientation=" + orientation +
                ", alignment=" + alignment +
                ", actualCharacter='" + actualCharacter + '\'' +
                ", style=" + style +
                '}';
    }
}
