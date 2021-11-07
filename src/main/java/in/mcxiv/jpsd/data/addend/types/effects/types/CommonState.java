package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_41831
 */
public class CommonState extends Effect {

    public CommonState() {
        super(EffectType.CommonState, 0, true);
    }

    @Override
    public String toString() {
        return "CommonState{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
