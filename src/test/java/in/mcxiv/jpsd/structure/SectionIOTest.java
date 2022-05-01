package in.mcxiv.jpsd.structure;

import in.mcxiv.jpsd.PSDDocument;
import in.mcxiv.jpsd.data.common.ImageMeta;
import in.mcxiv.jpsd.data.file.ColorMode;
import in.mcxiv.jpsd.data.layer.LayerInfo;
import in.mcxiv.jpsd.data.layer.info.LayerRecord;
import in.mcxiv.jpsd.data.layer.info.record.ChannelInfo;
import in.mcxiv.jpsd.io.ImageMakerStudio;
import in.mcxiv.jpsd.io.PSDConnection;
import in.mcxiv.jpsd.io.RawDataDecoder;
import open.OpenSimplexNoise;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SectionIOTest {

    public static String[] resources = {
            /*0*/ "/2x2_Red.psd",
            /*1*/ "/2x2_Green.psd",
            /*2*/ "/2x2_Yellow_(Reg+Green).psd",
            /*3*/ "/2x2_Yellow_over_2x4_Red_centered_(2_layers).psd",
            /*4*/ "/custom rgb depth8.psd",
            /*5*/ "/custom rgb depth16.psd",
            /*6*/ "/custom rgb depth32.psd",
            /*7*/ "/IUGFCJCFUJG.psd",
            /*8*/ "/ImageWithMask.psd",
            /*9*/ "/test_files/custom.psd",
            /*10*/ "/test_files/10x11.psb",
    };

    public static FileImageInputStream get(String res) throws IOException {
        return new FileImageInputStream(new File(System.getProperty("user.dir") + "/src/test/test_data" + res));
    }

    public static FileImageOutputStream put(String res) throws IOException {
        return new FileImageOutputStream(new File(System.getProperty("user.dir") + "/src/test/test_data/out" + res));
    }

    public static FileImageInputStream get(Path res) throws IOException {
        return new FileImageInputStream(res.toFile());
    }

    public static String file(String resnm) {
        String s = System.getProperty("user.dir") + "/src/test/test_data" + resnm;
        System.err.println(s);
        return s;
    }

    @BeforeAll
    static void beforeAll() {
        PSDConnection.setDebuggingMode(true);
    }

    @Test
    void testThatWeCanRetrieveResUsingThisMethod() throws IOException {
        get(resources[3]);
    }

    @Test
    void reading() throws IOException {
        FileImageInputStream in = get(resources[8]);
        PSDConnection con = new PSDConnection(in);
        pj(con.toString());
    }

    @Test
    @Disabled("It's disabled because, duh I have somewhere like 12 GBs of PSD files to be tested!")
    void readAllCompositionsDecoderMethod() throws IOException {
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder();

        Files.walk(new File(file("")).toPath(), 1)
                .filter(path -> path.toString().endsWith(".psd") || path.toString().endsWith(".psb"))
                .map(path -> {
                    builder.setLength(0);
                    System.out.println("Current file: " + builder.append(path));
                    return get(() -> get(path));
                })
                .filter(Objects::nonNull)
                .map(iis -> get(() -> new PSDConnection(iis)))
                .filter(Objects::nonNull)
                .map(psdFileReader -> get(() -> ImageMakerStudio.toImage(psdFileReader)))
//                .forEach(ints -> System.out.println(Arrays.toString(ints)));
                .filter(Objects::nonNull)
                .forEach(image -> run(() -> ImageIO.write(image, "PNG", new File(file("/out/" + getName(builder) + "_" + count.getAndIncrement() + ".png")))));
    }

    @Test
    @Disabled("It's disabled because, duh I have somewhere like 12 GBs of PSD files to be tested!")
    void readAllLayersDecoderMethod() throws IOException {
        AtomicInteger count = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder();

        Files.walk(new File(file("/random_test_files")).toPath(), 2)
                .filter(path -> path.toString().endsWith(".psd") || path.toString().endsWith(".psb"))
                .map(path -> {
                    builder.setLength(0);
                    System.out.println("Current file: " + builder.append(path));
                    return get(() -> get(path));
                })
                .filter(Objects::nonNull)
                .map(iis -> get(() -> new PSDConnection(iis)))
                .filter(Objects::nonNull)
                .filter(psdConnection -> psdConnection.getFileHeaderData().getColorMode()==ColorMode.RGB)
                .map(psdFileReader -> new SimpleEntry<>(psdFileReader, psdFileReader.getLayerAndMaskData()))
//                .forEach(System.out::println);
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().justGetALayerInfo()))
                .filter(pair -> pair.getValue() != null)
                .map(pair -> new SimpleEntry<>(pair.getKey(), pair.getValue().getLayerRecords()))
                .flatMap(pair -> Stream.of(pair.getValue()).map(record -> new SimpleEntry<>(pair.getKey(), record)))
                .map(pair -> ImageMakerStudio.toImage(pair.getValue(), pair.getKey()))
                .filter(Objects::nonNull)
                .forEach(image -> run(() -> ImageIO.write(image, "PNG", new File(file("/out/" + getName(builder) + "_" + count.getAndIncrement() + ".png")))));
    }

    @Test
    @Disabled
    void selectiveReadForDebuggingAboveMethod() throws IOException {
        PSDConnection.setDebuggingMode(true);
        PSDConnection.unknownBytesStrategy = new PSDConnection.UnknownBytesStrategy(PSDConnection.UnknownBytesStrategy.Action.ExcludeData);

        pj("FileHeaderData{version=PSD, channels=4, height=984, width=1378, depth=8, colorMode=RGB}");
        pj("ColorModeData{length=0, data=[], hasData=false}");
        pj("ImageResourcesData{blocks=[ImageResourceBlock{identity=CaptionDigest, pascalString='', length when read (if read)=16}, ImageResourceBlock{identity=XMPMetadata, pascalString='', length when read (if read)=4649}, ResolutionRBlock{HDpi=100.0, HResDisplayUnit=PxPerInch, WidthDisplayUnit=Centimeters, VDpi=100.0, VResDisplayUnit=PxPerInch, HeightDisplayUnit=Centimeters}, ImageResourceBlock{identity=PrintScale, pascalString='', length when read (if read)=14}, AlphaChannelsNamesRBlock{channelNames=[Transparency]}, UnicodeAlphaNamesRBlock{channelNames=[Transparency]}, ImageResourceBlock{identity=Display, pascalString='', length when read (if read)=14}, ImageResourceBlock{identity=AlphaIdentifiers, pascalString='', length when read (if read)=4}, ImageResourceBlock{identity=GlobalAngle, pascalString='', length when read (if read)=4}, ImageResourceBlock{identity=GlobalAltitude, pascalString='', length when read (if read)=4}, ImageResourceBlock{identity=PrintFlags, pascalString='', length when read (if read)=9}, ImageResourceBlock{identity=CopyrightFlag, pascalString='', length when read (if read)=1}, ImageResourceBlock{identity=PrintFlagsInfo, pascalString='', length when read (if read)=10}, ImageResourceBlock{identity=ColorHalftoning, pascalString='', length when read (if read)=72}, ImageResourceBlock{identity=ColorTransferFunction, pascalString='', length when read (if read)=112}, ImageResourceBlock{identity=LayerState, pascalString='', length when read (if read)=2}, ImageResourceBlock{identity=LayerGroup, pascalString='', length when read (if read)=14}, GridAndGuidesRBlock{version=1, horizontal=576, vertical=576, fGuideCount=0, blocks=[]}, ImageResourceBlock{identity=URLList, pascalString='', length when read (if read)=4}, ImageResourceBlock{identity=Slices, pascalString='', length when read (if read)=843}, ImageResourceBlock{identity=ICCUntaggedProfile, pascalString='', length when read (if read)=1}, ImageResourceBlock{identity=DocumentIDSeed, pascalString='', length when read (if read)=4}, ThumbnailRBlock{isOldFormat=false, format=kJpegRGB, width=128, height=91, widthBytes=384, totalSize=34944, compressedSize=5018, bitsPerPixel=24, numberOfPlanes=1, imageData.length=5018}, VersionInfoRBlock{version=1, hasMergedData=true, readerName='Adobe Photoshop', writerName='Adobe Photoshop 7.0', fileVersion=1}, ImageResourceBlock{identity=EXIFData1, pascalString='', length when read (if read)=294}]}");
        pj("LayerAndMaskData{layerInfo=LayerInfo{hasAlpha=true, layerRecords=[LayerRecord{content=Rectangle{top=0, lef=0, bot=984, rig=1378}, info=[ChannelInfo{id=ChannelID:Channel1, dataLength=312479, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=312477}}, ChannelInfo{id=ChannelID:Channel2, dataLength=326231, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=326229}}, ChannelInfo{id=ChannelID:Channel3, dataLength=331674, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=331672}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=true, VISIBLE=true, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='Background', additionalLayerInfos=[UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='Background'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=1}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=59, lef=726, bot=882, rig=1306}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=11524, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=11522}}, ChannelInfo{id=ChannelID:Channel1, dataLength=318870, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=318868}}, ChannelInfo{id=ChannelID:Channel2, dataLength=359810, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=359808}}, ChannelInfo{id=ChannelID:Channel3, dataLength=306105, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=306103}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=true, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='Layer 3', additionalLayerInfos=[UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='Layer 3'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=6}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=60, lef=723, bot=880, rig=1307}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=13122, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=13120}}, ChannelInfo{id=ChannelID:Channel1, dataLength=318563, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=318561}}, ChannelInfo{id=ChannelID:Channel2, dataLength=358390, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=358388}}, ChannelInfo{id=ChannelID:Channel3, dataLength=306520, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=306518}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=true, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='Layer 4', additionalLayerInfos=[UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='Layer 4'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=7}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=931, lef=163, bot=963, rig=1206}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=10134, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=10132}}, ChannelInfo{id=ChannelID:Channel1, dataLength=642, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=640}}, ChannelInfo{id=ChannelID:Channel2, dataLength=642, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=640}}, ChannelInfo{id=ChannelID:Channel3, dataLength=642, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=640}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=true, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='FRONT SIDE (LAYOUT 1)         ', additionalLayerInfos=[AdditionalLayerInfo{key=Key:TYPE_TOOL_OBJECT_KEY:TySh, length=20060}, UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='FRONT SIDE (LAYOUT 1)         '}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=3}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=57, lef=719, bot=883, rig=1301}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=13218, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=13216}}, ChannelInfo{id=ChannelID:Channel1, dataLength=261752, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=261750}}, ChannelInfo{id=ChannelID:Channel2, dataLength=357092, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=357090}}, ChannelInfo{id=ChannelID:Channel3, dataLength=245057, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=245055}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=false, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='Layer 2', additionalLayerInfos=[UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='Layer 2'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=5}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=22, lef=-24, bot=1002, rig=666}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=17298, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=17296}}, ChannelInfo{id=ChannelID:Channel1, dataLength=273929, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=273927}}, ChannelInfo{id=ChannelID:Channel2, dataLength=281757, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=281755}}, ChannelInfo{id=ChannelID:Channel3, dataLength=284685, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=284683}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=false, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='Layer 1', additionalLayerInfos=[UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='Layer 1'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=2}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}, LayerRecord{content=Rectangle{top=931, lef=182, bot=963, rig=1187}, info=[ChannelInfo{id=ChannelID:TransparencyMask, dataLength=9688, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=9686}}, ChannelInfo{id=ChannelID:Channel1, dataLength=578, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=576}}, ChannelInfo{id=ChannelID:Channel2, dataLength=578, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=576}}, ChannelInfo{id=ChannelID:Channel3, dataLength=578, data=ChannelImageData{compression=Compression:RLE_Compression, data.length=576}}], blendingMode=NORMAL, opacity=-1, clipping=Base, layerRecordInfoFlag=LayerRecordInfoFlag{TRANSPARENCY_PROTECTED=false, VISIBLE=false, OBSOLETE=false, HAS_FOURTH=true, PIXEL_DATA=false}, filler=0, layerMaskData=null, layerBlendingRanges=LayerBlendingRanges{compositeGray=Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, otherChannels=[Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}, Blend{source=Range{black=0, white=-1}, destination=Range{black=0, white=-1}}]}, layerName='BACK SIDE (LAYOUT 1)           ', additionalLayerInfos=[AdditionalLayerInfo{key=Key:TYPE_TOOL_OBJECT_KEY:TySh, length=20048}, UnicodeLayerName{key=Key:UNICODE_LAYER_NAME_KEY:luni, name='BACK SIDE (LAYOUT 1)           copy'}, AdditionalLayerInfo{key=Key:LAYER_NAME_SOURCE_SETTING:lnsr, length=4}, LayerID{key=Key:LAYER_ID_KEY:lyid, id=4}, AdditionalLayerInfo{key=Key:BLEND_CLIPPING_ELEMENTS:clbl, length=4}, AdditionalLayerInfo{key=Key:BLEND_INTERIOR_ELEMENTS:infx, length=4}, AdditionalLayerInfo{key=Key:KNOCKOUT_SETTING:knko, length=4}, AdditionalLayerInfo{key=Key:PROTECTED_SETTING:lspf, length=4}, AdditionalLayerInfo{key=Key:SHEET_COLOR_SETTING:lclr, length=8}, AdditionalLayerInfo{key=Key:REFERENCE_POINT:fxrp, length=16}]}]}, globalLayerMaskInfo=null, additionalLayerInfo=[]}");

        PSDDocument psdDocument = new PSDDocument(new File("src\\test\\test_data\\random_test_files\\Book Cover1.psd"));
        pj(psdDocument.toString());

        psdDocument.getLayers().forEach(layerRecord -> run(() -> {
            BufferedImage image = ImageMakerStudio.toImage(layerRecord, psdDocument.getConnection());
            if (image != null)
                ImageIO.write(
                        image,
                        "PNG",
                        new File(file("/out/" + layerRecord.getLayerName() + ".png"))
                );
        }));
//        psdDocument.getLayers().forEach(layerRecord -> pj(layerRecord.toString()));
        ImageIO.write(
                psdDocument.getCompositeImage(),
                "PNG",
                new File(file("/out/selectiveReadForDebuggingAboveMethod.png"))
        );
    }

    @Test
    void readingMaskData() throws IOException {

        FileImageInputStream in = get(resources[8]);
        PSDConnection con = new PSDConnection(in);

        LayerRecord layerRecord = con.getLayerAndMaskData().getLayerInfo().getLayerRecords()[1];

        int w = layerRecord.getWidth();
        int h = layerRecord.getHeight();
        ImageMeta meta = new ImageMeta(w, h, con.getFileHeaderData().isLarge(), ColorMode.RGB, 1, con.getFileHeaderData().getDepthEntry());
        for (ChannelInfo channelInfo : layerRecord.getChannelInfo())
            System.out.printf("%30s : %d\n", channelInfo.getId().toString(), RawDataDecoder.decode(channelInfo.getData().getCompression(), channelInfo.getData().getData(), meta).length);

        ImageIO.write(ImageMakerStudio.toImageMask(layerRecord, con), "PNG", new File(file("/out/ImageWithMask_TheMask.png")));

    }

    private String getName(StringBuilder builder) {
        return builder.substring(builder.lastIndexOf("\\") + 1, builder.lastIndexOf("."));
    }

    @Test
    void writingASimpleImage() throws IOException {
        BufferedImage image = new BufferedImage(10, 20, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getHeight(); i++)
            for (int j = 0; j < image.getWidth(); j++)
                image.setRGB(j, i, new Color(i * 1f / image.getHeight(), (image.getHeight() - i) * 1f / image.getHeight(), j * 1f / image.getWidth(), (image.getWidth() - j) * 1f / image.getWidth()).getRGB());
        ImageIO.write(image, "PNG", new File(file("/out/writingASimpleImage.png")));
        PSDConnection reader = ImageMakerStudio.fromImage(image);
//        reader.getColorModeData().setData(new byte[]{104, 100, 114, 116, 0, 0, 0, 3, 62, 107, -123, 31, 0, 0, 0, 2, 0, 0, 0, 8, 0, 68, 0, 101, 0, 102, 0, 97, 0, 117, 0, 108, 0, 116, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, -1, 0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 65, -128, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 63, -128, 0, 0, 104, 100, 114, 97, 0, 0, 0, 6, 0, 0, 0, 0, 65, -96, 0, 0, 65, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, -128, 0, 0, 0, 0, 0, 0, 0, 0});
        pj(reader.toString());
        reader.write(put("/writingASimpleImage.psd"));
        new PSDConnection(put("/writingASimpleImage.psd"));
    }

    @Test
    void writingABilayeredImage() throws IOException {
        BufferedImage layer1 = new BufferedImage(10, 20, BufferedImage.TYPE_USHORT_555_RGB);
        for (int i = 0; i < layer1.getHeight(); i++)
            for (int j = 0; j < layer1.getWidth(); j++)
                layer1.setRGB(j, i, ((i + j) / 2) % 2 == 0 ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
        BufferedImage layer2 = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < layer2.getHeight(); i++)
            for (int j = 0; j < layer2.getWidth(); j++)
                layer2.setRGB(j, i, new Color(i * 1f / layer2.getHeight(), (layer2.getHeight() - i) * 1f / layer2.getHeight(), j * 1f / layer2.getWidth(), (layer2.getWidth() - j) * 1f / layer2.getWidth()).getRGB());

        LayerRecord layerRecord1 = new LayerRecord(0, 0, "BGHI", layer1);
        LayerRecord layerRecord2 = new LayerRecord(0, 5, "REDT", layer2);

        ImageIO.write(layer1, "PNG", new File(file("/out/writingABilayeredImage_l1.png")));
        ImageIO.write(layer2, "PNG", new File(file("/out/writingABilayeredImage_2.png")));

        PSDConnection reader = ImageMakerStudio.fromImage(layer1);
        reader.getLayerAndMaskData().setLayerInfo(new LayerInfo(true, new LayerRecord[]{layerRecord1, layerRecord2}));
        reader.write(put("/writingABilayeredImage.psd"));
        new PSDConnection(put("/writingABilayeredImage.psd"));
    }


    public static void pj(String str) {
        char[] chars = str.toCharArray();
        StringBuilder b = new StringBuilder();
        int depth = 0;

        for (char c : chars) {
            if (c == '{' || c == '[') {
                b.append(" {\n");
                depth++;
                for (int i = 0; i < depth; i++, b.append("  ")) ;
            } else if (c == '}' || c == ']') {
                b.append("\n");
                depth--;
                for (int i = 0; i < depth; i++, b.append("  ")) ;
                b.append("}");
            } else if (c == ',') {
                b.append("\n");
                for (int i = 0; i < depth - 1; i++, b.append("  ")) ;
                b.append(" ");
            } else {
                b.append(c);
            }
        }

        System.out.println(b);
    }

    public static <T> T get(DangerousSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public static void run(DangerousRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public interface DangerousSupplier<T> {
        T get() throws Throwable;
    }

    public interface DangerousRunnable {
        void run() throws Throwable;
    }

    private static final double scl = 20;
    private static final int delta = 5;

    static OpenSimplexNoise noise = new OpenSimplexNoise(new Random().nextLong());

    private static int eval255(int x, int y, int z) {
        return Math.max(0, Math.min(255, (int) (noise.eval(x / scl, y / scl, z / scl) * 128 + 128)));
    }

    public static BufferedImage getRandom(int w, int h, boolean alpha) {
        noise = new OpenSimplexNoise(new Random().nextLong());
        BufferedImage image = new BufferedImage(w, h, alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int r = eval255(x, y, 0);
                int g = eval255(x, y, delta);
                int b = eval255(x, y, 2 * delta);
                int a = alpha ? eval255(x, y, 3 * delta) : 255;
                int i = y * w + x;
                data[i] = (a << 24) | (r << 16) | (g << 8) | b;
//                image.setRGB(x, y, new Color(0, g, b).getRGB());
            }
        }
        return image;
    }

}