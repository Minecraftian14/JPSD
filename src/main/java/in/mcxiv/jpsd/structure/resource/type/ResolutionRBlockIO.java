package in.mcxiv.jpsd.structure.resource.type;

import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock.ResUnit;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock.Unit;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.resource.ImageResourceBlockIO;

import java.io.IOException;

public class ResolutionRBlockIO extends ImageResourceBlockIO<ResolutionRBlock> {

    public ResolutionRBlockIO() {
        super(true);
    }

    @Override
    public ResolutionRBlock read(DataReader reader, ImageResourceID id, String pascalString, long blockLength) throws IOException {

        //@formatter:off

        float   HDpi              = reader.                    readFFloat   ();
        ResUnit HResDisplayUnit   = ResUnit.of(reader.stream.  readShort    ());
        Unit    WidthDisplayUnit  = Unit.of(reader.stream.     readShort    ());
        float   VDpi              = reader.                    readFFloat   ();
        ResUnit VResDisplayUnit   = ResUnit.of(reader.stream.  readShort    ());
        Unit    HeightDisplayUnit = Unit.of(reader.stream.     readShort    ());

        //@formatter:on

        return new ResolutionRBlock(id, pascalString, blockLength, HDpi, HResDisplayUnit, WidthDisplayUnit, VDpi, VResDisplayUnit, HeightDisplayUnit);
    }

    @Override
    public void write(DataWriter writer, ResolutionRBlock data) {
        //@formatter:off

        writer. writeFFloat (data.  getHDpi                 ());
        writer. writeShort  (data.  getHResDisplayUnit      ().getValue());
        writer. writeShort  (data.  getWidthDisplayUnit     ().getValue());
        writer. writeFFloat (data.  getVDpi                 ());
        writer. writeShort  (data.  getVResDisplayUnit      ().getValue());
        writer. writeShort  (data.  getHeightDisplayUnit    ().getValue());

        //@formatter:on
    }
}
