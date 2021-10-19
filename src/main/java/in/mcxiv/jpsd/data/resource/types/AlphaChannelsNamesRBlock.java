package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.Arrays;

public class AlphaChannelsNamesRBlock extends ImageResourceBlock {

    protected String[] channelNames;

    public AlphaChannelsNamesRBlock(ImageResourceID identity, String pascalString, long length, String[] channelNames) {
        super(identity, pascalString, length);
        this.channelNames = channelNames;
    }

    public String[] getNames() {
        return channelNames;
    }

    @Override
    public String toString() {
        return "AlphaChannelsNamesRBlock{" +
                "channelNames=" + Arrays.toString(channelNames) +
                '}';
    }
}
