package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;

public enum BlendingMode implements BytesEntry {
    PASS_THROUGH("pass"),
    NORMAL("norm"),
    DISSOLVE("diss"),
    DARKEN("dark"),
    MULTIPLY("mul"),
    COLOR_BURN("idiv"),
    LINEAR_BURN("lbrn"),
    DARKER_COLOR("dkCl"),
    LIGHTEN("lite"),
    SCREEN("scrn"),
    COLOR_DODGE("div"),
    LINEAR_DODGE("lddg"),
    LIGHTER_COLOR("lgCl"),
    OVERLAY("over"),
    SOFT_LIGHT("sLit"),
    HARD_LIGHT("hLit"),
    VIVID_LIGHT("vLit"),
    LINEAR_LIGHT("lLit"),
    PIN_LIGHT("pLit"),
    HARD_MIX("hMix"),
    DIFFERENCE("diff"),
    EXCLUSION("smud"),
    SUBTRACT("fsub"),
    DIVIDE("fdiv"),
    SATURATION("sat"),
    COLOR("colr"),
    LUMINOSITY("lum");

    private final byte[] value;

    BlendingMode(String name) {
        if (name.length() == 3) name += " ";
        value = name.getBytes(StandardCharsets.US_ASCII);
    }

    public static BlendingMode of(byte[] value) throws IllegalSignatureException {
        return BytesEntry.of(value, values());
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
