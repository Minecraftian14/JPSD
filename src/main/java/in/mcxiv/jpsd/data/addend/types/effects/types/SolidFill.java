package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.BlendingMode;

public class SolidFill extends Effect {

    private BlendingMode mode;
    private ColorComponents color;
    private byte opacity;
    private ColorComponents nativeColor;

    public SolidFill() {
        super(EffectType.SolidFill);
    }
}
