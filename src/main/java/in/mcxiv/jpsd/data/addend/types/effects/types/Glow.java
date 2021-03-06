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
    protected ColorComponents nativeColorComponents;

    public Glow(boolean isInner, int version, boolean isEnabled, int blur, int intensity, ColorComponents colorComponents, BlendingMode blendingMode, boolean opacityAsPercent, ColorComponents nativeColorComponents) {
        super(isInner ? EffectType.InnerGlow : EffectType.OuterGlow, version, isEnabled);
        this.blur = blur;
        this.intensity = intensity;
        this.colorComponents = colorComponents;
        this.blendingMode = blendingMode;
        this.opacityAsPercent = opacityAsPercent;
        this.nativeColorComponents = nativeColorComponents;
    }

    public int getBlur() {
        return blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public ColorComponents getColorComponents() {
        return colorComponents;
    }

    public void setColorComponents(ColorComponents colorComponents) {
        this.colorComponents = colorComponents;
    }

    public BlendingMode getBlendingMode() {
        return blendingMode;
    }

    public void setBlendingMode(BlendingMode blendingMode) {
        this.blendingMode = blendingMode;
    }

    public boolean isOpacityAsPercent() {
        return opacityAsPercent;
    }

    public void setOpacityAsPercent(boolean opacityAsPercent) {
        this.opacityAsPercent = opacityAsPercent;
    }

    public ColorComponents getNativeColorComponents() {
        return nativeColorComponents;
    }

    public void setNativeColorComponents(ColorComponents nativeColorComponents) {
        this.nativeColorComponents = nativeColorComponents;
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
               ", nativeColorComponents=" + nativeColorComponents +
               '}';
    }
}
