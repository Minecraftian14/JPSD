package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * An abstract class to contain common properties between inner glow and outer glow.
 *
 * @see InnerGlow
 * @see OuterGlow
 */
public abstract class Glow extends Effect {

    protected int blur;
    protected int intensity;
    protected ColorComponents colorComponents;
    protected BlendingMode blendingMode;
    protected boolean opacityAsPercent;

    public Glow(EffectType type, int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent) {
        super(type, version, isEnabled);
        this.blur = blur;
        this.intensity = intensity;
        this.colorComponents = colorComponents;
        this.blendingMode = blendingMode;
        this.opacityAsPercent = opacityAsPercent;
    }

    @Override
    public String toString() {
        return "Glow{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", blur=" + blur +
                ", intensity=" + intensity +
                ", colorComponents=" + colorComponents +
                ", blendingMode=" + blendingMode +
                ", opacityAsPercent=" + opacityAsPercent +
                '}';
    }
}
