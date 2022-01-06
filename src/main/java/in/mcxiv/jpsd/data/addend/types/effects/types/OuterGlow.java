package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_25738
 */
public class OuterGlow extends Glow {

    public OuterGlow(int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent, ColorComponents nativeColorComponents) {
        super(EffectType.OuterGlow, version, isEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent, nativeColorComponents);
    }

    @Override
    public String toString() {
        return "OuterGlow{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", blur=" + blur +
                ", intensity=" + intensity +
                ", colorComponents=" + colorComponents +
                ", blendingMode=" + blendingMode +
                ", opacityAsPercent=" + opacityAsPercent +
                ", nativeColorComponents=" + nativeColorComponents +
                '}';
    }
}
