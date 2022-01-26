package in.mcxiv.jpsd.io;

import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.structure.SectionIO;
import in.mcxiv.jpsd.structure.sections.ImageDataIO;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static in.mcxiv.jpsd.io.PSDFileReader.UnknownBytesStrategy.Action.Skip;

public class PSDFileReader {

    private static final PrintStream nullStream = new PrintStream(new OutputStream() {
        private volatile boolean closed;

        private void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("Stream closed");
            }
        }

        @Override
        public void write(int b) throws IOException {
            ensureOpen();
        }

        @Override
        public void write(byte b[], int off, int len) throws IOException {
            ensureOpen();
        }

        @Override
        public void close() {
            closed = true;
        }
    });

    public static PrintStream out = nullStream;

    /**
     * Signifies the start of a Photoshop file.
     */
    public static final byte[] FILE_SIGNATURE_8BPS = {'8', 'B', 'P', 'S'};

    /**
     * Signifies the start of an Image Resource Block.
     * Starting identifier for several photoshop byte blocks.
     */
    public static final byte[] RESOURCE = {'8', 'B', 'I', 'M'};

    /**
     * Signifies the start of an Image Resource Block.
     * Starting identifier for some ImageReady specific duty.
     * <p>
     * Please refer the following for further research.
     * <ul>
     *      <li>http://fileformats.archiveteam.org/wiki/Photoshop_Image_Resources</li>
     *      <li>https://github.com/haraldk/TwelveMonkeys/issues/403#issuecomment-355544600</li>
     * </ul>
     */
    public static final byte[] RESOURCE_IMAGE_READY = {'M', 'e', 'S', 'a'};

    /**
     * Signifies the start of an Image Resource Block.
     * Starting identifier for some PhotoDeluxe specific duty.
     * <p>
     * Please refer the following for further research.
     * <ul>
     *      <li>http://fileformats.archiveteam.org/wiki/PhotoDeluxe</li>
     *      <li>http://fileformats.archiveteam.org/wiki/Photoshop_Image_Resources</li>
     * </ul>
     */
    public static final byte[] RESOURCE_PHOTO_DELUXE = {'P', 'H', 'U', 'T'};

    /**
     * Signifies the start of an Image Resource Block.
     * Starting identifier for some LightRoom specific duty.
     * <p>
     * Please refer the following for further research.
     * <ul>
     *      <li>http://fileformats.archiveteam.org/wiki/Photoshop_Image_Resources</li>
     * </ul>
     */
    public static final byte[] RESOURCE_LIGHT_ROOM = {'A', 'g', 'H', 'g'};

    /**
     * Signifies the start of an Image Resource Block.
     * Hmm!?
     * <p>
     * Please refer the following for further research.
     * <ul>
     *      <li>http://fileformats.archiveteam.org/wiki/Photoshop_Image_Resources</li>
     * </ul>
     */
    public static final byte[] RESOURCE_DCSR = {'D', 'C', 'S', 'R'};


    public static final byte[] ADDITIONAL_LAYER_INFO_SIGNATURE_SMALL = RESOURCE;

    public static final byte[] ADDITIONAL_LAYER_INFO_SIGNATURE_LONG = {'8', 'B', '6', '4'};

    @Deprecated // "Not a good thing to keep hacky methods."
    public static final byte[] CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_2_BYTES_CHOOT = {0, 0, '8', 'B'};

    @Deprecated // "Not a good thing to keep hacky methods."
    public static final byte[] CORRUPTED_ADDITIONAL_LAYER_INFO_SIGNATURE_1_BYTE_CHOOT = {0, '8', 'B', 'I'};

    public static UnknownBytesStrategy unknownBytesStrategy = new UnknownBytesStrategy(Skip);

    public static class UnknownBytesStrategy {

        public enum Action {
            ReadAll, ExcludeData, Skip, Quit;
        }

        public Action action;

        public UnknownBytesStrategy(Action action) {
            this.action = action;
        }

    }

    private DataReader reader;
    private boolean readResources;

    private FileHeaderData fileHeaderData;
    private ColorModeData colorModeData;
    private ImageResourcesData imageResourcesData;
    private LayerAndMaskData layerAndMaskData;
    private ImageData imageData;

    public PSDFileReader(FileHeaderData fileHeaderData, ColorModeData colorModeData, ImageResourcesData imageResourcesData, LayerAndMaskData layerAndMaskData, ImageData imageData) {
        this.fileHeaderData = fileHeaderData;
        this.colorModeData = colorModeData;
        this.imageResourcesData = imageResourcesData;
        this.layerAndMaskData = layerAndMaskData;
        this.imageData = imageData;
    }

    public PSDFileReader(ImageInputStream input) throws IOException {
        this(input, true);
    }

    public PSDFileReader(ImageInputStream input, boolean readNow) throws IOException {
        this(input, readNow, true);
    }

    public PSDFileReader(ImageInputStream input, boolean readNow, boolean readResources) throws IOException {
        reader = new DataReader(input);
        this.readResources = readResources;

        if (!readNow) return;

        fileHeaderData = SectionIO.FILE_HEADER_SECTION.read(reader);
        colorModeData = SectionIO.COLOR_MODE_DATA_SECTION.read(reader);

        if (readResources) {
            imageResourcesData = SectionIO.IMAGE_RESOURCES_DATA_SECTION.read(reader);
        } else {
            long sectionLength;
            if (fileHeaderData.getVersion().isLarge()) sectionLength = reader.stream.readLong();
            else sectionLength = reader.stream.readUnsignedInt();
            reader.stream.skipBytes(sectionLength);
            imageResourcesData = new ImageResourcesData(null);
        }

        if (fileHeaderData.getVersion().isLarge())
            layerAndMaskData = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSB.read(reader);
        else layerAndMaskData = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSD.read(reader);

        out.println(reader.stream.getStreamPosition());

        SectionIO<ImageData> IMAGE_DATA_IO = new ImageDataIO(fileHeaderData);
        imageData = IMAGE_DATA_IO.read(reader);

        out.println(reader.stream.getStreamPosition());
        out.println(reader.stream.length());

        reader.close();
    }

    public FileHeaderData getFileHeaderData() {
        if (fileHeaderData == null) {
            try {
                fileHeaderData = SectionIO.FILE_HEADER_SECTION.read(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileHeaderData;
    }

    public ColorModeData getColorModeData() {
        getFileHeaderData();
        if (colorModeData == null) {
            try {
                colorModeData = SectionIO.COLOR_MODE_DATA_SECTION.read(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return colorModeData;
    }

    public ImageResourcesData getImageResourcesData() {
        getColorModeData();
        if (imageResourcesData == null) {
            try {
                if (readResources)
                    imageResourcesData = SectionIO.IMAGE_RESOURCES_DATA_SECTION.read(reader);
                else {
                    long sectionLength;
                    if (fileHeaderData.getVersion().isLarge()) sectionLength = reader.stream.readLong();
                    else sectionLength = reader.stream.readUnsignedInt();
                    reader.stream.skipBytes(sectionLength);
                    imageResourcesData = new ImageResourcesData(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageResourcesData;
    }

    public LayerAndMaskData getLayerAndMaskData() {
        getImageResourcesData();
        if (layerAndMaskData == null) {
            try {
                if (fileHeaderData.getVersion().isLarge())
                    layerAndMaskData = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSB.read(reader);
                else layerAndMaskData = SectionIO.LAYER_AND_MASK_DATA_SECTION_PSD.read(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return layerAndMaskData;
    }

    public ImageData getImageData() {
        getLayerAndMaskData();
        if (imageData == null) {
            try {
                SectionIO<ImageData> IMAGE_DATA_IO = new ImageDataIO(fileHeaderData);
                imageData = IMAGE_DATA_IO.read(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageData;
    }



    public void write(ImageOutputStream output) throws IOException {
        DataWriter writer = new DataWriter(output);

        SectionIO.FILE_HEADER_SECTION.write(writer, fileHeaderData);
        SectionIO.COLOR_MODE_DATA_SECTION.write(writer, colorModeData);
        SectionIO.IMAGE_RESOURCES_DATA_SECTION.write(writer, imageResourcesData);

        if (fileHeaderData.getVersion().isLarge())
            SectionIO.LAYER_AND_MASK_DATA_SECTION_PSB.write(writer, layerAndMaskData);
        else SectionIO.LAYER_AND_MASK_DATA_SECTION_PSD.write(writer, layerAndMaskData);

        SectionIO<ImageData> IMAGE_DATA_IO = new ImageDataIO(fileHeaderData);
        IMAGE_DATA_IO.write(writer, imageData);

        writer.close();
    }

    private static boolean R_V_DEBUGGING = false;

    /**
     * @return A boolean, true only if debugging mode is enabled.
     */
    public static boolean debugging() {
        return R_V_DEBUGGING;
    }

    public static void setDebuggingMode(boolean rVDebugging) {
        R_V_DEBUGGING = rVDebugging;
        if (R_V_DEBUGGING) out = System.out;
        else out = nullStream;
    }

    @Override
    public String toString() {
        return "PSDFileReader{" +
                "fileHeaderData=" + fileHeaderData +
                ", colorModeData=" + colorModeData +
                ", imageResourcesData=" + imageResourcesData +
                ", layerAndMaskData=" + layerAndMaskData +
                ", imageData=" + imageData +
                '}';
    }
}
