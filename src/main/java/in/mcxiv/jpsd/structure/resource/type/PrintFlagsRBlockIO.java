package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.PrintFlagsRBlock;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class PrintFlagsRBlockIO extends ImageResourceBlockIO<PrintFlagsRBlock> {

    public PrintFlagsRBlockIO() {
        super(false);
    }

    @Override
    public PrintFlagsRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        //@formatter:off
        boolean labels             = reader.stream.readBoolean();
        boolean crop_masks         = reader.stream.readBoolean();
        boolean color_bars         = reader.stream.readBoolean();
        boolean registration_marks = reader.stream.readBoolean();
        boolean negative           = reader.stream.readBoolean();
        boolean flip               = reader.stream.readBoolean();
        boolean interpolate        = reader.stream.readBoolean();
        boolean caption            = reader.stream.readBoolean();
        boolean print_flags        = reader.stream.readBoolean();
        //@formatter:on

        return new PrintFlagsRBlock(id, pascalString, blockLength, labels, crop_masks, color_bars, registration_marks, negative, flip, interpolate, caption, print_flags);
    }

    @Override
    public void write(DataWriter writer, PrintFlagsRBlock printFlagsRBlock) throws IOException {
        writer.stream.writeBoolean(printFlagsRBlock.isLabels());
        writer.stream.writeBoolean(printFlagsRBlock.isCropMasks());
        writer.stream.writeBoolean(printFlagsRBlock.isColorBars());
        writer.stream.writeBoolean(printFlagsRBlock.isRegistrationMarks());
        writer.stream.writeBoolean(printFlagsRBlock.isNegative());
        writer.stream.writeBoolean(printFlagsRBlock.isFlip());
        writer.stream.writeBoolean(printFlagsRBlock.isInterpolate());
        writer.stream.writeBoolean(printFlagsRBlock.isCaption());
        writer.stream.writeBoolean(printFlagsRBlock.isPrintFlags());
    }
}
