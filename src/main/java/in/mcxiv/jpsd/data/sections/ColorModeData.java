package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;
import in.mcxiv.jpsd.data.file.ColorMode;

import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.util.Arrays;

public class ColorModeData extends DataObject {

    private byte[] data;

    /**
     * If true, the data "may" represent {@link ColorMode#Indexed} or {@link ColorMode#Duotone} modes.
     * If false, length is simply 0.
     */
    private boolean hasData;

    public ColorModeData() {
        this(0, null);
    }

    public ColorModeData(int length, byte[] data) {
        if (data == null) data = new byte[0];
        if (data.length != length)
            throw new IllegalArgumentException("Data byte's length(" + data.length + ") and the given length(" + length + ") should be same! And don't ask why!!");
        this.data = data;
        this.hasData = length != 0;
    }

    public int getLength() {
        return data.length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.hasData = data.length!=0;
    }

    public boolean hasData() {
        return hasData;
    }

    @Override
    public String toString() {
        return "ColorModeData{" +
                "length=" + data.length +
                ", data=" + Arrays.toString(data) +
                ", hasData=" + hasData +
                '}';
    }
}
