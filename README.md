# JPSD

## Reading a PSD file

```js
File psdFile = ...
ImageInputStream iis = new FileImageInputStream(psdFile);
PSDFileReader reader = new PSDFileReader(iis);

// To get the composite image
Image img = ImageMakerStudio.toImage(psdFileReader);
```

## Reading each and every layer

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

#### The first two arguments of newDefaultRecord are the offset of the layer from top left corner in pixels.
