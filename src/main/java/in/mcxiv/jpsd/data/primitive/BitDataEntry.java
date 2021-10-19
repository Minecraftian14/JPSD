package in.mcxiv.jpsd.data.primitive;

public abstract class BitDataEntry implements ByteEntry {

    private byte value;

    public BitDataEntry(byte value) {
        this.value = value;
    }

    public void add(byte b) {
        this.value |= b;
    }

    public void remove(byte b) {
        this.value &= ~b;
    }

    public boolean has(byte b) {
        return (this.value & b) > 0;
    }

    @Override
    public byte getValue() {
        return value;
    }

}
