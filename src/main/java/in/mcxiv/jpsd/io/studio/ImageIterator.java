package in.mcxiv.jpsd.io.studio;

import in.mcxiv.jpsd.data.file.DepthEntry;

import java.awt.color.ColorSpace;
import java.awt.image.*;

public abstract class ImageIterator {

    public BufferedImage image;
    public int width;
    public int height;
    public ColorModel colorModel;
    public WritableRaster raster;
    public DataBuffer buffer;
    public SampleModel sampleModel;
    public ColorSpace colorSpace;
    public int channels;
    public ValueDepthConvertor valueConvertor;

    public ImageIterator(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.colorModel = image.getColorModel();
        this.raster = image.getRaster();
        this.buffer = raster.getDataBuffer();
        this.sampleModel = raster.getSampleModel();
        this.colorSpace = colorModel.getColorSpace();
        this.channels = sampleModel.getNumBands();
        this.valueConvertor = new ValueDepthConvertor(DepthEntry.of(this.buffer));
    }


    public static class BandedImageIterator extends ImageIterator {
        public BandedImageIterator(BufferedImage image) {
            super(image);
        }
    }

    public class Depth8Cursor extends ImageIteratorCursor{

        @Override
        public void getData(Cursor data) {

        }

        @Override
        public void setData(Cursor data) {
        }
    }

    public abstract class ImageIteratorCursor {

        protected int x = 0;
        protected int y = 0;

        public abstract void getData(Cursor data);

        public abstract void setData(Cursor data);

    }

}
