package in.mcxiv.jpsd.data.sections;

import in.mcxiv.jpsd.data.DataObject;

import java.util.Arrays;

public class ColorModeData extends DataObject {

    private int length;
    private byte[] data;

    /**
     * If true, the data "may" represent {@link FileHeaderData.ColorMode#Indexed} or {@link FileHeaderData.ColorMode#Duotone} modes.
     * If false, length is simply 0.
     */
    private boolean hasData;

    public ColorModeData(int length, byte[] data) {
        if (data == null) data = new byte[0];
        if (data.length != length)
            throw new IllegalArgumentException("Data byte's length(" + data.length + ") and the given length(" + length + ") should be same! And don't ask why!!");
        this.length = length;
        this.data = data;
        this.hasData = length != 0;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }

    public boolean hasData() {
        return hasData;
    }

    @Override
    public String toString() {
        return "ColorModeData{" +
                "length=" + length +
                ", data=" + Arrays.toString(data) +
                ", hasData=" + hasData +
                '}';
    }
}
