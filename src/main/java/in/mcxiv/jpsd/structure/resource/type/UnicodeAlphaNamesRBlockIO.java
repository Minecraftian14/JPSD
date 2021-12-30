package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.UnicodeAlphaNamesRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnicodeAlphaNamesRBlockIO extends ImageResourceBlockIO<UnicodeAlphaNamesRBlock> {

    public UnicodeAlphaNamesRBlockIO() {
        super(true);
    }

    @Override
    public UnicodeAlphaNamesRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        long endPosition = reader.stream.getStreamPosition() + blockLength;

        List<String> strings = new ArrayList<>();

        while (reader.stream.getStreamPosition() < endPosition) {

            String channelName = reader.readUnicodeString();

            if (channelName.endsWith("\0"))
                channelName = channelName.substring(0, channelName.length() - 1);

            strings.add(channelName);
        }

        return new UnicodeAlphaNamesRBlock(id, pascalString, blockLength, strings.toArray(new String[0]));
    }

    @Override
    public void write(DataWriter writer, UnicodeAlphaNamesRBlock data) throws IOException {
        for (String name : data.getNames())
            writer.writeUnicodeString(name + "\0");
    }
}
