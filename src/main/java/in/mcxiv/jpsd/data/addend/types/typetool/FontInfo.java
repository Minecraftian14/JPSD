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
