package in.mcxiv.jpsd.data.addend;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;

public enum AdditionalInfoKey implements BytesEntry {

    UNICODE_LAYER_NAME_KEY("luni", false),
    LAYER_ID_KEY("lyid", false),

    LAYER_NAME_SOURCE_SETTING("lnsr", false),
    BLEND_CLIPPING_ELEMENTS("clbl", false),
    BLEND_INTERIOR_ELEMENTS("infx", false),
    KNOCKOUT_SETTING("knko", false),
    PROTECTED_SETTING("lspf", false),
    SHEET_COLOR_SETTING("lclr", false),
    METADATA_SETTING("shmd", false),
    REFERENCE_POINT("fxrp", false),

    USER_MASK("LMsk", true),
    LAYER_AND_MASK_INFO_16("Lr16", true),
    LAYER_AND_MASK_INFO_32("Lr32", true),
    LAYER_AND_MASK_INFO("Layr", true),
    SAVING_MERGED_TRANSPARENCY_16("Mt16", true),
    SAVING_MERGED_TRANSPARENCY_32("Mt32", true),
    SAVING_MERGED_TRANSPARENCY("Mtrn", true),
    Alph("Alph", true),
    FILTER_MASK("FMsk", true),
    LINKED_LAYER_2("lnk2", true),
    FILTER_EFFECTS_E("FEid", true),
    FILTER_EFFECTS_X("FXid", true),
    PIXEL_SOURCE_DATA("PxSD", true),

    LINKED_LAYER_3("lnk3", false),
    LINKED_LAYER("lnkD", false),

    COMPOSITOR_USED("cinf", false),

    EFFECTS_KEY("lrFX", false),
    TYPE_TOOL_INFO_KEY("tySh", false),
    TYPE_TOOL_OBJECT_KEY("TySh", false),
    OBJECT_BASED_EFFECTS_LAYER_KEY("lfx2", false),

    TEXT_ENGINE_DATA("Txt2", false),

    PATTERN("Patt", false),
    PATTERN2("Pat2", false),
    PATTERN3("Pat3", false);

    private final byte[] value;
    private final boolean isLarge;

    AdditionalInfoKey(String key, boolean isLarge) {
        this.value = key.getBytes(StandardCharsets.US_ASCII);
        this.isLarge = isLarge;
    }

    public static AdditionalInfoKey of(byte[] bytes) throws IllegalSignatureException {
        return BytesEntry.of(bytes, values());
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    public boolean isLarge() {
        return isLarge;
    }

    @Override
    public String toString() {
        return "Key:" + name() + ":" + new String(value);
    }
}
