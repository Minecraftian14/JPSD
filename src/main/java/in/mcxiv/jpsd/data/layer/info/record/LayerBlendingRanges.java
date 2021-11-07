package in.mcxiv.jpsd.data.layer.info.record;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;

import java.util.Arrays;

public class LayerBlendingRanges extends DataObject {

    public static class Blend {
        private Range source;
        private Range destination;

        public Blend(short blackSource,short whiteSource,short blackDestination,short whiteDestination ) {
            this.source = new Range(blackSource, whiteSource);
            this.destination = new Range(blackDestination, whiteDestination);
        }

        @Override
        public String toString() {
            return "Blend{" +
                    "source=" + source +
                    ", destination=" + destination +
                    '}';
        }
    }

    public static class Range {
        private short black;
        private short white;

        public Range(short black, short white) {
            this.black = black;
            this.white = white;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "black=" + black +
                    ", white=" + white +
                    '}';
        }
    }

    private Blend compositeGray;
    private Blend[] otherChannels;

    public LayerBlendingRanges(Blend compositeGray, Blend[] otherChannels) {
        this.compositeGray = compositeGray;
        this.otherChannels = otherChannels;
    }

    @Override
    public String toString() {
        return "LayerBlendingRanges{" +
                "compositeGray=" + compositeGray +
                ", otherChannels=" + Arrays.toString(otherChannels) +
                '}';
    }
}
