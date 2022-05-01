package in.mcxiv.jpsd.structure.layer;

import in.mcxiv.jpsd.data.common.OverlayColorComponents;
import in.mcxiv.jpsd.data.layer.GlobalLayerMaskInfo;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.OverlayColorComponentsIO;

import java.io.IOException;

public class GlobalLayerMaskInfoIO extends SectionIO<GlobalLayerMaskInfo> {

    public GlobalLayerMaskInfoIO() {
        super(true);
    }

    @Override
    public GlobalLayerMaskInfo read(DataReader reader) throws IOException {

        long size = reader.stream.readUnsignedInt();
        if (size == 0) return null;

        OverlayColorComponents color = OverlayColorComponentsIO.INSTANCE.read(reader);
        short opacity = reader.stream.readShort();
        GlobalLayerMaskInfo.Kind kind = GlobalLayerMaskInfo.Kind.of(reader.stream.readByte());

        if (size >= 13) {
            if (size % 2 == 1) size++;
            reader.stream.skipBytes(size - 13); // color:10 + opacity:2 + kind:1 = 13
        }

        return new GlobalLayerMaskInfo((int) size, color, opacity, kind);
    }

    @Override
    public void write(DataWriter writer, GlobalLayerMaskInfo globalLayerMaskInfo) throws IOException {

        if (globalLayerMaskInfo == null) {
            writer.stream.writeInt(0);
            return;
        }

        writer.stream.writeInt(16);
        OverlayColorComponentsIO.INSTANCE.write(writer, globalLayerMaskInfo.getColor());
        writer.stream.writeShort(globalLayerMaskInfo.getOpacity());
        writer.writeEntry(globalLayerMaskInfo.getKind());
        writer.fillZeros(3);
    }
}
