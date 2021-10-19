package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public class InnerGlow extends Effect {

    private int blur;
    private int intensity;
    private ColorComponents colorComponents;
    private BlendingMode blendingMode;
    private boolean opacityAsPercent;
    private boolean invert;
    protected ColorComponents nativeColorComponents;

    public InnerGlow() {
        super(EffectType.InnerGlow);
    }
}
