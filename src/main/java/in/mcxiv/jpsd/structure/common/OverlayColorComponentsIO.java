package in.mcxiv.jpsd.structure.common;

import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.common.OverlayColorComponents;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.SamplersResourceSubBlock.ColorSpace;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class OverlayColorComponentsIO extends SectionIO<OverlayColorComponents> {

    public static final OverlayColorComponentsIO INSTANCE = new OverlayColorComponentsIO();

    public OverlayColorComponentsIO() {
        super(false);
    }

    @Override
    public OverlayColorComponents read(DataReader reader) throws IOException {

        short colorSpace = reader.stream.readShort();
        short cc1 = reader.stream.readShort();
        short cc2 = reader.stream.readShort();
        short cc3 = reader.stream.readShort();
        short cc4 = reader.stream.readShort();

        return new OverlayColorComponents(colorSpace, cc1, cc2, cc3, cc4);
    }

    @Override
    public void write(DataWriter writer, OverlayColorComponents data) throws IOException {

        writer.stream.writeShort(data.getColorSpace());
        writer.stream.writeShort(data.getColorComponent1());
        writer.stream.writeShort(data.getColorComponent2());
        writer.stream.writeShort(data.getColorComponent3());
        writer.stream.writeShort(data.getColorComponent4());

    }
}
