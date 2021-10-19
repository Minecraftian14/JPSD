package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class VersionInfoRBlock extends ImageResourceBlock {

    private int version;
    private boolean hasMergedData;
    private String readerName;
    private String writerName;
    private int fileVersion;

    public VersionInfoRBlock(ImageResourceID identity, String pascalString, long length, int version, boolean hasMergedData, String readerName, String writerName, int fileVersion) {
        super(identity, pascalString, length);
        this.version = version;
        this.hasMergedData = hasMergedData;
        this.readerName = readerName;
        this.writerName = writerName;
        this.fileVersion = fileVersion;
    }

    public int getVersion() {
        return version;
    }

    public boolean hasMergedData() {
        return hasMergedData;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getWriterName() {
        return writerName;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    @Override
    public String toString() {
        return "VersionInfoRBlock{" +
                "version=" + version +
                ", hasMergedData=" + hasMergedData +
                ", readerName='" + readerName + '\'' +
                ", writerName='" + writerName + '\'' +
                ", fileVersion=" + fileVersion +
                '}';
    }
}
