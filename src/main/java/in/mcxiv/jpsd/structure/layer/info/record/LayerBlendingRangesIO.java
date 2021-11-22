package in.mcxiv.jpsd.structure.layer.info.record;

import in.mcxiv.jpsd.data.layer.info.record.LayerBlendingRanges;
import in.mcxiv.jpsd.data.layer.info.record.LayerBlendingRanges.Blend;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class LayerBlendingRangesIO extends SectionIO<LayerBlendingRanges> {

    public LayerBlendingRangesIO() {
        super(true);
    }

    @Override
    public LayerBlendingRanges read(DataReader reader) throws IOException {

        int size = reader.stream.readInt();

        Blend grayComposite = readBlend(reader);

        int numberOfRanges = size - 8; // 4 x shorts = 4 x 2 = 8

        assert numberOfRanges % 8 == 0;
        Blend[] otherChannels = new Blend[numberOfRanges / 8];

        for (int i = 0, j = 0; i < numberOfRanges; i += 8, j++) otherChannels[j] = readBlend(reader);

        return new LayerBlendingRanges(grayComposite, otherChannels);
    }

    public static Blend readBlend(DataReader reader) throws IOException {
        short source_Black = reader.stream.readShort();
        short source_White = reader.stream.readShort();
        short dest_Black = reader.stream.readShort();
        short dest_White = reader.stream.readShort();

        return new Blend(source_Black, source_White, dest_Black, dest_White);
    }

    @Override
    public void write(DataWriter writer, LayerBlendingRanges layerBlendingRanges) throws IOException {

        Blend[] otherChannels = layerBlendingRanges.getOtherChannels();

        writer.stream.writeInt(8 + 8 * otherChannels.length);

        writeBlend(writer, layerBlendingRanges.getCompositeGray());

        for (int i = 0; i < otherChannels.length; i++)
            writeBlend(writer, otherChannels[i]);

    }

    private void writeBlend(DataWriter writer, Blend blend) throws IOException {
        writer.stream.writeInt(blend.getSource().getBlack());
        writer.stream.writeInt(blend.getSource().getWhite());
        writer.stream.writeInt(blend.getDestination().getBlack());
        writer.stream.writeInt(blend.getDestination().getWhite());
    }
}
