package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * A class representing both drop and inner shadows.
 * <p>
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_22203
 */
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

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ColorComponents getColorComponents() {
        return colorComponents;
    }

    public void setColorComponents(ColorComponents colorComponents) {
        this.colorComponents = colorComponents;
    }

    public BlendingMode getMode() {
        return mode;
    }

    public void setMode(BlendingMode mode) {
        this.mode = mode;
    }

    public boolean isUseAngleInAllEffects() {
        return useAngleInAllEffects;
    }

    public void setUseAngleInAllEffects(boolean useAngleInAllEffects) {
        this.useAngleInAllEffects = useAngleInAllEffects;
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
        return "Shadow{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", blur=" + blur +
                ", intensity=" + intensity +
                ", angle=" + angle +
                ", distance=" + distance +
                ", colorComponents=" + colorComponents +
                ", mode=" + mode +
                ", useAngleInAllEffects=" + useAngleInAllEffects +
                ", opacityAsPercent=" + opacityAsPercent +
                ", nativeColorComponents=" + nativeColorComponents +
                '}';
    }
}
