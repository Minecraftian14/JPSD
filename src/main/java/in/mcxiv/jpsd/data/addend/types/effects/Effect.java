package in.mcxiv.jpsd.data.addend.types.effects;

public abstract class Effect {

    protected final EffectType type;
    protected int version;
    protected boolean isEnabled;

    public Effect(EffectType type, int version, boolean isEnabled) {
        this.type = type;
        this.version = version;
        this.isEnabled = isEnabled;
    }
}
