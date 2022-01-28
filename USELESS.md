# JPSD

[![](https://jitpack.io/v/Minecraftian14/JPSD.svg)](https://jitpack.io/#Minecraftian14/JPSD)

A PSD file is organised into 5 major sections:
* FileHeaderData
* ColorModeData
* ImageResourcesData
* LayerAndMaskData
* ImageData

To write a new PSD from scratch, one need an instance of each object
mentioned in the list above.

##### FileHeaderData
This block contains the most basic information about the file and how
data is stored in the file.

Requires the following data to construct an instance
* version - specifies the extension of the file and certain other parameters used internally.
    * either one of FileVersion.PSD or FileVersion.PSB
* channels - number of color channels present in the image.
    * any non-negative integer less than 56
    * best value perhaps is 3 only (for RGB)
* height - height of the image/base layer
    * if PSD - maximum height possible is 30_000
    * if PSB - maximum height possible is 300_000
* width - width of the image/base layer
    * if PSD - maximum height possible is 30_000
    * if PSB - maximum height possible is 300_000
* depth - number of bits given to each color component of a pixel
    * only values accepted are 1, 8, 16 and 32 as defined in DepthEntry
* colorMode - specifies the different color mox`des as defined in ColorMode enum

##### ColorModeData
* I don't know what is this, also the reason that it doesnt works here xD.
* Just use the default constructor for basic use.
* Documentation says:
    * If the color mode is ColorMode.Indexed it contains a byte array of length 768
      representing the color table in non-interleaved order.
    * If the color mode is ColorMode.Duotone it contains "the" duotone specification
      which is "not" documented xD
* However, I have a 32-bit psd which has 112 bytes in this section, without
  which the psd is not even valid!

#####ImageResourcesData
* This... is a big section!
* This block contains an array of other blocks which present additional information.
* A photoshopped saved image contains a lot of such resource blocks.
* However, I recommend creating only these blocks:
    * (ignore the length field in constructor (store in anything like -1))
    * ResolutionRBlock
    * GridAndGuidesRBlock
    * VersionInfoRBlock
  ```js
  new ResolutionRBlock(ImageResourceID.Resolution, "", -1, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches, 299.99942f, ResolutionRBlock.ResUnit.PxPerInch, ResolutionRBlock.Unit.Inches);
  new GridAndGuidesRBlock(ImageResourceID.GridAndGuides, "", -1, 1, 576, 576, null);
  new VersionInfoRBlock(ImageResourceID.VersionInfo, "", -1, 1, true, "JPSD", "JPSD", 1);
  ```
* There are a lot other resource blocks which can be added as required. Look
  for subclasses of ImageResourceBlock.
* ImageResourceID contains some information about every block possible (though not supported).

##### LayerAndMaskData
* This block contains information about the layers present in the PSD.
* And this block is a big mess :)
* If the image only has one layer (the base layer/background) create an empty LayerAndMaskData
    * `new LayerAndMaskData(null, null, new AdditionalLayerInfo[0]);`
* If the image has multiple layers, each layer must have its own LayerRecord
    * A layer record has a few default construction helpers
        * `LayerRecord.newDefaultRecord(topLefX, topLefY, layerName, image)`
            * `topLefX` = Distance of the image from left
            * `topLefY` = Distance of the image from top
            * `layerName` = Name of the layer
            * `image` = The BufferedImage
        * `LayerRecord.newDefaultRecord(topLefX, topLefY, botRhtX, botRhtY, layerName, image)`
            * `botRhtX` = Distance of the right most point of image from left
            * `botRightY` = Distance of the bottom most point of image from top
    * If one uses the enormous constructor instead, please read it's javadoc, hopefully i covered everything there.
* With the layer records in hand we proceed to create a LayerInfo object.
* If one wishes to edit the global mask settings, continue to create a GlobalLayerMaskInfo; else just leave it null.
* Finally, prepare a list of additional information blocks if required and create the LayerAndMaskData.
    * For a list of possible additional information blocks, refer subclasses of AdditionalLayerInfo.

##### ImageData
* This block contains image data of how the final composite image looks like.
* Data is stored in banded form.

## Some quick example of using builtin methods

### Reading a PSD file

```js
File psdFile = ...
ImageInputStream iis = new FileImageInputStream(psdFile);
PSDFileReader reader = new PSDFileReader(iis);

// To get the composite image
Image img = ImageMakerStudio.toImage(psdFileReader);
```

### Reading each and every layer

```js
LayerRecord[] records = reader.getLayerAndMaskData().getLayerInfo().getLayerRecords();
for (LayerRecord record : records) {
    BufferedImage img = ImageMakerStudio.toImage(record, reader);
    // actions with image
}
```

## Writing a PSD file

```js
BufferedImage image = ...
ImageOutputStream ios = ... 
ImageMakerStudio
    .fromImage(image)
    .write(ios);
```

## Writing a PSD file with multiple layers

```js
BufferedImage layer1 = ...
BufferedImage layer2 = ...

LayerRecord layerRecord1 = LayerRecord.newDefaultRecord(0, 0, "layer_1_name", layer1);
LayerRecord layerRecord2 = LayerRecord.newDefaultRecord(0, 5, "layer_2_name", layer2);

PSDFileReader reader = ImageMakerStudio.fromImage(layer1);
reader.getLayerAndMaskData().setLayerInfo(new LayerInfo(true, new LayerRecord[]{layerRecord1, layerRecord2}));
reader.write(ios);
```