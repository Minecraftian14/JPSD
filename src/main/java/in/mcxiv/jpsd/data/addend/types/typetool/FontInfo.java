package in.mcxiv.jpsd.data.addend.types.typetool;

public class FontInfo {

    private short markValue;
    private int fontTypeData;
    private String fontName;
    private String fontFamilyName;
    private String fontStyleName;
    private short scriptValue;
    private int numberOfDesignAxis;
    private int designVectorValue;

    public FontInfo(short markValue, int fontTypeData, String fontName, String fontFamilyName, String fontStyleName, short scriptValue, int numberOfDesignAxis, int designVectorValue) {
        this.markValue = markValue;
        this.fontTypeData = fontTypeData;
        this.fontName = fontName;
        this.fontFamilyName = fontFamilyName;
        this.fontStyleName = fontStyleName;
        this.scriptValue = scriptValue;
        this.numberOfDesignAxis = numberOfDesignAxis;
        this.designVectorValue = designVectorValue;
    }

    public short getMarkValue() {
        return markValue;
    }

    public void setMarkValue(short markValue) {
        this.markValue = markValue;
    }

    public int getFontTypeData() {
        return fontTypeData;
    }

    public void setFontTypeData(int fontTypeData) {
        this.fontTypeData = fontTypeData;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontFamilyName() {
        return fontFamilyName;
    }

    public void setFontFamilyName(String fontFamilyName) {
        this.fontFamilyName = fontFamilyName;
    }

    public String getFontStyleName() {
        return fontStyleName;
    }

    public void setFontStyleName(String fontStyleName) {
        this.fontStyleName = fontStyleName;
    }

    public short getScriptValue() {
        return scriptValue;
    }

    public void setScriptValue(short scriptValue) {
        this.scriptValue = scriptValue;
    }

    public int getNumberOfDesignAxis() {
        return numberOfDesignAxis;
    }

    public void setNumberOfDesignAxis(int numberOfDesignAxis) {
        this.numberOfDesignAxis = numberOfDesignAxis;
    }

    public int getDesignVectorValue() {
        return designVectorValue;
    }

    public void setDesignVectorValue(int designVectorValue) {
        this.designVectorValue = designVectorValue;
    }

    @Override
    public String toString() {
        return "FontInfo{" +
                "markValue=" + markValue +
                ", fontTypeData=" + fontTypeData +
                ", fontName='" + fontName + '\'' +
                ", fontFamilyName='" + fontFamilyName + '\'' +
                ", fontStyleName='" + fontStyleName + '\'' +
                ", scriptValue=" + scriptValue +
                ", numberOfDesignAxis=" + numberOfDesignAxis +
                ", designVectorValue=" + designVectorValue +
                '}';
    }
}
