package in.mcxiv.jpsd.data.resource;

public enum ImageResourceID {
    /**
     * Just for the sake of existence. To denote an unidentified ID.
     */
    UNDEFINED(0),
    /**
     * Obsolete--Photoshop 2.0 only
     * Contains five 2-byte values: number of channels, rows, columns, depth, and mode
     */
    ImageData(1000),
    /**
     * Macintosh print manager print info record.
     */
    MacPrintRecord(1001),
    /**
     * Macintosh page format information. No longer read by Photoshop.
     */
    MacPageFormat(1002),
    /**
     * Obsolete--Photoshop 2.0 only
     * Indexed color table.
     */
    IndexedColorTable(1003),
    /**
     * ResolutionInfo structure.
     * See Appendix A in "Photoshop API Guide.pdf".
     */
    Resolution(1005),
    /**
     * Names of the alpha channels as a series of Pascal strings.
     */
    AlphaChannelNames(1006),
    /**
     * Obsolete
     * See ID '1077DisplayInfo' structure.
     * See Appendix A in "Photoshop API Guide.pdf".
     */
    Display(1007),
    /**
     * The caption as a Pascal string.
     */
    PascalString(1008),
    /**
     * Border information.
     * Contains a fixed number (2 bytes real, 2 bytes fraction) for the border width,and
     * 2 bytes for border units (1 = inches, 2 = cm, 3 = points, 4 = picas, 5 = columns).
     */
    Border(1009),
    /**
     * Background color.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577411_31265.
     */
    BackgroundColor(1010),
    /**
     * Print flags. A series of one-byte boolean values (see Page Setup dialog): labels, crop marks,
     * color bars, registration marks, negative, flip, interpolate, caption, print flags.
     */
    PrintFlags(1011),
    /**
     * Grayscale and multichannel halftoning information.
     */
    GrayscaleAndMultichannelHalftoning(1012),
    /**
     * Color halftoning information.
     */
    ColorHalftoning(1013),
    /**
     * Duotone halftoning information.
     */
    DuotoneHalftoning(1014),
    /**
     * Grayscale and multichannel transfer function.
     */
    GrayscaleAndMultichannelTransferFunction(1015),
    /**
     * Color transfer functions.
     */
    ColorTransferFunction(1016),
    /**
     * Duotone transfer functions.
     */
    DuotoneTransferFunction(1017),
    /**
     * Duotone image information.
     */
    DuotoneImage(1018),
    /**
     * Two bytes for the effective black and white values for the dot range.
     */
    BlackAndWhiteDotRange(1019),
    /**
     * Obsolete.
     */
    Obsolete1(1020),
    /**
     * EPS options
     */
    EPSOptions(1021),
    /**
     * Quick Mask information.
     * 2 bytes containing Quick Mask channel ID; 1- byte boolean indicating
     * whether the mask was initially empty.
     */
    QuickMask(1022),
    /**
     * Obsolete.
     */
    Obsolete(1023),
    /**
     * Layer state information. 2 bytes containing the index of target layer (0 = bottom layer).
     */
    LayerState(1024),
    /**
     * Working path (not saved).
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_17587.
     */
    WorkingPath(1025),
    /**
     * Layers group information.
     * 2 bytes per layer containing a group ID for the dragging groups. Layers in a group have the same group ID.
     */
    LayerGroup(1026),
    /**
     * Obsolete.
     */
    Obsolete3(1027),
    /**
     * IPTC-NAA record.
     * Contains the File Info... information.
     * See the documentation in the IPTC folder of the Documentation folder.
     */
    IPTC_NAA_Record(1028),
    /**
     * Image mode for raw format files
     */
    RawImageMode(1029),
    /**
     * JPEG quality.
     * Private.
     */
    JPEGQuality(1030),
    /**
     * (Photoshop 4.0)
     * Grid and guides information.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_62190.
     */
    GridAndGuides(1032),
    /**
     * (Photoshop 4.0)
     * Thumbnail resource for Photoshop 4.0 only.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_74450.
     */
    Thumbnail4(1033),
    /**
     * (Photoshop 4.0)
     * Copyright flag.
     * Boolean indicating whether image is copyrighted. Can be set via Property
     * suite or by user in File Info...
     */
    CopyrightFlag(1034),
    /**
     * (Photoshop 4.0)
     * URL.
     * Handle of a text string with uniform resource locator. Can be set via Property
     * suite or by user in File Info...
     */
    URL(1035),
    /**
     * (Photoshop 5.0)
     * Thumbnail resource (supersedes resource 1033).
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_74450.
     */
    Thumbnail5(1036),
    /**
     * (Photoshop 5.0)
     * Global Angle.
     * 4 bytes that contain an integer between 0 and 359, which is the global
     * lighting angle for effects layer. If not present, assumed to be 30.
     */
    GlobalAngle(1037),
    /**
     * Obsolete. See ID 1073 below.
     * (Photoshop 5.0)
     * Color samplers resource.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_13931.
     */
    ColorSamplers(1038),
    /**
     * (Photoshop 5.0)
     * ICC Profile.
     * The raw bytes of an ICC (International Color Consortium) format profile. See ICC1v42_2006-05.pdf
     * in the Documentation folder and icProfileHeader.h in Sample Code\Common\Includes .
     */
    ICCProfile(1039),
    /**
     * (Photoshop 5.0)
     * Watermark. One byte.
     */
    Watermark(1040),
    /**
     * (Photoshop 5.0)
     * ICC Untagged Profile.
     * 1 byte that disables any assumed profile handling when opening the file. 1 = intentionally untagged.
     */
    ICCUntaggedProfile(1041),
    /**
     * (Photoshop 5.0)
     * Effects visible.
     * 1-byte global flag to show/hide all the effects layer. Only present when they are hidden.
     */
    EffectsVisibility(1042),
    /**
     * (Photoshop 5.0)
     * Spot Halftone.
     * 4 bytes for version, 4 bytes for length, and the variable length data.
     */
    SpotHalftone(1043),
    /**
     * (Photoshop 5.0)
     * Document-specific IDs seed number.
     * 4 bytes: Base value, starting at which layer IDs will be generated
     * (or a greater value if existing IDs already exceed it). Its purpose
     * is to avoid the case where we add layers, flatten, save, open, and
     * then add more layers that end up with the same IDs as the first set.
     */
    DocumentIDSeed(1044),
    /**
     * (Photoshop 5.0)
     * Unicode Alpha Names.
     * Unicode string (https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#UnicodeStringDefine)
     */
    UnicodeAlphaNames(1045),
    /**
     * (Photoshop 6.0)
     * Indexed Color Table Count.
     * 2 bytes for the number of colors in table that are actually defined.
     */
    IndexedColorTableCount(1046),
    /**
     * (Photoshop 6.0)
     * Transparency Index.
     * 2 bytes for the index of transparent color, if any.
     */
    TransparencyIndex(1047),
    /**
     * (Photoshop 6.0)
     * Global Altitude.
     * 4 byte entry for altitude
     */
    GlobalAltitude(1049),
    /**
     * (Photoshop 6.0)
     * Slices.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_19931.
     */
    Slices(1050),
    /**
     * (Photoshop 6.0)
     * Workflow URL.
     * Unicode string (https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#UnicodeStringDefine)
     */
    WorkflowURL(1051),
    /**
     * (Photoshop 6.0)
     * Jump To XPEP.
     * 2 bytes major version, 2 bytes minor version, 4 bytes count. Following
     * is repeated for count: 4 bytes block size, 4 bytes key, if key = 'jtDd' ,
     * then next is a Boolean for the dirty flag; otherwise it's a 4 byte entry
     * for the mod date.
     */
    JumpToXPEP(1052),
    /**
     * (Photoshop 6.0)
     * Alpha Identifiers.
     * 4 bytes of length, followed by 4 bytes each for every alpha identifier.
     */
    AlphaIdentifiers(1053),
    /**
     * (Photoshop 6.0)
     * URL List.
     * 4 byte count of URLs, followed by 4 byte long, 4 byte ID, and Unicode string for each count.
     */
    URLList(1054),
    /**
     * (Photoshop 6.0)
     * Version Info.
     * 4 bytes version, 1 byte hasRealMergedData , Unicode string: writer name,
     * Unicode string: reader name, 4 bytes file version.
     */
    VersionInfo(1057),
    /**
     * (Photoshop 7.0)
     * EXIF data 1.
     * See http://www.kodak.com/global/plugins/acrobat/en/service/digCam/exifStandard2.pdf
     */
    EXIFData1(1058),
    /**
     * (Photoshop 7.0)
     * EXIF data 3.
     * See http://www.kodak.com/global/plugins/acrobat/en/service/digCam/exifStandard2.pdf
     */
    EXIFData3(1059),
    /**
     * (Photoshop 7.0)
     * XMP metadata.
     * File info as XML description. See http://www.adobe.com/devnet/xmp/
     */
    XMPMetadata(1060),
    /**
     * (Photoshop 7.0)
     * Caption digest.
     * 16 bytes: RSA Data Security, MD5 message-digest algorithm
     */
    CaptionDigest(1061),
    /**
     * (Photoshop 7.0)
     * Print scale.
     * 2 bytes style (0 = centered, 1 = size to fit, 2 = user defined).
     * 4 bytes x location (floating point). 4 bytes y location (floating point).
     * 4 bytes scale (floating point)
     */
    PrintScale(1062),
    /**
     * (Photoshop CS)
     * Pixel Aspect Ratio.
     * 4 bytes (version = 1 or 2), 8 bytes double, x / y of a pixel. Version 2,
     * attempting to correct values for NTSC and PAL, previously off by a factor of approx. 5%.
     */
    PixelAspectRatio(1064),
    /**
     * (Photoshop CS)
     * Layer Comps.
     * 4 bytes (descriptor version = 16), Descriptor (see See Descriptor structure)
     */
    LayerComps(1065),
    /**
     * (Photoshop CS)
     * Alternate Duotone Colors.
     * 2 bytes (version = 1), 2 bytes count, following is repeated for each
     * count: [ Color: 2 bytes for space followed by 4 * 2 byte color component ]
     * , following this is another 2 byte count, usually 256, followed by Lab
     * colors one byte each for L, a, b. This resource is not read or used by Photoshop.
     */
    AlternateDuotoneColors(1066),
    /**
     * (Photoshop CS)
     * Alternate Spot Colors.
     * 2 bytes (version = 1), 2 bytes channel count, following is repeated
     * for each count: 4 bytes channel ID, Color: 2 bytes for space followed
     * by 4 * 2 byte color component. This resource is not read or used by Photoshop.
     */
    AlternateSpotColors(1067),
    /**
     * (Photoshop CS2)
     * Layer Selection ID(s).
     * 2 bytes count, following is repeated for each count: 4 bytes layer ID
     */
    LayerSelectionIDs(1069),
    /**
     * (Photoshop CS2)
     * HDR Toning information
     */
    HDRToning(1070),
    /**
     * (Photoshop CS2)
     * Print info
     */
    PrintInfo(1071),
    /**
     * (Photoshop CS2)
     * Layer Group(s) Enabled ID.
     * 1 byte for each layer in the document, repeated by length of the resource.
     * NOTE: Layer groups have start and end markers
     */
    LayerGroupsID(1072),
    /**
     * (Photoshop CS3)
     * Color samplers resource.
     * Also see ID 1038 for old format.
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_13931.
     */
    ColorSamplersResource(1073),
    /**
     * (Photoshop CS3)
     * Measurement Scale.
     * 4 bytes (descriptor version = 16), Descriptor (see https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577411_21585)
     */
    MeasurementScale(1074),
    /**
     * (Photoshop CS3)
     * Timeline Information.
     * 4 bytes (descriptor version = 16), Descriptor
     */
    TimelineInformation(1075),
    /**
     * (Photoshop CS3)
     * Sheet Disclosure.
     * 4 bytes (descriptor version = 16), Descriptor
     */
    SheetDisclosure(1076),
    /**
     * (Photoshop CS3)
     * DisplayInfo
     * structure to support floating point clors. Also see ID 1007. See Appendix A in Photoshop API Guide.pdf .
     */
    DisplayInfo(1077),
    /**
     * (Photoshop CS3)
     * Onion Skins.
     * 4 bytes (descriptor version = 16), Descriptor (see See Descriptor structure)
     */
    OnionSkins(1078),
    /**
     * (Photoshop CS4)
     * Count Information.
     * 4 bytes (descriptor version = 16), Descriptor
     * Information about the count in the document. See the Count Tool.
     */
    CountInformation(1080),
    /**
     * (Photoshop CS5)
     * Print Information.
     * 4 bytes (descriptor version = 16), Descriptor
     * Information about the current print settings in the document. The color management options.
     */
    PrintInformation(1082),
    /**
     * (Photoshop CS5)
     * Print Style.
     * 4 bytes (descriptor version = 16), Descriptor
     * Information about the current print style in the document.
     * The printing marks, labels, ornaments, etc.
     */
    PrintStyle(1083),
    /**
     * (Photoshop CS5)
     * Macintosh NSPrintInfo.
     * Variable OS specific info for Macintosh. NSPrintInfo. It is
     * recommened that you do not interpret or use this data.
     */
    MacNSPrintInfo(1084),
    /**
     * (Photoshop CS5)
     * Windows DEVMODE.
     * Variable OS specific info for Windows. DEVMODE. It is
     * recommened that you do not interpret or use this data.
     */
    WindowsDEVMODE(1085),
    /**
     * (Photoshop CS6)
     * Auto Save File Path.
     * Unicode string. It is recommened that you do not interpret or use this data.
     */
    AutoSaveFilePath(1086),
    /**
     * (Photoshop CS6)
     * Auto Save Format.
     * Unicode string. It is recommened that you do not interpret or use this data.
     */
    AutoSaveFormat(1087),
    /**
     * (Photoshop CC)
     * Path Selection State.
     * 4 bytes (descriptor version = 16), Descriptor Information about
     * the current path selection state.
     */
    PathSelectionState(1088),
    /**
     * Path Information (saved paths).
     * See https://www.adobe.com/devnet-apps/photoshop/fileformatashtml/PhotoshopFileFormats.htm#50577409_17587.
     * <p>
     * Note, this ID ranges from 2000 all the way up to 2997.
     */
    PathInformation(2000),
    /**
     * Name of clipping path.
     * See Path resource format.
     */
    ClippingPath(2999),
    /**
     * (Photoshop CC)
     * Origin Path Info.
     * 4 bytes (descriptor version = 16), Descriptor Information about the origin path data.
     */
    OriginPathInfo(3000),
    /**
     * Plug-In resource(s).
     * Resources added by a plug-in.
     * See the plug-in API found in the SDK documentation
     * <p>
     * Note, this ID ranges from 4000 all the way up to 4999.
     */
    PlugInResource(4000),
    /**
     * Image Ready variables.
     * XML representation of variables definition
     */
    ImageReadyVariables(7000),
    /**
     * Image Ready data sets
     */
    ImageReadyDatasets(7001),
    /**
     * Image Ready default selected state
     */
    ImageReadyDefaultSelectedState(7002),
    /**
     * Image Ready 7 rollover expanded state
     */
    ImageReady7RolloverExpandedState(7003),
    /**
     * Image Ready rollover expanded state
     */
    ImageReadyRolloverExpandedState(7004),
    /**
     * Image Ready save layer settings
     */
    ImageReadySaveLayerSettings(7005),
    /**
     * Image Ready Version
     */
    ImageReadyVersion(7006),
    /**
     * (Photoshop CS3)
     * Lightroom workflow,
     * if present the document is in the middle of a Lightroom workflow.
     */
    LightroomWorkflow(8000),
    /**
     * Print flags information. 2 bytes version ( = 1), 1 byte center crop marks, 1 byte ( = 0), 4 bytes bleed width value, 2 bytes bleed width scale.
     */
    PrintFlagsInfo(10000);

    private final short identifier;

    ImageResourceID(int identifier) {
        this.identifier = (short) identifier;
    }

    public short getIdentity() {
        return identifier;
    }

    public static ImageResourceID of(short identity) {
        for (ImageResourceID value : values()) if (value.identifier == identity) return value;
        throw new IllegalArgumentException("No such identity defined as " + identity);
    }
}
