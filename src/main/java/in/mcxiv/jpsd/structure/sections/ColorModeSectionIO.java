package in.mcxiv.jpsd.structure.sections;

import in.mcxiv.jpsd.data.sections.ColorModeData;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

/**
 * Total bytes = 4 or 772 or 4+
 *
 * 4 bytes Section size s= 0 if color mode is NOT indexed and duotone // The size of upcoming data.
 * s bytes Color data    = If indexed color mode length is 768 consisting of the color table in non interleaved order.
 *                    Else if duotone it contains the duotone specification. (undocumented)
 */
public class ColorModeSectionIO extends SectionIO<ColorModeData> {

    public ColorModeSectionIO() {
        super(true);
    }

    @Override
    public ColorModeData read(DataReader reader) throws IOException {

        int sectionLength = reader.stream.readInt();
        long mark = reader.stream.getStreamPosition();

        byte[] data = reader.readBytes(sectionLength, true);

        checkBytesCount(sectionLength,mark,reader.stream.getStreamPosition());

        return new ColorModeData(sectionLength, data);
    }

    @Override
    public void write(DataWriter writer, ColorModeData data) throws IOException {

        writer.stream.writeInt(data.getLength());
        if (data.hasData())
            writer.writeBytes(data.getData());

    }
}
