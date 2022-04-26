package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.sections.LayerAndMaskData;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.addend.AdditionalLayerInfoIO;
import in.mcxiv.jpsd.structure.layer.GlobalLayerMaskInfoIO;
import in.mcxiv.jpsd.structure.layer.LayerInfoIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Total bytes = 4 or 4+ <br />
 * <p> <br />
 * 4 bytes Section size s= W <br />
 * s bytes Section data  = A composition of three sub-sections, namely one {@link LayerInfo}, one {@link GlobalLayerMaskInfo} and a collection of {@link AdditionalLayerInfo} objects. <br />
 */
public class LayerAndMaskSectionIO extends SectionIO<LayerAndMaskData> {

    public final LayerInfoIO LAYER_INFO_IO;
    public static final GlobalLayerMaskInfoIO GLOBAL_LAYER_MASK_INFO_IO = new GlobalLayerMaskInfoIO();
    public final AdditionalLayerInfoIO ADDITIONAL_LAYER_INFO_IO;

    /**
     * The length data in header varies according to the file version!
     * For PSD it's 4 bytes and 8 bytes for PSB.
     */
    public final FileVersion version;

    public LayerAndMaskSectionIO(FileVersion version) {
        super(true);
        this.version = version;
        LAYER_INFO_IO = new LayerInfoIO(this.version);
        ADDITIONAL_LAYER_INFO_IO = new AdditionalLayerInfoIO(this.version, LAYER_INFO_IO);
    }

    @Override
    public LayerAndMaskData read(DataReader reader) throws IOException {

        long sectionLength;
        if (version.isLarge()) sectionLength = reader.stream.readLong();
        else sectionLength = reader.stream.readUnsignedInt();

        long mark = reader.stream.getStreamPosition();
        long expectedEnd = mark + sectionLength;

        LayerInfo layerInfo = LAYER_INFO_IO.read(reader);
        GlobalLayerMaskInfo globalLayerMaskInfo = GLOBAL_LAYER_MASK_INFO_IO.read(reader);
        List<AdditionalLayerInfo> additionalLayerInfos = new ArrayList<>();

        // FIXME:
        // When using /2x2_Yellow_over_2x4_Red_centered_(2_layers).psd I had to skip 2 bytes (in debug mode)
        // otherwise, when reading the next four bytes for sign, we only got "**8B"

//        reader.stream.skipBytes(2);

        // NOTE: the above mentioned problem is temporarily fixed by checking of a given string ends with "8B"
        // PSDFileReader#CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_2_BYTES_CHOOT
        // PSDFileReader#CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_1_BYTE_CHOOT

        // Wow, now we are left with a single byte... WTH
        // reference to custom.psd and there was one more in random tests

        while (expectedEnd - reader.stream.getStreamPosition() >= 12) {
            // 12 = signature:4 + key:4 + length:4||8
            // Therefore, if that's < 12, there can't be any valid data left.
            // Thing is, why is sometimes data less than 12?
            // I really want to investigate why that's like that, but
            // for now, it's just a TODO.

            AdditionalLayerInfo additionalLayerInfo = ADDITIONAL_LAYER_INFO_IO.read(reader);
            if (additionalLayerInfo != null)
                additionalLayerInfos.add(additionalLayerInfo);
        }

        checkBytesCount(sectionLength, mark, reader.stream.getStreamPosition());

        return new LayerAndMaskData(layerInfo, globalLayerMaskInfo, additionalLayerInfos.toArray(new AdditionalLayerInfo[0]));
    }

    @Override
    public void write(DataWriter writer, LayerAndMaskData layerAndMaskData) throws IOException {

        DataWriter buffer = new DataWriter();

        LAYER_INFO_IO.write(buffer, layerAndMaskData.getLayerInfo());

        if(layerAndMaskData.doesAnyLayerContainAMask() && layerAndMaskData.getGlobalLayerMaskInfo() == null)
            layerAndMaskData.createDefaultGlobalLayerMaskInfo();

        GLOBAL_LAYER_MASK_INFO_IO.write(buffer, layerAndMaskData.getGlobalLayerMaskInfo());

        // FIXME:
        // figure out why there is a 1-2 byte spacing after reading global...
        // and add them here if that's really required!

        for (AdditionalLayerInfo additionalLayerInfo : layerAndMaskData.getAdditionalLayerInfo()) {
            ADDITIONAL_LAYER_INFO_IO.write(buffer, additionalLayerInfo);
        }

        byte[] bytes = buffer.toByteArray();

        if (version.isLarge()) writer.stream.writeLong(bytes.length);
        else writer.stream.writeInt(bytes.length);
        writer.writeBytes(bytes);
    }
}
