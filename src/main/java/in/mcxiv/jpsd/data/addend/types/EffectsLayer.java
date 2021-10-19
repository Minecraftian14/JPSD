package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;

public class EffectsLayer extends AdditionalLayerInfo {

    public static final String EFFECTS_SIGNATURE = "8BIM";

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.EFFECTS_KEY;

    private Effect[] effects;

    public EffectsLayer(long length, Effect[] effects) {
        super(KEY, length);
        this.effects = effects;
    }
}
