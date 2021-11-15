package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_31889
 */
public class Bevel extends Effect {

    private int angle;
    private int strength;
    private int blur;
    private BlendingMode highlightBlendingMode;
    private BlendingMode shadowBlendingMode;
    private ColorComponents highlightColor;
    private ColorComponents shadowColor;
    private byte bevelStyle;
    private byte highlightOpacity;
    private byte shadowOpacity;
    private boolean useAngleInAllEffect;
    private boolean upOrDown;

    private ColorComponents realHighlightColor;
    private ColorComponents realShadowColor;

    public Bevel(int version, boolean isEnabled, int angle, int strength, int blur, BlendingMode highlightBlendingMode, BlendingMode shadowBlendingMode, ColorComponents highlightColor, ColorComponents shadowColor, byte bevelStyle, byte highlightOpacity, byte shadowOpacity, boolean useAngleInAllEffect, boolean upOrDown, ColorComponents realHighlightColor, ColorComponents realShadowColor) {
        super(EffectType.Bevel, version, isEnabled);
        this.angle = angle;
        this.strength = strength;
        this.blur = blur;
        this.highlightBlendingMode = highlightBlendingMode;
        this.shadowBlendingMode = shadowBlendingMode;
        this.highlightColor = highlightColor;
        this.shadowColor = shadowColor;
        this.bevelStyle = bevelStyle;
        this.highlightOpacity = highlightOpacity;
        this.shadowOpacity = shadowOpacity;
        this.useAngleInAllEffect = useAngleInAllEffect;
        this.upOrDown = upOrDown;
        this.realHighlightColor = realHighlightColor;
        this.realShadowColor = realShadowColor;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getBlur() {
        return blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }

    public BlendingMode getHighlightBlendingMode() {
        return highlightBlendingMode;
    }

    public void setHighlightBlendingMode(BlendingMode highlightBlendingMode) {
        this.highlightBlendingMode = highlightBlendingMode;
    }

    public BlendingMode getShadowBlendingMode() {
        return shadowBlendingMode;
    }

    public void setShadowBlendingMode(BlendingMode shadowBlendingMode) {
        this.shadowBlendingMode = shadowBlendingMode;
    }

    public ColorComponents getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(ColorComponents highlightColor) {
        this.highlightColor = highlightColor;
    }

    public ColorComponents getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(ColorComponents shadowColor) {
        this.shadowColor = shadowColor;
    }

    public byte getBevelStyle() {
        return bevelStyle;
    }

    public void setBevelStyle(byte bevelStyle) {
        this.bevelStyle = bevelStyle;
    }

    public byte getHighlightOpacity() {
        return highlightOpacity;
    }

    public void setHighlightOpacity(byte highlightOpacity) {
        this.highlightOpacity = highlightOpacity;
    }

    public byte getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowOpacity(byte shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }

    public boolean isUseAngleInAllEffect() {
        return useAngleInAllEffect;
    }

    public void setUseAngleInAllEffect(boolean useAngleInAllEffect) {
        this.useAngleInAllEffect = useAngleInAllEffect;
    }

    public boolean isUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(boolean upOrDown) {
        this.upOrDown = upOrDown;
    }

    public ColorComponents getRealHighlightColor() {
        return realHighlightColor;
    }

    public void setRealHighlightColor(ColorComponents realHighlightColor) {
        this.realHighlightColor = realHighlightColor;
    }

    public ColorComponents getRealShadowColor() {
        return realShadowColor;
    }

    public void setRealShadowColor(ColorComponents realShadowColor) {
        this.realShadowColor = realShadowColor;
    }

    @Override
    public String toString() {
        return "Bevel{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", angle=" + angle +
                ", strength=" + strength +
                ", blur=" + blur +
                ", highlightBlendingMode=" + highlightBlendingMode +
                ", shadowBlendingMode=" + shadowBlendingMode +
                ", highlightColor=" + highlightColor +
                ", shadowColor=" + shadowColor +
                ", bevelStyle=" + bevelStyle +
                ", highlightOpacity=" + highlightOpacity +
                ", shadowOpacity=" + shadowOpacity +
                ", useAngleInAllEffect=" + useAngleInAllEffect +
                ", upOrDown=" + upOrDown +
                ", realHighlightColor=" + realHighlightColor +
                ", realShadowColor=" + realShadowColor +
                '}';
    }
}
