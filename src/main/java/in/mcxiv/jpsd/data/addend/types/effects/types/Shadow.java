package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public abstract class Shadow extends Effect {

//  protected int version; // 0 for PS 5 and 2 for PS 5.5
    protected int blur;
    protected int intensity;
    protected int angle;//degrees
    protected int distance;
    protected ColorComponents colorComponents;
    protected BlendingMode mode;
    protected boolean useAngleInAllEffects;
    protected boolean opacityAsPercent;
    protected ColorComponents nativeColorComponents;

    public Shadow(EffectType type) {
        super(type);
    }
}
