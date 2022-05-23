package in.mcxiv.jpsd.data.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.addend.types.effects.Effect;

import java.util.Arrays;

public class EffectsLayer extends AdditionalLayerInfo {

    public static final AdditionalInfoKey KEY = AdditionalInfoKey.EFFECTS_KEY;

    private short version;
    private Effect[] effects;

    public EffectsLayer(short version, Effect[] effects) {
        this(-1, version, effects);
    }

    public EffectsLayer(long length, short version, Effect[] effects) {
        super(KEY, length);
        this.version = version;
        this.effects = effects;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public short getVersion() {
        return version;
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
