package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.common.ColorComponents;

public class TypeToolInfo extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.TYPE_TOOL_INFO_KEY;

    public static class FontInfo {

        private short markValue;
        private int fontTypeData;
        private String fontName; // pascalstr
        private String fontFamilyName;  // pascalstr
        private String fontStyleName;  // pascalstr
        private short scriptValue;
        private int numberOfDesignAxis;
        private int designVectorValue;

    }

    private static class StyleInfo {

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

    }

    public static class LineInfo {

        private int charCount;
        private short orientation;
        private short alignment;
        private short actualCharacter; // Actual character as a double byte character
        private short style;

    }

    private short version;
    private final double[] transformInfo = new double[6];
    // Font Info
    private short fontVersion;
    private short numberOfFaces;
    private FontInfo[] fontInfos; // length == numberOfFaces
    // Style Info
    private short numberOfStyles;
    private StyleInfo[] styleInfos; // length == numberOfStyles
    // Text Info
    private short type;
    private float scalingFactor;
    private int charCount;
    private int horizontalPlacement;
    private int verticalPlacement;
    private int selectionStart;
    private int selectionEnd;
    private short lineNumber;
    private LineInfo[] lineInfos; // length == lineNumber
    // Color info
    private ColorComponents colorComponents;
    private boolean antiAlias;

    public TypeToolInfo(String signature, long length) {
        super(KEY, length);
    }

}
