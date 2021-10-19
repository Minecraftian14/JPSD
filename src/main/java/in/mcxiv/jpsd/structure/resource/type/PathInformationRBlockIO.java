package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.common.PointData;
import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.path.Selector;
import in.mcxiv.jpsd.data.path.types.*;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.PathInformationRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathInformationRBlockIO extends ImageResourceBlockIO<PathInformationRBlock> {

    private short fixedId;

    public PathInformationRBlockIO(short fixedId) {
        super(true);
        this.fixedId = fixedId;
    }

    @Override
    public PathInformationRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        if (blockLength % 26 != 0) throw new IOException();

        long expectedEnd = reader.stream.getStreamPosition() + blockLength;

        List<PathRecord> list = new ArrayList<>();

        while (reader.stream.getStreamPosition() < expectedEnd) {

            Selector selector = Selector.of(reader.stream.readShort());

            switch (selector) {

                case PATH_FILL_RULE_RECORD:
                    list.add(new PathFillRule());
                    break;

                case CLOSED_SUB_PATH_LENGTH_RECORD:
                case OPEN_SUB_PATH_LENGTH_RECORD:
                    short length = reader.stream.readShort();
                    list.add(new SubPathLengthRecord(selector, length));
                    break;

                case CLOSED_SUB_PATH_BEZIER_KNOT_LINKED:
                case CLOSED_SUB_PATH_BEZIER_KNOT_UNLINKED:
                case OPEN_SUB_PATH_BEZIER_KNOT_LINKED:
                case OPEN_SUB_PATH_BEZIER_KNOT_UNLINKED:
                    PointData preceding_control = readPoint(reader);
                    PointData anchor = readPoint(reader);
                    PointData leaving_control = readPoint(reader);
                    list.add(new BezierKnotRecord(selector, preceding_control, anchor, leaving_control));
                    break;

                case CLIPBOARD_RECORD:
                    // FIXME: Umm? We had to use readFFloat only, right?
                    float top = reader.readFFloat();
                    float lef = reader.readFFloat();
                    float bot = reader.readFFloat();
                    float rig = reader.readFFloat();
                    float res = reader.readFFloat();
                    list.add(new ClipboardRecord(top, lef, bot, rig, res));
                    break;

                case INITIAL_FILL_RULE_RECORD:
                    short fillStart = reader.stream.readShort();
                    list.add(new InitialFillRuleRecord(fillStart));
                    break;
            }

        }

        return new PathInformationRBlock(pascalString, blockLength, fixedId, list.toArray(new PathRecord[0]));
    }

    private PointData readPoint(DataReader reader) throws IOException {
        double position_x = reader.readFDouble();
        double position_y = reader.readFDouble();
        return new PointData(position_x, position_y);
    }

    @Override
    public void write(DataWriter writer, PathInformationRBlock pathInformationRBlock) {

    }
}
