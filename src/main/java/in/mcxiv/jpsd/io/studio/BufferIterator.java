package in.mcxiv.jpsd.io.studio;

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

    public Cursor createCursor() {
        return new BufferIteratorCursor();
    }

    public class BufferIteratorCursor extends Cursor {
        @Override
        public void setPosition(int x, int y) {
            position = (x + width * y) * depth;
        }

        @Override
        public byte getData() {
            return data[position];
        }

        @Override
        public void setData(byte d) {
            data[position] = d;
        }
    }
}
