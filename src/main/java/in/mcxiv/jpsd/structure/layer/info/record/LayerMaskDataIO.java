package in.mcxiv.jpsd.structure.layer.info.record;

import in.mcxiv.jpsd.data.common.Rectangle;
import in.mcxiv.jpsd.data.layer.info.record.LayerMaskData;
import in.mcxiv.jpsd.data.layer.info.record.mask.LayerMaskInfoFlag;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameter;
import in.mcxiv.jpsd.data.layer.info.record.mask.MaskParameterFlag;
import in.mcxiv.jpsd.exceptions.UnknownByteBlockException;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.common.RectangleIO;
import in.mcxiv.jpsd.structure.layer.info.record.mask.MaskParameterIO;

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
            maskParameters = new MaskParameterFlag(reader.stream.readByte());
            maskParameter = MaskParameterIO.read(maskParameters, reader);
        }

        long bytesReadSinceLastMark = reader.stream.getStreamPosition() - mark;
        long dataUnread = size - 18 - bytesReadSinceLastMark;

        if (size == 20 && dataUnread == 2) {
            reader.stream.skipBytes(2);
            return new LayerMaskData(maskEncloser, defaultColor, layerMaskInfoFlag, maskParameter);
        }

        if (dataUnread >= 2) {
            layerMaskInfoFlag = new LayerMaskInfoFlag(reader.stream.readByte());
            defaultColor = reader.stream.readByte();
            dataUnread -= 2;
        }

        if (dataUnread >= 16) {
            maskEncloser = RectangleIO.INSTANCE.read(reader);
        }

        if (dataUnread > 0)
            throw new UnknownByteBlockException("Too many bytes to read! About " + dataUnread + " remaining.");

        return new LayerMaskData(maskEncloser, defaultColor, layerMaskInfoFlag, maskParameter);
    }

    @Override
    public void write(DataWriter writer, LayerMaskData layerMaskData) throws IOException {

        if (layerMaskData == null) {
            writer.stream.writeInt(0);
            return;
        }

        DataWriter buffer = new DataWriter();

        RectangleIO.INSTANCE.write(buffer, layerMaskData.getMaskEncloser());
        buffer.stream.writeByte(layerMaskData.getDefaultColor());
        buffer.writeEntry(layerMaskData.getLayerMaskInfoFlag());

        if (layerMaskData.getLayerMaskInfoFlag().has(LayerMaskInfoFlag.HAVE_PARAMETERS_APPLIED)) {
            buffer.writeEntry(layerMaskData.getMaskParameters());
                MaskParameterIO.write(layerMaskData.getMaskParameters(), buffer, layerMaskData.getMaskParameter());
        } else {
            buffer.fillZeros(2); // padding to 20.
        }

        byte[] bytes = buffer.toByteArray();
        writer.stream.writeInt(bytes.length);
        writer.writeBytes(bytes);
    }
}
