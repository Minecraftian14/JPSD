package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.path.PathRecord;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class PathInformationRBlock extends ImageResourceBlock {

    private short identity;
    private PathRecord records[];

    public PathInformationRBlock(String pascalString, long length, short identity, PathRecord[] records) {
        super(ImageResourceID.PathInformation, pascalString, length);
        this.identity = identity;
        this.records = records;
    }

    /**
     * @return The {@link ImageResourceID} here varies from 2000 to 2997.
     * By default, the ID returned is 2000. THIS method returns the actual
     * id used to write the resource instead.
     */
    public short getIdentityExact() {
        return identity;
    }

}
