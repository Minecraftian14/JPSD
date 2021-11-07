package in.mcxiv.jpsd.data.common;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;

public enum BlendingMode implements BytesEntry {
    pass, norm, diss, dark, mul, idiv, lbrn, dkCl, lite, scrn, div, lddg, lgCl,
    over, sLit, hLit, vLit, lLit, pLit, hMix, diff, smud, fsub, fdiv, sat, colr, lum;

    private final byte[] value;

    BlendingMode() {
        String name = name();
        if (name.length() == 3) name += " ";
        value = name.getBytes(StandardCharsets.US_ASCII);
    }

    public static BlendingMode of(byte[] value) throws IllegalSignatureException {
        return BytesEntry.of(value,values());
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
