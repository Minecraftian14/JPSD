package in.mcxiv.jpsd.structure.layer.info.record;

import in.mcxiv.jpsd.data.layer.info.record.LayerMaskData;
import in.mcxiv.jpsd.data.layer.info.record.mask.LayerMaskInfoFlag;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameter;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameterFlag;
import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.RectangleIO;

import java.io.IOException;

public class LayerMaskDataIO extends SectionIO<LayerMaskData> {

    public LayerMaskDataIO() {
        super(true);
    }

    @Override
    public LayerMaskData read(DataReader reader) throws IOException {

        int size = reader.stream.readInt();
        if (size == 0) return null;

        Rectangle maskEncloser = RectangleIO.INSTANCE.read(reader);
        byte defaultColor = reader.stream.readByte();

        LayerMaskInfoFlag layerMaskInfoFlag = new LayerMaskInfoFlag(reader.stream.readByte());

        MaskParameterFlag maskParameters = null;
        MaskParameter maskParameter = null;

        // Till now, 18 bytes have been read.
        // We need to know, how much data will be available to us after
        // reading MaskParameters IN CASE WE ACTUALLY HAVE TO READ IT.
        long mark = reader.stream.getStreamPosition();

        if (layerMaskInfoFlag.has(LayerMaskInfoFlag.HAVE_PARAMETERS_APPLIED)) {
            System.err.println("UNTESTED!!! This part of code is not reliable!");
            maskParameters = new MaskParameterFlag(reader.stream.readByte());
            maskParameter = new MaskParameter(maskParameters, reader);
        }

        long bytesReadSinceLastMark = reader.stream.getStreamPosition() - mark;
        long dataUnread = size - 18 - bytesReadSinceLastMark;

        if (size == 20 && dataUnread == 2) {
            reader.stream.skipBytes(2);
            return new LayerMaskData(maskEncloser, defaultColor, layerMaskInfoFlag, maskParameters, maskParameter);
        }

        if (dataUnread >= 2) {
            layerMaskInfoFlag = new LayerMaskInfoFlag(reader.stream.readByte());
            defaultColor = reader.stream.readByte();
            dataUnread -= 2;
        }

        if (dataUnread >= 16) {
            maskEncloser = RectangleIO.INSTANCE.read(reader);
        }

        return new LayerMaskData(maskEncloser, defaultColor, layerMaskInfoFlag, maskParameters, maskParameter);
    }

    @Override
    public void write(DataWriter writer, LayerMaskData layerMaskData) throws IOException {

    }
}
