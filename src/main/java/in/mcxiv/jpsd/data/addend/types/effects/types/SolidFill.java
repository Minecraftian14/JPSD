package in.mcxiv.jpsd.data.addend.types.effects.types;

import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;

/**
 * https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_70055
 */
public class SolidFill extends Effect {

    private BlendingMode mode;
    private ColorComponents color;
    private byte opacity;
    private ColorComponents nativeColor;

    public SolidFill(int version, boolean isEnabled, BlendingMode mode, ColorComponents color, byte opacity, ColorComponents nativeColor) {
        super(EffectType.SolidFill, version, isEnabled);
        this.mode = mode;
        this.color = color;
        this.opacity = opacity;
        this.nativeColor = nativeColor;
    }

    public BlendingMode getMode() {
        return mode;
    }

    public void setMode(BlendingMode mode) {
        this.mode = mode;
    }

    public ColorComponents getColor() {
        return color;
    }

    public void setColor(ColorComponents color) {
        this.color = color;
    }

    public byte getOpacity() {
        return opacity;
    }

    public void setOpacity(byte opacity) {
        this.opacity = opacity;
    }

    public ColorComponents getNativeColor() {
        return nativeColor;
    }

    public void setNativeColor(ColorComponents nativeColor) {
        this.nativeColor = nativeColor;
    }

    @Override
    public String toString() {
        return "SolidFill{" +
                "type=" + type +
                ", version=" + version +
                ", isEnabled=" + isEnabled +
                ", mode=" + mode +
                ", color=" + color +
                ", opacity=" + opacity +
                ", nativeColor=" + nativeColor +
                '}';
    }
}
