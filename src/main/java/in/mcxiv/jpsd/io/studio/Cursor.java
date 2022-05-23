package in.mcxiv.jpsd.io.studio;

public abstract class Cursor {

    protected int position = 0;

    public abstract void setPosition(int x, int y);

    public abstract byte getData();

    public abstract void setData(byte data);

    public byte getData(int offset) {
        position += offset;
        byte data = getData();
        position -= offset;
        return data;
    }

    public void setData(int offset, byte data) {
        position += offset;
        setData(data);
        position -= offset;
    }

}
