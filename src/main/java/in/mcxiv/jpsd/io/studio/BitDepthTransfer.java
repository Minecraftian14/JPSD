package in.mcxiv.jpsd.io.studio;

public final class BitDepthTransfer {

    private BitDepthTransfer() {
    }

    public static void transferByte(byte data, Cursor target) {
        target.setData(data);
    }

    public static void transferShort(short data, Cursor target) {
        target.setData(0, (byte) ((data >> 8) & 0xFF));
        target.setData(1, (byte) (data & 0xFF));
    }

    public static void transferInt(int data, Cursor target) {
        target.setData(0, (byte) ((data >> 24) & 0xFF));
        target.setData(1, (byte) ((data >> 16) & 0xFF));
        target.setData(2, (byte) ((data >> 8) & 0xFF));
        target.setData(3, (byte) (data & 0xFF));
    }

}
