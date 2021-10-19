package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public class InnerGlow extends Glow {

    private boolean invert;
    protected ColorComponents nativeColorComponents;

    public InnerGlow(int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent, boolean invert, ColorComponents nativeColorComponents) {
        super(EffectType.InnerGlow, version, isEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent);
        this.invert = invert;
        this.nativeColorComponents = nativeColorComponents;
    }
}
