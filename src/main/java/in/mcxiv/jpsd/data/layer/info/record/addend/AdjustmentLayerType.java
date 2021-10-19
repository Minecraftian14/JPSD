package in.mcxiv.jpsd.data.layer.info.record.addend;

import in.mcxiv.jpsd.data.primitive.BytesEntry;

import java.nio.charset.StandardCharsets;

public enum AdjustmentLayerType implements BytesEntry {
    SolidColor("SoCo"),
    Gradient("GdFl"),
    Pattern("PtFl"),
    Brightness_Contrast("brit"),
    Levels("levl"),
    Curves("curv" ),
    Exposure("expA"),
    Vibrance("vibA"),
    Old_Hue_Saturation("hue "),
    New_Hue_Saturation("hue2"),
    ColorBalance("blnc"),
    BlackAndWhite("blwh"),
    PhotoFilter("phfl"),
    ChannelMixer("mixr"),
    ColorLookup("clrL"),
    Invert("nvrt"),
    Posterize("post"),
    Threshold("thrs"),
    GradientMap("grdm"),
    SelectiveColor("selc");

    private final byte[] value;

    AdjustmentLayerType(String data) {
        value = data.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public byte[] getValue() {
        return value;
    }
}
