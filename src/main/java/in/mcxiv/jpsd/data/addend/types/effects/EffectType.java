package in.mcxiv.jpsd.data.addend.types.effects;

import in.mcxiv.jpsd.data.primitive.BytesEntry;
import in.mcxiv.jpsd.exceptions.IllegalSignatureException;

import java.nio.charset.StandardCharsets;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_71546
 * under Effects Layer (Photoshop 5.0)
 */
public enum EffectType implements BytesEntry {
    CommonState("cmnS"),
    DropShadow("dsdw"),
    InnerShadow("isdw"),
    OuterGlow("oglw"),
    InnerGlow("iglw"),
    Bevel("bevl"),
    SolidFill("sofi");

    private final byte[] value;

    EffectType(String v) {
        value = v.getBytes(StandardCharsets.US_ASCII);
    }

    public static EffectType of(byte[] value) throws IllegalSignatureException {
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
