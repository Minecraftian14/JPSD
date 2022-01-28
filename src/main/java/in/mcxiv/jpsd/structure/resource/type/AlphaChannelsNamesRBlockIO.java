package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.AlphaChannelsNamesRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlphaChannelsNamesRBlockIO extends ImageResourceBlockIO<AlphaChannelsNamesRBlock> {

    public AlphaChannelsNamesRBlockIO() {
        super(true);
    }

    @Override
    public AlphaChannelsNamesRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        long endPosition = reader.stream.getStreamPosition() + blockLength;

        List<String> strings = new ArrayList<>();

        while (reader.stream.getStreamPosition() < endPosition)
            strings.add(reader.readPascalStringRaw());

        return new AlphaChannelsNamesRBlock(id, pascalString, blockLength, strings.toArray(new String[0]));
    }

    @Override
    public void write(DataWriter writer, AlphaChannelsNamesRBlock alphaChannelsNamesRBlock) throws IOException {
        for (String channelName : alphaChannelsNamesRBlock.getNames())
            writer.writePascalStringRaw(channelName);

    }
}
