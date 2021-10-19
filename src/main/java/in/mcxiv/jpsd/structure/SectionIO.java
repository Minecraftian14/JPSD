package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.sections.ColorModeData;
import in.mcxiv.jpsd.data.sections.FileHeaderData;
import in.mcxiv.jpsd.data.sections.ImageResourcesData;
import in.mcxiv.jpsd.data.sections.LayerAndMaskData;
import in.mcxiv.jpsd.exceptions.BytesReadDidntMatchExpectedCount;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.sections.ColorModeSectionIO;
import in.mcxiv.jpsd.structure.sections.FileHeaderSectionIO;
import in.mcxiv.jpsd.structure.sections.ImageResourcesSectionIO;
import in.mcxiv.jpsd.structure.sections.LayerAndMaskSectionIO;

import java.io.IOException;

public abstract class SectionIO<Data extends DataObject> {

    public static final SectionIO<FileHeaderData> FILE_HEADER_SECTION = new FileHeaderSectionIO();
    public static final SectionIO<ColorModeData> COLOR_MODE_DATA_SECTION = new ColorModeSectionIO();
    public static final SectionIO<ImageResourcesData> IMAGE_RESOURCES_DATA_SECTION = new ImageResourcesSectionIO();
    public static final SectionIO<LayerAndMaskData> LAYER_AND_MASK_DATA_SECTION_PSD = new LayerAndMaskSectionIO(FileHeaderData.FileVersion.PSD);
    public static final SectionIO<LayerAndMaskData> LAYER_AND_MASK_DATA_SECTION_PSB = new LayerAndMaskSectionIO(FileHeaderData.FileVersion.PSB);

    protected PSDFileReader.UnknownBytesStrategy unknownBytesStrategy = PSDFileReader.unknownBytesStrategy;

    private final boolean hasVariableLength;

    public SectionIO(boolean hasVariableLength) {
        this.hasVariableLength = hasVariableLength;
    }

    public abstract Data read(DataReader reader) throws IOException;

    public abstract void write(DataWriter writer, Data data);

    protected static void checkBytesCount(long expected, long actual) throws BytesReadDidntMatchExpectedCount {
        if (expected != actual)
            throw new BytesReadDidntMatchExpectedCount(expected, actual);
    }

    protected static void checkBytesCount(long expected, long start, long end) throws BytesReadDidntMatchExpectedCount {
        if (expected != (end - start))
            throw new BytesReadDidntMatchExpectedCount(expected, start, end);
    }

}
