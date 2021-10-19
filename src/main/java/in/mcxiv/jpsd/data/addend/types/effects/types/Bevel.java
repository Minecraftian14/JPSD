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

    public Bevel() {
        super(EffectType.Bevel);
    }
}
