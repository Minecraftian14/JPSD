package in.mcxiv.jpsd.structure.addend;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.UnknownAdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.*;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.exceptions.UnknownByteBlockException;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.addend.types.EffectsLayerIO;
import in.mcxiv.jpsd.structure.addend.types.TypeToolInfoIO;
import in.mcxiv.jpsd.structure.layer.LayerInfoIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class AdditionalLayerInfoIO extends SectionIO<AdditionalLayerInfo> {

    public final FileVersion version;

    public final LayerInfoIO LAYER_INFO_IO;

    public static final EffectsLayerIO EFFECTS_LAYER_IO = new EffectsLayerIO();
    public static final TypeToolInfoIO TYPE_TOOL_INFO_IO = new TypeToolInfoIO();

    public AdditionalLayerInfoIO(FileVersion version) {
        super(true);
        this.version = version;
        LAYER_INFO_IO = null;
    }

    public AdditionalLayerInfoIO(FileVersion version, LayerInfoIO LAYER_INFO_IO) {
        super(true);
        this.version = version;
        this.LAYER_INFO_IO = LAYER_INFO_IO;
    }

    @Override
    public AdditionalLayerInfo read(DataReader reader) throws IOException {

        byte[] signature = reader.verifySignature(PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_SMALL, PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_LONG,
                PSDFileReader.CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_2_BYTES_CHOOT, PSDFileReader.CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_1_BYTE_CHOOT);

        if (Arrays.equals(signature, PSDFileReader.CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_1_BYTE_CHOOT))
            reader.stream.skipBytes(1);
        else if (Arrays.equals(signature, PSDFileReader.CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_2_BYTES_CHOOT))
            reader.stream.skipBytes(2);

        AdditionalInfoKey key = AdditionalInfoKey.of(reader.readBytes(4, true));

        boolean isLargeResource = Arrays.equals(PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_LONG, signature);

        if (version.isLarge()) {
            if (key.isLarge())
                isLargeResource = true;
        }

        long size;
        if (isLargeResource) size = reader.stream.readLong();
        else size = reader.stream.readInt();

        switch (key) {

            case EFFECTS_KEY:
                return EFFECTS_LAYER_IO.readEffectsLayer(reader, size);

            case LAYER_AND_MASK_INFO_16:
                if (size % 2 != 0) size++; // make even
                LayerInfo layerInfo = LAYER_INFO_IO.read(reader, size);
                reader.skipToPadBy(size, 4);
                return new LayerAndMaskInfo(key, size, layerInfo);

            case LAYER_ID_KEY:
                int id = reader.stream.readInt();
                return new LayerID(id, size);

            case TYPE_TOOL_INFO_KEY:
                TypeToolInfo typeToolInfo = TYPE_TOOL_INFO_IO.readTypeToolInfo(reader, size);
                reader.skipToPadBy(size, 4);
                return typeToolInfo;

            case UNICODE_LAYER_NAME_KEY:
                String unicodeName = reader.readUnicodeString();

                int expectedLength = (int) (size - 4) / 2;
                // Is there any danger of \0 coming next?
                if (unicodeName.length() != expectedLength) {
                    if (expectedLength - unicodeName.length() == 1) {
                        PSDFileReader.out.println("Reading num bytes!");
                        reader.stream.skipBytes(2);
                    } else {
                        new Exception("The heck?").printStackTrace(PSDFileReader.out);
                        reader.stream.skipBytes(2 * (expectedLength - unicodeName.length()));
                    }
                }
                return new UnicodeLayerName(unicodeName, size);

            default:
                switch (unknownBytesStrategy.action) {
                    case ReadAll:
                        return new UnknownAdditionalLayerInfo(key, reader.readBytes((int) size, true), size);
                    case ExcludeData:
                        reader.skipAndPadBy4(size);
                        return new UnknownAdditionalLayerInfo(key, null, size);
                    case Skip:
                        reader.skipAndPadBy4(size);
                        return null;
                    case Quit:
                        throw new UnknownByteBlockException("Layer And Mask Section> Additional Layer Info> " + key);
                }
        }

        System.err.println("HOW did i reach here?");
        return null;
    }

    @Override
    public void write(DataWriter writer, AdditionalLayerInfo info) throws IOException {

        if (version.isLarge()) // TODO: version isLarge or key isLarge?
            writer.sign(PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_LONG);
        else writer.sign(PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_SMALL);

        writer.writeBytes(info.getKey().getValue());

//        boolean isLargeResource = Arrays.equals(PSDFileReader.ADDITIONAL_LAYER_INFO_SIGNATURE_LONG, signature);
        boolean isLargeResource = version.isLarge(); // It's kept here because of the TODO right above

        if (version.isLarge()) {
            if (info.getKey().isLarge())
                isLargeResource = true;
        }

        ByteArrayOutputStream aliData = new ByteArrayOutputStream();
        DataWriter aliWriter = new DataWriter(aliData);

        long mark, size;

        switch (info.getKey()) {

            case EFFECTS_KEY:
                EFFECTS_LAYER_IO.write(aliWriter, ((EffectsLayer) info));
                break;

            case LAYER_AND_MASK_INFO_16:
                // TODO: verify if I did it properly.
                mark = aliWriter.stream.getStreamPosition();
                LAYER_INFO_IO.write(aliWriter, ((LayerAndMaskInfo) info).getLayerInfo());
                size = aliWriter.stream.getStreamPosition() - mark;
                if (size % 2 != 0) size++;
                aliWriter.skipToPadBy(size, 4);
                break;

            case LAYER_ID_KEY:
                aliWriter.stream.writeInt(((LayerID) info).getId());
                break;

            case TYPE_TOOL_INFO_KEY:
                mark = aliWriter.stream.getStreamPosition();
                TYPE_TOOL_INFO_IO.write(aliWriter, ((TypeToolInfo) info));
                size = aliWriter.stream.getStreamPosition() - mark;
                aliWriter.skipToPadBy(size, 4);
                break;

            case UNICODE_LAYER_NAME_KEY:
                aliWriter.writeUnicodeString(((UnicodeLayerName) info).getName());
                // That expected length thingy due to \0? Required to be set manually?
                break;

            default:
                // FIXME: No action... right?
                // Specifically, those people who just want to mutate the psd only slightly, might want to retain all other properties...
        }

        byte[] aliBytes = aliData.toByteArray();

        if (isLargeResource) writer.stream.writeLong(aliBytes.length);
        else writer.stream.writeInt(aliBytes.length);

        writer.writeBytes(aliBytes);

    }
}
