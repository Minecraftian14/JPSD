package in.mcxiv.jpsd.io;

import java.awt.image.*;

public class Utility {

    public static final Map2Dto1D BANDED_INDEX_MAP = (w, x, h, y, s, c) -> y * w + x + c * w * h;
    public static final Map2Dto1D INTERLEAVED_INDEX_MAP = (w, x, h, y, s, c) -> s * (y * w + x) + c;

    private static final int[] RGBtoBGR = {2, 1, 0};

    /**
     * PSD always saves data in RGB, but the image can be BGR or something else too.
     * @return a mapper which always returns 0 for R, 1 for G and 2 for B given any image.
     */
    public static FunctionII getComponentMap(BufferedImage image) {
        // if RGB
//        return val -> val;
        // if BGR
        return val -> RGBtoBGR[val];
    }

    public static Map2Dto1D getIndexMap(SampleModel model) {
        if (model instanceof BandedSampleModel) return BANDED_INDEX_MAP;
        if (model instanceof PixelInterleavedSampleModel) return INTERLEAVED_INDEX_MAP;
        if (model instanceof SinglePixelPackedSampleModel) return null;
        throw new UnsupportedOperationException();
    }

    public interface Map2Dto1D {
        /**
         * Converts a mapping in the image where (x, y) are the indices of a pixel to linear data bank which have s components for each pixel.
         *
         * @param w upper limit of x
         * @param x abscissa in [0, w)
         * @param h upper limit of y
         * @param y ordinate in [0, h)
         * @param s upper limit of c
         * @param c sample in [0, s)
         * @return
         */
        int map(int w, int x, int h, int y, int s, int c);
    }

    public interface FunctionII {
        int map(int val);
    }

}
