package in.mcxiv.jpsd.structure.common;

import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class RectangleIO extends SectionIO<Rectangle> {

    public static final RectangleIO INSTANCE = new RectangleIO();

    public RectangleIO() {
        super(false);
    }

    @Override
    public Rectangle read(DataReader reader) throws IOException {

        int top = reader.stream.readInt();
        int lef = reader.stream.readInt();
        int bot = reader.stream.readInt();
        int rig = reader.stream.readInt();

        return new Rectangle(top, lef, bot, rig);
    }

    @Override
    public void write(DataWriter writer, Rectangle rectangle) throws IOException {
        writer.stream.writeInt(rectangle.getTop());
        writer.stream.writeInt(rectangle.getLef());
        writer.stream.writeInt(rectangle.getBot());
        writer.stream.writeInt(rectangle.getRig());
    }
}
