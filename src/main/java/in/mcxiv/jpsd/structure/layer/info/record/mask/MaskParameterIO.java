package in.mcxiv.jpsd.structure.layer.info.record.mask;

import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameter;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameterFlag;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class MaskParameterIO {

    public static MaskParameter read(MaskParameterFlag maskParameters, DataReader reader) throws IOException {

        byte userMaskDensity = -1;
        double userMaskFeather = -1;
        byte vectorMaskDensity = -1;
        double vectorMaskFeather = -1;

        if (maskParameters.has(MaskParameterFlag.USER_MASK_DENSITY))
            userMaskDensity = reader.stream.readByte();

        if (maskParameters.has(MaskParameterFlag.USER_MASK_FEATHER))
            userMaskFeather = reader.stream.readDouble();

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_DENSITY))
            vectorMaskDensity = reader.stream.readByte();

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_FEATHER))
            vectorMaskFeather = reader.stream.readDouble();

        return new MaskParameter(userMaskDensity, userMaskFeather, vectorMaskDensity, vectorMaskFeather);
    }

    public static void write(MaskParameterFlag maskParameters,DataWriter writer, MaskParameter maskParameter) throws IOException {
        if (maskParameters.has(MaskParameterFlag.USER_MASK_DENSITY))
            writer.stream.writeByte(maskParameter.getUserMaskDensity());

        if (maskParameters.has(MaskParameterFlag.USER_MASK_FEATHER))
            writer.stream.writeDouble(maskParameter.getUserMaskFeather());

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_DENSITY))
            writer.stream.writeByte(maskParameter.getVectorMaskDensity());

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_FEATHER))
            writer.stream.writeDouble(maskParameter.getVectorMaskFeather());
    }
}
