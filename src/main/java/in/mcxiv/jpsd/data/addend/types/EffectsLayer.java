package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;

import java.util.Arrays;

public class EffectsLayer extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.EFFECTS_KEY;

    private Effect[] effects;

    public EffectsLayer(long length, Effect[] effects) {
        super(KEY, length);
        this.effects = effects;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public void setEffects(Effect[] effects) {
        this.effects = effects;
    }

    @Override
    public String toString() {
        return "EffectsLayer{" +
                "effects=" + Arrays.toString(effects) +
                '}';
    }
}
