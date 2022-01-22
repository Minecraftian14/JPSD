package in.mcxiv.jpsd.structure.addend.types;

import in.mcxiv.jpsd.data.addend.types.TypeToolInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.FontInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.LineInfo;
import in.mcxiv.jpsd.data.addend.types.typetool.StyleInfo;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.ColorComponentsIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TypeToolInfoIO extends SectionIO<TypeToolInfo> {

    public TypeToolInfoIO() {
        super(true);
    }

    @Override
    public TypeToolInfo read(DataReader reader) throws IOException {
        return readTypeToolInfo(reader, -1);
    }

    public TypeToolInfo readTypeToolInfo(DataReader reader, long length) throws IOException {

        short version = reader.stream.readShort();
        double[] transformInfo = new double[6];
        for (int i = 0; i < 6; transformInfo[i++] = reader.stream.readDouble()) ;

        short fontVersion = reader.stream.readShort();
        short numberOfFaces = reader.stream.readShort();
        FontInfo[] fontInfos = new FontInfo[numberOfFaces];
        for (int i = 0; i < numberOfFaces; fontInfos[i++] = readFontInfo(reader)) ;

        short numberOfStyles = reader.stream.readShort();
        StyleInfo[] styleInfos = new StyleInfo[numberOfStyles];
        for (int i = 0; i < numberOfStyles; styleInfos[i++] = readStyleInfo(reader)) ;

        //@formatter:off
        short type                = reader.stream.readShort();
        float scalingFactor       = reader.stream.readFloat(); // FIXME: FFloat or Float?
        int   charCount           = reader.stream.readInt  ();
        int   horizontalPlacement = reader.stream.readInt  ();
        int   verticalPlacement   = reader.stream.readInt  ();
        int   selectionStart      = reader.stream.readInt  ();
        int   selectionEnd        = reader.stream.readInt  ();
        short lineNumber          = reader.stream.readShort();
        //@formatter:on
        LineInfo[] lineInfos = new LineInfo[lineNumber];
        for (int i = 0; i < lineNumber; lineInfos[i++] = readLineInfo(reader)) ;

        ColorComponents colorComponents = ColorComponentsIO.INSTANCE.read(reader);
        boolean antiAlias = reader.stream.readBoolean();

        return new TypeToolInfo(
                length, version, transformInfo,
                fontVersion, fontInfos,
                styleInfos,
                type, scalingFactor, charCount, horizontalPlacement, verticalPlacement, selectionStart, selectionEnd, lineInfos,
                colorComponents, antiAlias
        );
    }

    private FontInfo readFontInfo(DataReader reader) throws IOException {

        //@formatter:off
        short  markValue          = reader.stream.readShort   ();
        int    fontTypeData       = reader.stream.readInt     ();
        String fontName           = reader.readPascalStringRaw(); // FIXME: raw, even or four?
        String fontFamilyName     = reader.readPascalStringRaw();
        String fontStyleName      = reader.readPascalStringRaw();
        short  scriptValue        = reader.stream.readShort   ();
        int    numberOfDesignAxis = reader.stream.readInt     ();
        int    designVectorValue  = reader.stream.readInt     ();
        //@formatter:on

        return new FontInfo(markValue, fontTypeData, fontName, fontFamilyName, fontStyleName, scriptValue, numberOfDesignAxis, designVectorValue);
    }

    private StyleInfo readStyleInfo(DataReader reader) throws IOException {
        //@formatter:off
        short   markValue      = reader.stream.readShort  ();
        short   faceMarkValue  = reader.stream.readShort  ();
        int     size           = reader.stream.readInt    ();
        int     trackingValue  = reader.stream.readInt    ();
        int     kerningValue   = reader.stream.readInt    ();
        int     leadingValue   = reader.stream.readInt    ();
        int     baseShiftValue = reader.stream.readInt    ();
        boolean autoKern       = reader.stream.readBoolean();
        boolean something      = reader.stream.readBoolean(); // FIXME: only present in version <= 5; Hmmm.
        boolean upOrDown       = reader.stream.readBoolean();
        //@formatter:on

        return new StyleInfo(markValue, faceMarkValue, size, trackingValue, kerningValue, leadingValue, baseShiftValue, autoKern, something, upOrDown);
    }

    private LineInfo readLineInfo(DataReader reader) throws IOException {

        //@formatter:off
        int    charCount       = reader.stream.readInt();
        short  orientation     = reader.stream.readShort();
        short  alignment       = reader.stream.readShort();
        String actualCharacter = new String(reader.readBytes(2, true), StandardCharsets.UTF_16);
        short  style           = reader.stream.readShort();
        //@formatter:on

        return new LineInfo(charCount, orientation, alignment, actualCharacter, style);
    }

    @Override
    public void write(DataWriter writer, TypeToolInfo typeToolInfo) throws IOException {
        writeTypeToolInfo(writer, typeToolInfo);
    }

    public void writeTypeToolInfo(DataWriter writer, TypeToolInfo typeToolInfo) throws IOException {

        writer.stream.writeShort(typeToolInfo.getVersion());
        for (double ti : typeToolInfo.getTransformInfo())
            writer.stream.writeDouble(ti);

        writer.stream.writeShort(typeToolInfo.getFontVersion());
        writer.stream.writeShort(typeToolInfo.getNumberOfFaces());
        for (FontInfo fontInfo : typeToolInfo.getFontInfos())
            writeFontInfo(writer, fontInfo);

        writer.stream.writeShort(typeToolInfo.getNumberOfStyles());
        for (StyleInfo styleInfo : typeToolInfo.getStyleInfos())
            writeStyleInfo(writer, styleInfo);

        //@formatter:off
        writer.stream.writeShort (typeToolInfo.getType());
        writer.stream.writeFloat (typeToolInfo.getScalingFactor());
        writer.stream.writeInt   (typeToolInfo.getCharCount());
        writer.stream.writeInt   (typeToolInfo.getHorizontalPlacement());
        writer.stream.writeInt   (typeToolInfo.getVerticalPlacement());
        writer.stream.writeInt   (typeToolInfo.getSelectionStart());
        writer.stream.writeInt   (typeToolInfo.getSelectionEnd());
        writer.stream.writeShort (typeToolInfo.getLineNumber());
        //@formatter:on

        for (LineInfo lineInfo : typeToolInfo.getLineInfos())
            writeLineInfo(writer, lineInfo);

        ColorComponentsIO.INSTANCE.write(writer, typeToolInfo.getColorComponents());
        writer.stream.writeBoolean(typeToolInfo.isAntiAlias());
    }

    private void writeFontInfo(DataWriter writer, FontInfo fontInfo) throws IOException {
        //@formatter:off
        writer.stream.writeShort   (fontInfo.getMarkValue());
        writer.stream.writeInt     (fontInfo.getFontTypeData());
        writer.writePascalStringRaw(fontInfo.getFontName());
        writer.writePascalStringRaw(fontInfo.getFontFamilyName());
        writer.writePascalStringRaw(fontInfo.getFontStyleName());
        writer.stream.writeShort   (fontInfo.getScriptValue());
        writer.stream.writeInt     (fontInfo.getNumberOfDesignAxis());
        writer.stream.writeInt     (fontInfo.getDesignVectorValue());
        //@formatter:on
    }

    private void writeStyleInfo(DataWriter writer, StyleInfo styleInfo) throws IOException {
        //@formatter:off
        writer.stream.writeShort   (styleInfo.getMarkValue());
        writer.stream.writeShort   (styleInfo.getFaceMarkValue());
        writer.stream.writeInt     (styleInfo.getSize());
        writer.stream.writeInt     (styleInfo.getTrackingValue());
        writer.stream.writeInt     (styleInfo.getKerningValue());
        writer.stream.writeInt     (styleInfo.getLeadingValue());
        writer.stream.writeInt     (styleInfo.getBaseShiftValue());
        writer.stream.writeBoolean (styleInfo.isAutoKern());
        writer.stream.writeBoolean (styleInfo.isSomething());
        writer.stream.writeBoolean (styleInfo.isUpOrDown());
        //@formatter:on
    }

    private void writeLineInfo(DataWriter writer, LineInfo lineInfo) throws IOException {
        //@formatter:off
        writer.stream.writeInt   (lineInfo.getCharCount());
        writer.stream.writeShort (lineInfo.getOrientation());
        writer.stream.writeShort (lineInfo.getAlignment());
        writer.       writeBytes (lineInfo.getActualCharacterBytes());
        writer.stream.writeShort (lineInfo.getStyle());
        //@formatter:on
    }
}
