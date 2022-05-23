package in.mcxiv.jpsd.io.studio;

import in.mcxiv.jpsd.data.file.DepthEntry;

public class ValueDepthConvertor implements IndexFunction {

    private final DepthEntry depthEntry;

    public ValueDepthConvertor(DepthEntry depthEntry) {
        this.depthEntry = depthEntry;
    }

    @Override
    public int apply(int index) {
        return depthEntry.getBytes() * index;
    }

}
