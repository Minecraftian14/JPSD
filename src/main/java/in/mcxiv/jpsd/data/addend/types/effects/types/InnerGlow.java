package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_27692
 */
public class InnerGlow extends Glow {

    private boolean invert;

    public InnerGlow(int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent, boolean invert, ColorComponents nativeColorComponents) {
        super(EffectType.InnerGlow, version, isEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent, nativeColorComponents);
        this.invert = invert;
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    @Override
    public String toString() {
        return "InnerGlow{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", blur=" + blur +
                ", intensity=" + intensity +
                ", colorComponents=" + colorComponents +
                ", blendingMode=" + blendingMode +
                ", opacityAsPercent=" + opacityAsPercent +
                ", invert=" + invert +
                ", nativeColorComponents=" + nativeColorComponents +
                '}';
    }
}
