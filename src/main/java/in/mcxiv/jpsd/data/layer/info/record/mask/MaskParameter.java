package in.mcxiv.jpsd.data.layer.info.record.mask;

import in.mcxiv.jpsd.io.DataReader;

import java.io.IOException;

public class MaskParameter {

    private byte userMaskDensity;
    private double userMaskFeather;
    private byte vectorMaskDensity;
    private double vectorMaskFeather;

    public MaskParameter(MaskParameterFlag maskParameters, DataReader reader) throws IOException {

        if (maskParameters.has(MaskParameterFlag.USER_MASK_DENSITY))
            userMaskDensity = reader.stream.readByte();

        if (maskParameters.has(MaskParameterFlag.USER_MASK_FEATHER))
            userMaskFeather = reader.stream.readDouble();

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_DENSITY))
            vectorMaskDensity = reader.stream.readByte();

        if (maskParameters.has(MaskParameterFlag.VECTOR_MASK_FEATHER))
            vectorMaskFeather = reader.stream.readDouble();

    }

    @Override
    public String toString() {
        return "MaskParameter{" +
                "userMaskDensity=" + userMaskDensity +
                ", userMaskFeather=" + userMaskFeather +
                ", vectorMaskDensity=" + vectorMaskDensity +
                ", vectorMaskFeather=" + vectorMaskFeather +
                '}';
    }
}
