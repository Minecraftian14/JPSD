package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.FontInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.LineInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.StyleInfo;
import in.mcxiv.jpsd.data.common.ColorComponents;

import java.util.Arrays;

public class TypeToolInfo extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.TYPE_TOOL_INFO_KEY;

    private short version;
    private double[] transformInfo;
    // Font Info
    private short fontVersion;
    //  private short numberOfFaces;
    private FontInfo[] fontInfos; // length == numberOfFaces
    // Style Info
//  private short numberOfStyles;
    private StyleInfo[] styleInfos; // length == numberOfStyles
    // Text Info
    private short type;
    private float scalingFactor;
    private int charCount;
    private int horizontalPlacement;
    private int verticalPlacement;
    private int selectionStart;
    private int selectionEnd;
    //  private short lineNumber;
    private LineInfo[] lineInfos; // length == lineNumber
    // Color info
    private ColorComponents colorComponents;
    private boolean antiAlias;

    public TypeToolInfo(long length, short version, double[] transformInfo, short fontVersion, FontInfo[] fontInfos, StyleInfo[] styleInfos, short type, float scalingFactor, int charCount, int horizontalPlacement, int verticalPlacement, int selectionStart, int selectionEnd, LineInfo[] lineInfos, ColorComponents colorComponents, boolean antiAlias) {
        super(KEY, length);
        this.version = version;
        this.transformInfo = transformInfo;
        this.fontVersion = fontVersion;
        this.fontInfos = fontInfos;
        this.styleInfos = styleInfos;
        this.type = type;
        this.scalingFactor = scalingFactor;
        this.charCount = charCount;
        this.horizontalPlacement = horizontalPlacement;
        this.verticalPlacement = verticalPlacement;
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
        this.lineInfos = lineInfos;
        this.colorComponents = colorComponents;
        this.antiAlias = antiAlias;
    }

    public int getNumberOfFaces() {
        return fontInfos.length;
    }

    public int getNumberOfStyles() {
        return styleInfos.length;
    }

    public int getLineNumber() {
        return lineInfos.length;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public double[] getTransformInfo() {
        return transformInfo;
    }

    public void setTransformInfo(double[] transformInfo) {
        this.transformInfo = transformInfo;
    }

    public short getFontVersion() {
        return fontVersion;
    }

    public void setFontVersion(short fontVersion) {
        this.fontVersion = fontVersion;
    }

    public FontInfo[] getFontInfos() {
        return fontInfos;
    }

    public void setFontInfos(FontInfo[] fontInfos) {
        this.fontInfos = fontInfos;
    }

    public StyleInfo[] getStyleInfos() {
        return styleInfos;
    }

    public void setStyleInfos(StyleInfo[] styleInfos) {
        this.styleInfos = styleInfos;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public float getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(float scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public int getHorizontalPlacement() {
        return horizontalPlacement;
    }

    public void setHorizontalPlacement(int horizontalPlacement) {
        this.horizontalPlacement = horizontalPlacement;
    }

    public int getVerticalPlacement() {
        return verticalPlacement;
    }

    public void setVerticalPlacement(int verticalPlacement) {
        this.verticalPlacement = verticalPlacement;
    }

    public int getSelectionStart() {
        return selectionStart;
    }

    public void setSelectionStart(int selectionStart) {
        this.selectionStart = selectionStart;
    }

    public int getSelectionEnd() {
        return selectionEnd;
    }

    public void setSelectionEnd(int selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    public LineInfo[] getLineInfos() {
        return lineInfos;
    }

    public void setLineInfos(LineInfo[] lineInfos) {
        this.lineInfos = lineInfos;
    }

    public ColorComponents getColorComponents() {
        return colorComponents;
    }

    public void setColorComponents(ColorComponents colorComponents) {
        this.colorComponents = colorComponents;
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    @Override
    public String toString() {
        return "TypeToolInfo{" +
                "version=" + version +
                ", transformInfo=" + Arrays.toString(transformInfo) +
                ", fontVersion=" + fontVersion +
                ", fontInfos=" + Arrays.toString(fontInfos) +
                ", styleInfos=" + Arrays.toString(styleInfos) +
                ", type=" + type +
                ", scalingFactor=" + scalingFactor +
                ", charCount=" + charCount +
                ", horizontalPlacement=" + horizontalPlacement +
                ", verticalPlacement=" + verticalPlacement +
                ", selectionStart=" + selectionStart +
                ", selectionEnd=" + selectionEnd +
                ", lineInfos=" + Arrays.toString(lineInfos) +
                ", colorComponents=" + colorComponents +
                ", antiAlias=" + antiAlias +
                '}';
    }
}
