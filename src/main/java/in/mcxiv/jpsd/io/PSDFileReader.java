package in.mcxiv.jpsd.io;

import static in.mcxiv.jpsd.io.PSDFileReader.UnknownBytesStrategy.Action.Skip;

public class PSDFileReader {

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

    private static boolean R_V_DEBUGGING = false;

    /**
     * @return A boolean, true only if debugging mode is enabled.
     */
    public static boolean debugging() {
        return R_V_DEBUGGING;
    }

    public static void setDebuggingMode(boolean rVDebugging) {
        R_V_DEBUGGING = rVDebugging;
    }

}
