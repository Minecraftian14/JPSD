package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceID;

import java.util.Arrays;

public class UnicodeAlphaNamesRBlock extends AlphaChannelsNamesRBlock {

    public UnicodeAlphaNamesRBlock(ImageResourceID identity, String pascalString, long length, String[] channelNames) {
        super(identity, pascalString, length, channelNames);
    }



    @Override
    public String toString() {
        return "UnicodeAlphaNamesRBlock{" +
                "channelNames=" + Arrays.toString(channelNames) +
                '}';
    }
}
