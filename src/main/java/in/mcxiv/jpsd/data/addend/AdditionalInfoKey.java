package in.mcxiv.jpsd.data.addend;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;

public enum AdditionalInfoKey implements BytesEntry {

    EFFECTS_KEY("lrFX", false),
    TYPE_TOOL_INFO_KEY("tySh", false),
    UNICODE_LAYER_NAME_KEY("luni", false),
    LAYER_ID_KEY("lyid", false),
    OBJECT_BASED_EFFECTS_LAYER_KEY("lfx2", false),
    PATTERN("Patt", false),
    PATTERN2("Pat2", false),
    PATTERN3("Pat3", false),
    ANNOTATIONS("Anno", false),
    BLEND_CLIPPING_ELEMENTS("clbl", false),
    BLEND_INTERIOR_ELEMENTS("infx", false),
    KNOCKOUT_SETTING("knko", false),
    PROTECTED_SETTING("lspf", false),
    SHEET_COLOR_SETTING("lclr", false),
    REFERENCE_POINT("fxrp", false),
    GRADIENT_SETTINGS("grdm", false),
    SECTION_DIVIDER_SETTING("lsct", false),
    CHANNEL_BLENDING_RESTRICTIONS_SETTING("brst", false),
    SOLID_COLOR_SHEET_SETTING("SoCo", false),
    PATTERN_FILL_SETTING("PtFl", false),
    GRADIENT_FILL_SETTING("GdFl", false),
    VECTOR_MASK_SETTING("vmsk", false),
    VECTOR_MASK_SETTING_PS6("vsms", false),
    TYPE_TOOL_OBJECT_KEY("TySh", false),
    FOREIGN_EFFECT_ID("ffxi", false),
    LAYER_NAME_SOURCE_SETTING("lnsr", false),
    PATTERN_DATA("shpa", false),
    METADATA_SETTING("shmd", false),
    LAYER_VERSION("lyvr", false),
    TRANSPARENCY_SHAPES_LAYER("tsly", false),
    LAYER_MASK_AS_GLOBAL_MASK("lmgm", false),
    VECTOR_MASK_AS_GLOBAL_MASK("vmgm", false),
    BRIGHTNESS_AND_CONTRAST("brit", false),
    CHANNEL_MIXER("mixr", false),
    COLOR_LOOKUP("clrL", false),
    PLACED_LAYER_OLD("plLd", false),
    LINKED_LAYER("lnkD", false),
    LINKED_LAYER_2("lnk2", true) /*LARGE*/,
    LINKED_LAYER_3("lnk3", false),
    PHOTO_FILTER("phfl", false),
    BLACK_WHITE("blwh", false),
    CONTENT_GENERATOR_EXTRA_DATA("CgEd", false),
    TEXT_ENGINE_DATA("Txt2", false),
    VIBRANCE("vibA", false),
    UNICODE_PATH_NAME("pths", false),
    ANIMATION_EFFECTS("anFX", false),
    FILTER_MASK("FMsk", true) /*LARGE*/,
    PLACED_LAYER_DATA("SoLd", false),
    VECTOR_STROKE_DATA("vstk", false),
    VECTOR_STROKE_CONTENT_DATA("vscg", false),
    USING_ALIGNED_RENDERING("sn2P", false),
    VECTOR_ORIGINATION_DATA("vogk", false),
    PIXEL_SOURCE_DATA_CC("PxSc", false),
    COMPOSITOR_USED("cinf", false),
    PIXEL_SOURCE_DATA_CC_15("PxSD", true) /*LARGE*/,
    ART_BOARD_DATA("artb", false),
    ART_BOARD_DATA_D("artd", false),
    ART_BOARD_DATA_DD("abdd", false),
    SMART_OBJECT_LAYER_DATA("SoLE", false),
    SAVING_MERGED_TRANSPARENCY("Mtrn", true) /*LARGE*/,
    SAVING_MERGED_TRANSPARENCY_16("Mt16", true) /*LARGE*/,
    SAVING_MERGED_TRANSPARENCY_32("Mt32", true) /*LARGE*/,
    USER_MASK("LMsk", true) /*LARGE*/,
    EXPOSURE("expA", false),
    FILTER_EFFECTS_X("FXid", true) /*LARGE*/,
    FILTER_EFFECTS_E("FEid", true) /*LARGE*/,

    LAYER_AND_MASK_INFO_16("Lr16", true) /*LARGE*/,
    LAYER_AND_MASK_INFO_32("Lr32", true) /*LARGE*/,
    LAYER_AND_MASK_INFO("Layr", true) /*LARGE*/,
    // The only mention of Alph is given here https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/#:~:text=Mt16%2C%20Mt32%2C%20Mtrn%2C-,Alph,-%2C%20FMsk%2C%20lnk2%2C%20FEid
    Alph("Alph", true) /*LARGE*/;

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
