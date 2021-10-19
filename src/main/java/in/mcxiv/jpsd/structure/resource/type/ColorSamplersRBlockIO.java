package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.SamplersResourceSubBlock;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.SamplersResourceSubBlock.ColorSpace;
import in.mcxiv.jpsd.data.resource.types.ColorSamplersRBlock.Version;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class ColorSamplersRBlockIO extends ImageResourceBlockIO<ColorSamplersRBlock> {

    public ColorSamplersRBlockIO() {
        super(true);
    }

    @Override
    public ColorSamplersRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        Version version = Version.of(reader.stream.readInt());
        int numberOfColorSamples = reader.stream.readInt();

        SamplersResourceSubBlock[] blocks = new SamplersResourceSubBlock[numberOfColorSamples];

        for (int i = 0; i < numberOfColorSamples; i++) {

            //@formatter:off
            int        versionS   = reader.stream.              readInt   ();
            int        horizontal = reader.stream.              readInt   ();    // Position
            int        vertical   = reader.stream.              readInt   ();    // Position
            ColorSpace colorSpace = ColorSpace.of(reader.stream.readShort ());
            short      depth      = reader.stream.              readShort ();
            //@formatter:on

            blocks[i] = new SamplersResourceSubBlock(versionS, horizontal, vertical, colorSpace, depth);
        }

        return new ColorSamplersRBlock(id, pascalString, blockLength, version, blocks);
    }

    @Override
    public void write(DataWriter writer, ColorSamplersRBlock data) {

        writer.writeInt(data.getVersion().getValue());
        writer.writeInt(data.getNumberOfColorSamples());

        for (SamplersResourceSubBlock block : data.getBlocks()) {
            writer.writeInt(block.getVersion());
            writer.writeInt(block.getHorizontal());
            writer.writeInt(block.getVersion());
            writer.writeShort(block.getColorSpace().getValue());
            writer.writeShort(block.getDepth());
        }
    }
}
