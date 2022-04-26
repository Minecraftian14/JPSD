package in.mcxiv.jpsd.structure.layer;

import in.mcxiv.jpsd.data.common.ColorComponents;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.ColorComponentsIO;

import java.io.IOException;

public class GlobalLayerMaskInfoIO extends SectionIO<GlobalLayerMaskInfo> {

    public GlobalLayerMaskInfoIO() {
        super(true);
    }

    @Override
    public GlobalLayerMaskInfo read(DataReader reader) throws IOException {

        long size = reader.stream.readUnsignedInt();
        if (size == 0) return null;

        ColorComponents color = ColorComponentsIO.INSTANCE.read(reader);
        short opacity = reader.stream.readShort();
        GlobalLayerMaskInfo.Kind kind = GlobalLayerMaskInfo.Kind.of(reader.stream.readByte());

        reader.stream.skipBytes(size - 13); // color:10 + opacity:2 + kind:1 = 13

        return new GlobalLayerMaskInfo((int) size, color, opacity, kind);
    }

    @Override
    public void write(DataWriter writer, GlobalLayerMaskInfo globalLayerMaskInfo) throws IOException {

        if (globalLayerMaskInfo == null) {
            writer.stream.writeInt(0);
            return;
        }

        writer.stream.writeInt(16);
        ColorComponentsIO.INSTANCE.write(writer, globalLayerMaskInfo.getColor());
        writer.stream.writeShort(globalLayerMaskInfo.getOpacity());
        writer.writeEntry(globalLayerMaskInfo.getKind());
        writer.fillZeros(3);
    }
}
