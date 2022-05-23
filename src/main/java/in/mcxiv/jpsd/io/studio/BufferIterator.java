package in.mcxiv.jpsd.io.studio;

import java.awt.image.*;

public abstract class BufferIterator {

    public byte[] data;
    public int width;
    public int height;
    public int channels;
    public int depth;

    public BufferIterator(int width, int height, int channels, int depth) {
        this.width = width;
        this.height = height;
        this.channels = channels;
        this.depth = depth;
        this.data = new byte[this.width * this.height * this.channels * this.depth];
    }

    public void setRed(int x, int y, byte[] color) {

    }
}
