package in.mcxiv.jpsd;

import in.mcxiv.jpsd.data.addend.AdditionalInfoKey;
import in.mcxiv.jpsd.data.addend.AdditionalLayerInfo;
import in.mcxiv.jpsd.data.file.DepthEntry;
import in.mcxiv.jpsd.data.file.FileVersion;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;
import in.mcxiv.jpsd.data.resource.types.GridAndGuidesRBlock;
import in.mcxiv.jpsd.data.resource.types.ResolutionRBlock;
import in.mcxiv.jpsd.data.resource.types.VersionInfoRBlock;
import in.mcxiv.jpsd.data.sections.*;
import in.mcxiv.jpsd.io.ImageMakerStudio;
import in.mcxiv.jpsd.io.PSDConnection;

import javax.imageio.stream.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PSDDocument {

    public enum PSDDocumentType {
        /**
         * Object was created to be written.
         */
        UNDEFINED,
        /**
         * The only image data is the base layer, and it's stored in Image
         * Data Section.
         */
        SINGLE_LAYER,
        /**
         * The file has more than one layers, and they were stored in Layer
         * Info in Layer And Mask Data Section.
         */
        MULTI_LAYER,
        /**
         * The file has more than one layers, and they were stored in one
         * of the Additional Layer Info in Layer And Mask Data Section.
         */
        LAYERS_IN_IRB;

    }

    private PSDDocumentType type = PSDDocumentType.UNDEFINED;

    private final PSDConnection connection;

    // To read files

    public PSDDocument(File file) throws IOException {
        this(new PSDConnection(new FileImageInputStream(file)));
    }

    public PSDDocument(InputStream inputStream) throws IOException {
        this(new PSDConnection(new MemoryCacheImageInputStream(inputStream)));
    }

    public PSDDocument(ImageInputStream inputStream) throws IOException {
        this(new PSDConnection(inputStream));
    }


    // To write files

    public PSDDocument(BufferedImage image) {
        this(image.getHeight(), image.getWidth());
        setCompositeImage(image);
    }

    public PSDDocument(int height, int width) {
        this(new PSDConnection(
                new FileHeaderData(height, width),
                new ColorModeData(),
                new ImageResourcesData(),
                new LayerAndMaskData(),
                null
        ));
    }

    public PSDDocument(int height, int width, DepthEntry depth) {
        this(new PSDConnection(
                new FileHeaderData(height, width, depth),
                new ColorModeData(),
                new ImageResourcesData(),
                new LayerAndMaskData(),
                null
        ));
    }


    // Private

    private PSDDocument(PSDConnection connection) {
        this.connection = connection;

        LayerAndMaskData lamd = connection.getLayerAndMaskData();

        // Deciding the type of PSD file
        if (lamd.getLayerInfo() != null)
            type = PSDDocumentType.MULTI_LAYER;
        else for (AdditionalLayerInfo alin : lamd.getAdditionalLayerInfo())
            if (alin.getKey().equals(AdditionalInfoKey.LAYER_AND_MASK_INFO_16)) {
                type = PSDDocumentType.LAYERS_IN_IRB;
                break;
            }
        if (type == PSDDocumentType.UNDEFINED)
            if (connection.getImageData() == null)
                type = PSDDocumentType.SINGLE_LAYER;

        // Default set of initialisations
        ImageResourcesData ird = connection.getImageResourcesData();
        ird.addBlock(new ResolutionRBlock(ImageResourceID.Resolution, "", -1, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches));
        ird.addBlock(new GridAndGuidesRBlock(ImageResourceID.GridAndGuides, "", -1, 1, 576, 576, null));
        ird.addBlock(new VersionInfoRBlock(ImageResourceID.VersionInfo, "", -1, 1, true, "JPSD", "JPSD", 1));

    }


    // Methods

    /**
     * Change the format of file between PSD or PSB.
     * <p>
     * Most commonly used file format is the PSD while PSB is a similar alternative.
     * Main advantage of using PSB over PSD is that it allows images of bigger
     * dimensions. If the image is expected to be larger than 30,000 x 30,000
     * use PSB. It's not possible to write images of that size in PSD.
     *
     * @param version The version of file to use. Refer {@link FileVersion}.
     */
    public void switchFileType(FileVersion version) {
        connection.getFileHeaderData().setVersion(version);
    }

    /**
     * Change the bit-depth or bits-per-channel.
     * <p>
     * Change the number of bits allotted to write a single color component of
     * one pixel.
     *
     * @param depth The depth of image data. Refer {@link DepthEntry}.
     */
    public void switchDepth(DepthEntry depth) {
        connection.getFileHeaderData().setDepth(depth);
    }

    public void addImageResource(ImageResourceBlock block) {
        connection.getImageResourcesData().addBlock(block);
    }

    public void addLayerInfo(AdditionalLayerInfo info) {
        connection.getLayerAndMaskData().addInfo(info);
    }

    /**
     * Retrieve the LayerInfo instance.
     * <p>
     * This object contains a list of all the layers.
     * Refer {@link LayerInfo#getLayers()}.
     *
     * @return the {@link LayerInfo} instance stored in {@link LayerAndMaskData}
     */
    public LayerInfo getLayersInfo() {
        return connection.getLayerAndMaskData().justGetALayerInfo();
    }

    public void setCompositeImage(BufferedImage image) {
        if (image.getHeight() != connection.getFileHeaderData().getHeight() || image.getWidth() != connection.getFileHeaderData().getWidth())
            throw new IllegalArgumentException("Dimensions of composite image and file should be same.");
        connection.setImageData(new ImageData(ImageMakerStudio.convertToRawData(image)));
    }

    public BufferedImage getCompositeImage() {
        return ImageMakerStudio.toImage(connection);
    }

    public PSDConnection getConnection() {
        return connection;
    }

    public void writeTo(File file) throws IOException {
        writeTo(new FileImageOutputStream(file));
    }

    public void writeTo(OutputStream stream) throws IOException {
        writeTo(new MemoryCacheImageOutputStream(stream));
    }

    public void writeTo(ImageOutputStream stream) throws IOException {
        if (getLayersInfo().getNumberOfLayers() != 0) {
            BufferedImage image = getLayersInfo().getLayers().get(0).getImage(connection);
            if (image.getHeight() != connection.getFileHeaderData().getHeight() || image.getWidth() != connection.getFileHeaderData().getWidth())
                throw new IllegalArgumentException("The first layer should have the same size as the file.");
            if (connection.getImageData() == null)
                connection.setImageData(new ImageData(ImageMakerStudio.convertToRawData(image)));
        }
        if (connection.getImageData() == null)
            throw new IllegalArgumentException("Please provide an Image Data or Layer Info");
        connection.write(stream);
    }

}
