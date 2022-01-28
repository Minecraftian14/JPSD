package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.layer.info.record.mask.LayerMaskInfoFlag;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameter;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameterFlag;

// Can be sized 40, 24, or 4 bytes only.
public class LayerMaskData extends DataObject {

    private Rectangle maskEncloser;
    private byte defaultColor;
    private LayerMaskInfoFlag layerMaskInfoFlag;
    private MaskParameter maskParameter; // Present only if maskParameters is present

    public LayerMaskData(Rectangle maskEncloser, byte defaultColor, LayerMaskInfoFlag layerMaskInfoFlag, MaskParameter maskParameter) {
        this.maskEncloser = maskEncloser;
        this.defaultColor = defaultColor;
        this.layerMaskInfoFlag = layerMaskInfoFlag;
        this.maskParameter = maskParameter;
    }

    public Rectangle getMaskEncloser() {
        return maskEncloser;
    }

    public void setMaskEncloser(Rectangle maskEncloser) {
        this.maskEncloser = maskEncloser;
    }

    public byte getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(byte defaultColor) {
        this.defaultColor = defaultColor;
    }

    public LayerMaskInfoFlag getLayerMaskInfoFlag() {
        return layerMaskInfoFlag;
    }

    public void setLayerMaskInfoFlag(LayerMaskInfoFlag layerMaskInfoFlag) {
        this.layerMaskInfoFlag = layerMaskInfoFlag;
    }

    public MaskParameterFlag getMaskParameters() {
        if (maskParameter == null) return null;
        return maskParameter.getMaskParameterFlag();
    }

    public MaskParameter getMaskParameter() {
        return maskParameter;
    }

    public void setMaskParameter(MaskParameter maskParameter) {
        this.maskParameter = maskParameter;
    }

    @Override
    public String toString() {
        return "LayerMaskData{" +
                "maskEncloser=" + maskEncloser +
                ", defaultColor=" + defaultColor +
                ", layerMaskInfoFlag=" + layerMaskInfoFlag +
                ", maskParameter=" + maskParameter +
                '}';
    }
}
