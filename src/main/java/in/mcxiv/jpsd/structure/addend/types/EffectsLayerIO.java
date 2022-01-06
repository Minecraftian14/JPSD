package in.mcxiv.jpsd.structure.addend.types;

import in.mcxiv.jpsd.data.addend.types.EffectsLayer;
import in.mcxiv.jpsd.data.addend.types.effects.Effect;
import in.mcxiv.jpsd.data.addend.types.effects.EffectType;
import in.mcxiv.jpsd.data.addend.types.effects.types.*;
import in.mcxiv.jpsd.data.common.BlendingMode;
import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.io.PSDFileReader;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.ColorComponentsIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EffectsLayerIO extends SectionIO<EffectsLayer> {

    public EffectsLayerIO() {
        super(true);
    }

    @Override
    public EffectsLayer read(DataReader reader) throws IOException {
        return readEffectsLayer(reader, -1);
    }

    public EffectsLayer readEffectsLayer(DataReader reader, long length) throws IOException {

        //@formatter:off
        short version = reader.stream.readShort();
        short count   = reader.stream.readShort();
        //@formatter:on

        List<Effect> effects = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            reader.verifySignature(PSDFileReader.RESOURCE);

            EffectType type = EffectType.of(reader.readBytes(4, false));

            switch (type) {
                case CommonState: {
                    // This byte block always contain the same data!
                    reader.stream.skipBytes(4 + 7);
                    effects.add(new CommonState());
                    // FIXED: But that's an odd number of bytes for the size of this block!
                    // FIX: After referring to 0a9e420[1].psd, it doesn't look like we need an extra byte...
                    // 8BIMcmnS...7....1..8BIMdsdw->...
                    // ^   ^      ^    ^  ^   ^
                    // sig typ    0x7 0x1 sig typ
                }
                break;

                case DropShadow:
                case InnerShadow:
                    // Space
                {
                    //@formatter:off
                    int             size                  = reader.stream.readInt();
                    int             versionS              = reader.stream.readInt();
                    int             blur                  = reader.stream.readInt();
                    int             intensity             = reader.stream.readInt();
                    int             angle                 = reader.stream.readInt();
                    int             distance              = reader.stream.readInt();
                    ColorComponents colorComponents       = ColorComponentsIO.INSTANCE.read(reader);
                    reader.verifySignature(PSDFileReader.RESOURCE);
                    BlendingMode    mode                  = BlendingMode.of(reader.readBytes(4, false));
                    boolean         effectEnabled         = reader.stream.readBoolean();
                    boolean         useAngleInAllEffects  = reader.stream.readBoolean();
                    boolean         opacityAsPercent      = reader.stream.readBoolean();
                    //@formatter:on
                    ColorComponents nativeColorComponents;
                    if (size == 51) nativeColorComponents = ColorComponentsIO.INSTANCE.read(reader);
                    else /* size == 41 */ nativeColorComponents = null;
                    effects.add(new Shadow(type, versionS, effectEnabled, blur, intensity, angle, distance, colorComponents, mode, useAngleInAllEffects, opacityAsPercent, nativeColorComponents));
                }
                break;

                case OuterGlow:
                case InnerGlow:
                    // Space
                {
                    //@formatter:off
                    reader.stream.skipBytes(4);
                    int             versionS         = reader.stream.readInt();
                    int             blur             = reader.stream.readInt();
                    int             intensity        = reader.stream.readInt(); // FIXME: um? float?
                    ColorComponents colorComponents  = ColorComponentsIO.INSTANCE.read(reader);
                    reader.verifySignature(PSDFileReader.RESOURCE);
                    BlendingMode    blendingMode     = BlendingMode.of(reader.readBytes(4, false));
                    boolean         effectEnabled    = reader.stream.readBoolean();
                    boolean         opacityAsPercent = reader.stream.readBoolean();
                    //@formatter:on
                    boolean invert = false;
                    ColorComponents nativeColorComponents;
                    if (versionS == 2) {
                        if (EffectType.InnerGlow.equals(type)) invert = reader.stream.readBoolean();
                        nativeColorComponents = ColorComponentsIO.INSTANCE.read(reader);
                    } else /* versionS == 0 */ nativeColorComponents = null;
                    if (EffectType.OuterGlow.equals(type))
                        effects.add(new OuterGlow(versionS, effectEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent, nativeColorComponents));
                    else
                        effects.add(new InnerGlow(versionS, effectEnabled, blur, intensity, colorComponents, blendingMode, opacityAsPercent, invert, nativeColorComponents));

                }
                break;

                case Bevel: {
                    //@formatter:off
                    reader.stream.skipBytes(4);
                    int             versionS              = reader.stream.readInt();
                    int             angle                 = reader.stream.readInt();
                    int             strength              = reader.stream.readInt();
                    int             blur                  = reader.stream.readInt();
                    reader.verifySignature(PSDFileReader.RESOURCE);
                    BlendingMode    highlightBlendingMode = BlendingMode.of(reader.readBytes(4, false));;
                    reader.verifySignature(PSDFileReader.RESOURCE);
                    BlendingMode    shadowBlendingMode    = BlendingMode.of(reader.readBytes(4, false));;
                    ColorComponents highlightColor        = ColorComponentsIO.INSTANCE.read(reader);;
                    ColorComponents shadowColor           = ColorComponentsIO.INSTANCE.read(reader);;
                    byte            bevelStyle            = reader.stream.readByte();
                    byte            highlightOpacity      = reader.stream.readByte();
                    byte            shadowOpacity         = reader.stream.readByte();
                    boolean         effectEnabled         = reader.stream.readBoolean();
                    boolean         useAngleInAllEffect   = reader.stream.readBoolean();;
                    boolean         upOrDown              = reader.stream.readBoolean();;
                    //@formatter:on
                    ColorComponents realHighlightColor = null;
                    ColorComponents realShadowColor = null;
                    if (versionS == 2) {
                        realHighlightColor = ColorComponentsIO.INSTANCE.read(reader);
                        realShadowColor = ColorComponentsIO.INSTANCE.read(reader);
                    }
                    effects.add(new Bevel(versionS, effectEnabled, angle, strength, blur, highlightBlendingMode, shadowBlendingMode, highlightColor, shadowColor, bevelStyle, highlightOpacity, shadowOpacity, useAngleInAllEffect, upOrDown, realHighlightColor, realShadowColor));
                }
                break;

                case SolidFill: {
                    //@formatter:off
                    reader.stream.skipBytes(4);
                    int             versionS              = reader.stream.readInt();
                                                            reader.verifySignature(PSDFileReader.RESOURCE);
                    BlendingMode    mode                  = BlendingMode.of(reader.readBytes(4, false));
                    ColorComponents color                 = ColorComponentsIO.INSTANCE.read(reader);
                    byte            opacity               = reader.stream.readByte();
                    boolean         effectEnabled         = reader.stream.readBoolean();
                    ColorComponents nativeColor           = ColorComponentsIO.INSTANCE.read(reader);
                    //@formatter:on
                    effects.add(new SolidFill(versionS, effectEnabled, mode, color, opacity, nativeColor));
                }
                break;
            }
        }

        return new EffectsLayer(length, version, effects.toArray(new Effect[0]));
    }

    @Override
    public void write(DataWriter writer, EffectsLayer effectsLayer) throws IOException {

        //@formatter:off
        writer.stream.writeShort(effectsLayer.getVersion());
        writer.stream.writeShort(effectsLayer.getEffects().length);
        //@formatter:on

        List<Effect> effects = new ArrayList<>();

        for (Effect effect : effectsLayer.getEffects()) {

            writer.sign(PSDFileReader.RESOURCE);

            writer.writeBytes(effect.getType().getValue());

            switch (effect.getType()) {
                case CommonState: {
                    // This byte block always contain the same data!
                    writer.stream.writeInt(7);        // 7 =
                    writer.stream.writeInt(0);        //     4
                    writer.stream.writeBoolean(true); //   + 1
                    writer.stream.writeShort(0);      //   + 2
                }
                break;

                case DropShadow:
                case InnerShadow:
                    // Space
                {
                    Shadow shadow = (Shadow) effect;
                    //@formatter:off
                    writer.stream              .writeInt     (shadow.getNativeColorComponents()==null ?41 :51);
                    writer.stream              .writeInt     (shadow.getVersion());
                    writer.stream              .writeInt     (shadow.getBlur());
                    writer.stream              .writeInt     (shadow.getIntensity());
                    writer.stream              .writeInt     (shadow.getAngle());
                    writer.stream              .writeInt     (shadow.getDistance());
                    ColorComponentsIO.INSTANCE .write        (writer, shadow.getColorComponents());
                    writer                     .sign         (PSDFileReader.RESOURCE);
                    writer                     .writeBytes   (shadow.getMode().getValue());
                    writer.stream              .writeBoolean (shadow.isEnabled());
                    writer.stream              .writeBoolean (shadow.isUseAngleInAllEffects());
                    writer.stream              .writeBoolean (shadow.isOpacityAsPercent());
                    //@formatter:on
                    if (shadow.getNativeColorComponents() != null)
                        ColorComponentsIO.INSTANCE.write(writer, shadow.getNativeColorComponents());
                }
                break;

                case OuterGlow:
                case InnerGlow:
                    // Space
                {
                    Glow glow = (Glow) effect;
                    //@formatter:off
                    writer.stream              .writeInt     (glow.getVersion()==2 ?42 :32);
                    writer.stream              .writeInt     (glow.getVersion());
                    writer.stream              .writeInt     (glow.getBlur());
                    writer.stream              .writeInt     (glow.getIntensity());
                    ColorComponentsIO.INSTANCE .write(writer, glow.getColorComponents());
                    writer                     .sign         (PSDFileReader.RESOURCE);
                    writer                     .writeEntry   (glow.getBlendingMode());
                    writer.stream              .writeBoolean (glow.isEnabled());
                    writer.stream              .writeBoolean (glow.isOpacityAsPercent());
                    //@formatter:on
                    if (glow.getVersion() == 2) {
                        if (EffectType.InnerGlow.equals(glow.getType()))
                            writer.stream.writeBoolean(((InnerGlow) glow).isInvert());
                        ColorComponentsIO.INSTANCE.write(writer, glow.getNativeColorComponents());
                    }
                }
                break;

                case Bevel: {
                    Bevel bevel = (Bevel) effect;
                    //@formatter:off
                    writer.stream              .writeInt     (bevel.getVersion()==2 ?78 :58);
                    writer.stream              .writeInt     (bevel.getVersion());
                    writer.stream              .writeInt     (bevel.getAngle());
                    writer.stream              .writeInt     (bevel.getStrength());
                    writer                     .sign         (PSDFileReader.RESOURCE);
                    writer                     .writeEntry   (bevel.getHighlightBlendingMode());
                    writer                     .sign         (PSDFileReader.RESOURCE);
                    writer                     .writeEntry   (bevel.getShadowBlendingMode());
                    ColorComponentsIO.INSTANCE .write        (writer, bevel.getHighlightColor());
                    ColorComponentsIO.INSTANCE .write        (writer, bevel.getShadowColor());
                    writer.stream              .writeByte    (bevel.getBevelStyle());
                    writer.stream              .writeByte    (bevel.getHighlightOpacity());
                    writer.stream              .writeByte    (bevel.getShadowOpacity());
                    writer.stream              .writeBoolean (bevel.isEnabled());
                    writer.stream              .writeBoolean (bevel.isUseAngleInAllEffect());
                    writer.stream              .writeBoolean (bevel.isUpOrDown());
                    //@formatter:on
                    if (bevel.getVersion() == 2) {
                        ColorComponentsIO.INSTANCE.write(writer, bevel.getRealHighlightColor());
                        ColorComponentsIO.INSTANCE.write(writer, bevel.getRealShadowColor());
                    }
                }
                break;

                case SolidFill: {
                    SolidFill solidFill = (SolidFill) effect;
                    //@formatter:off
                    writer.stream              .writeInt     (34);
                    writer.stream              .writeInt     (solidFill.getVersion());
                    writer                     .sign         (PSDFileReader.RESOURCE);
                    writer                     .writeEntry   (solidFill.getMode());
                    ColorComponentsIO.INSTANCE .write(writer, solidFill.getColor());
                    writer.stream              .writeByte    (solidFill.getOpacity());
                    writer.stream              .writeBoolean (solidFill.isEnabled());
                    ColorComponentsIO.INSTANCE .write(writer, solidFill.getNativeColor());
                    //@formatter:on
                }
                break;
            }
        }
    }
}
