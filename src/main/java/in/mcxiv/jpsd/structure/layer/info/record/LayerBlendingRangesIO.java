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

        short compositeGraySource_Black = reader.stream.readShort();
        short compositeGraySource_White = reader.stream.readShort();
        short compositeGrayDest_Black = reader.stream.readShort();
        short compositeGrayDest_White = reader.stream.readShort();

        Blend grayComposite = new Blend(compositeGraySource_Black, compositeGraySource_White, compositeGrayDest_Black, compositeGrayDest_White);

        int numberOfRanges = size - 8; // 4 x shorts = 4 x 2 = 8

        assert numberOfRanges % 8 == 0;
        Blend[] otherChannels = new Blend[numberOfRanges / 8];

        for (int i = 0, j = 0; i < numberOfRanges; i += 8, j++) {
            short iThChannelSourceBlack = reader.stream.readShort();
            short iThChannelSourceWhite = reader.stream.readShort();
            short iThChannelDestBlack = reader.stream.readShort();
            short iThChannelDestWhite = reader.stream.readShort();

            otherChannels[j] = new Blend(iThChannelSourceBlack, iThChannelSourceWhite, iThChannelDestBlack, iThChannelDestWhite);
        }

        return new LayerBlendingRanges(grayComposite, otherChannels);
    }

    @Override
    public void write(DataWriter writer, LayerBlendingRanges layerBlendingRanges) {

    }
}
