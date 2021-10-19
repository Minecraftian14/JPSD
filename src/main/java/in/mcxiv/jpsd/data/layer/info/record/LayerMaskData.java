package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.layer.info.record.mask.LayerMaskInfoFlag;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameter;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameterFlag;
import in.mcxiv.jpsd.data.common.Rectangle;

// Can be sized 40, 24, or 4 bytes only.
public class LayerMaskData extends DataObject {

    private Rectangle maskEncloser;
    private byte defaultColor;
    private LayerMaskInfoFlag layerMaskInfoFlag;
    private MaskParameterFlag maskParameters; // Present only if layerMaskInfoFlag.has(HAVE_PARAMETERS_APPLIED) is true/
    private MaskParameter maskParameter; // Present only if maskParameters is present

    public LayerMaskData(Rectangle maskEncloser, byte defaultColor, LayerMaskInfoFlag layerMaskInfoFlag, MaskParameterFlag maskParameters, MaskParameter maskParameter) {
        this.maskEncloser = maskEncloser;
        this.defaultColor = defaultColor;
        this.layerMaskInfoFlag = layerMaskInfoFlag;
        this.maskParameters = maskParameters;
        this.maskParameter = maskParameter;
    }

    @Override
    public String toString() {
        return "LayerMaskData{" +
                "maskEncloser=" + maskEncloser +
                ", defaultColor=" + defaultColor +
                ", layerMaskInfoFlag=" + layerMaskInfoFlag +
                ", maskParameters=" + maskParameters +
                ", maskParameter=" + maskParameter +
                '}';
    }
}
