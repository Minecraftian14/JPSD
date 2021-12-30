package in.mcxiv.jpsd.data.addend.types.effects;

/**
 * An abstract class to contain common properties between all the effects.
 * <p>
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_71546
 * under Effects Layer (Photoshop 5.0)
 */
public abstract class Effect {

    protected final EffectType type;
    protected int version;
    protected boolean isEnabled;

    public Effect(EffectType type, int version, boolean isEnabled) {
        this.type = type;
        this.version = version;
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                '}';
    }

    public EffectType getType() {
        return type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
