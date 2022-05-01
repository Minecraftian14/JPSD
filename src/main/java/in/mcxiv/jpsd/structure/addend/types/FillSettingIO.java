package in.mcxiv.jpsd.structure.addend.types;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.types.FillSetting;
import in.mcxiv.jpsd.data.common.complex.Descriptor;
import in.mcxiv.jpsd.io.DataReader;
import in.mcxiv.jpsd.io.DataWriter;
import in.mcxiv.jpsd.structure.SectionIO;

import java.io.IOException;

public class FillSettingIO extends SectionIO<FillSetting> {

    public FillSettingIO() {
        super(true);
    }

    @Override
    public FillSetting read(DataReader reader) throws IOException {
        return null;
    }

    public FillSetting readFillSetting(AdditionalInfoKey key, long size, DataReader reader) throws IOException {
        // FIXME: Implement DescriptorIO
        return new FillSetting(key, size, reader.stream.readInt(), null);
    }

    @Override
    public void write(DataWriter writer, FillSetting fillSettingIO) throws IOException {

    }
}
