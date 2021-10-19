package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;

public class CommonState extends Effect {

    private byte unused;

    public CommonState() {
        super(EffectType.CommonState);
    }

}
