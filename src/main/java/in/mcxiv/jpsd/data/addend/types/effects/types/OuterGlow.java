package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public class OuterGlow extends Glow {

    protected ColorComponents nativeColorComponents;

    public OuterGlow(int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent, ColorComponents nativeColorComponents) {
        super(EffectType.OuterGlow, version, isEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent);
        this.nativeColorComponents = nativeColorComponents;
    }
}
