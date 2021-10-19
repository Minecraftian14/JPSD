package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public class Shadow extends Effect {

    protected int blur;
    protected int intensity;
    protected int angle;//degrees
    protected int distance;
    protected ColorComponents colorComponents;
    protected BlendingMode mode;
    protected boolean useAngleInAllEffects;
    protected boolean opacityAsPercent;
    protected ColorComponents nativeColorComponents;

    public Shadow(EffectType type, int version, boolean isEnabled, int blur, int intensity, int angle, int distance, ColorComponents colorComponents, BlendingMode mode, boolean useAngleInAllEffects, boolean opacityAsPercent, ColorComponents nativeColorComponents) {
        super(type, version, isEnabled);
        this.blur = blur;
        this.intensity = intensity;
        this.angle = angle;
        this.distance = distance;
        this.colorComponents = colorComponents;
        this.mode = mode;
        this.useAngleInAllEffects = useAngleInAllEffects;
        this.opacityAsPercent = opacityAsPercent;
        this.nativeColorComponents = nativeColorComponents;
    }
}
