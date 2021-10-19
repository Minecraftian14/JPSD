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
                    // FIXME: but that's an odd number!
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
                    //                                      reader.verifySignature(PSDFileReader.RESOURCE);
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

        return new EffectsLayer(0, effects.toArray(new Effect[0]));
    }

    @Override
    public void write(DataWriter writer, EffectsLayer effectsLayer) {

    }
}
