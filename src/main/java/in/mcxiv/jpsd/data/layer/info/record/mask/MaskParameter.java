package in.mcxiv.jpsd.data.layer.info.record.mask;

import in.mcxiv.jpsd.data.DataObject;

public class MaskParameter extends DataObject {

    private byte userMaskDensity = -1;
    private double userMaskFeather = -1;
    private byte vectorMaskDensity = -1;
    private double vectorMaskFeather = -1;


    public MaskParameter(byte userMaskDensity, double userMaskFeather, byte vectorMaskDensity, double vectorMaskFeather) {
        this.userMaskDensity = userMaskDensity;
        this.userMaskFeather = userMaskFeather;
        this.vectorMaskDensity = vectorMaskDensity;
        this.vectorMaskFeather = vectorMaskFeather;
    }

    public MaskParameterFlag getMaskParameterFlag() {
        MaskParameterFlag flag = new MaskParameterFlag();
        if (userMaskDensity != -1) flag.add(MaskParameterFlag.USER_MASK_DENSITY);
        if (userMaskFeather != -1) flag.add(MaskParameterFlag.USER_MASK_FEATHER);
        if (vectorMaskDensity != -1) flag.add(MaskParameterFlag.VECTOR_MASK_DENSITY);
        if (vectorMaskFeather != -1) flag.add(MaskParameterFlag.VECTOR_MASK_FEATHER);
        return flag;
    }

    public byte getUserMaskDensity() {
        return userMaskDensity;
    }

    public void setUserMaskDensity(byte userMaskDensity) {
        this.userMaskDensity = userMaskDensity;
    }

    public double getUserMaskFeather() {
        return userMaskFeather;
    }

    public void setUserMaskFeather(double userMaskFeather) {
        this.userMaskFeather = userMaskFeather;
    }

    public byte getVectorMaskDensity() {
        return vectorMaskDensity;
    }

    public void setVectorMaskDensity(byte vectorMaskDensity) {
        this.vectorMaskDensity = vectorMaskDensity;
    }

    public double getVectorMaskFeather() {
        return vectorMaskFeather;
    }

    public void setVectorMaskFeather(double vectorMaskFeather) {
        this.vectorMaskFeather = vectorMaskFeather;
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
