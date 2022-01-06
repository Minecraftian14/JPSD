package in.mcxiv.jpsd.data.addend.types.typetool;

import java.nio.charset.StandardCharsets;

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

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public short getOrientation() {
        return orientation;
    }

    public void setOrientation(short orientation) {
        this.orientation = orientation;
    }

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }

    public String getActualCharacter() {
        return actualCharacter;
    }

    public byte[] getActualCharacterBytes() {
        byte[] bytes = actualCharacter.getBytes(StandardCharsets.UTF_16);
        if (bytes.length != 2) throw new IllegalStateException("The length of actualCharacter provided should be 1");
        return bytes;
    }

    public void setActualCharacter(String actualCharacter) {
        // FIXME: Force user to give StandardCharsets.UTF_16 strings only
        // And.. um.. they should have only one character, right?
        this.actualCharacter = actualCharacter;
    }

    public short getStyle() {
        return style;
    }

    public void setStyle(short style) {
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
