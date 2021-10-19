package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

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
}
