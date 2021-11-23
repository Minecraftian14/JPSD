package in.mcxiv.jpsd.data.layer.info.record.mask;

import in.mcxiv.jpsd.io.DataReader;

import java.io.IOException;

public class MaskParameter {

    private byte userMaskDensity = -1;
    private double userMaskFeather = -1;
    private byte vectorMaskDensity = -1;
    private double vectorMaskFeather = -1;

    public MaskParameter() {

    }

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

    public MaskParameterFlag getMaskParameterFlag() {
        MaskParameterFlag flag = new MaskParameterFlag();
        if (userMaskDensity != -1) flag.add(MaskParameterFlag.USER_MASK_DENSITY);
        if (userMaskFeather != -1) flag.add(MaskParameterFlag.USER_MASK_FEATHER);
        if (vectorMaskDensity != -1) flag.add(MaskParameterFlag.VECTOR_MASK_DENSITY);
        if (vectorMaskFeather != -1) flag.add(MaskParameterFlag.VECTOR_MASK_FEATHER);
        return flag;
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
