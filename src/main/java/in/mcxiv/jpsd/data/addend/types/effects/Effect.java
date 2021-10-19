package in.mcxiv.jpsd.data.addend.types.effects;

public abstract class Effect {

    protected final EffectType type;
    protected int version;
    protected boolean isEnabled;

    public Effect(EffectType type) {
        this.type = type;
    }
}
