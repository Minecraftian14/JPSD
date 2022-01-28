# JPSD

[![](https://jitpack.io/v/Minecraftian14/JPSD.svg)](https://jitpack.io/#Minecraftian14/JPSD)


## Some quick example of using builtin methods  

### Reading the Composite Image

```js
    // source can be a File or InputStream
    PSDDocument document = new PSDDocument(source);
    BufferedImage image = document.getCompositeImage();
```

### Reading the Image Layers

```js
    PSDDocument document = new PSDDocument(source);
    
    LayerInfo layersInfo = document.getLayersInfo();
    
    ArrayList<LayerRecord> layers = layersInfo.getLayers();
    
    // Total number of layers
    int nol = layersInfo.getNumberOfLayers();
    
    // Get first layer
    LayerRecord layerRecord = layers.get(0);
    
    // Get the image
    BufferedImage image = layerRecord.getImage(document);
    
    // Change the blending mode
    layerRecord.setBlendingMode(BlendingMode.lite);
    
    // Set opacity
    layerRecord.setOpacity(100);
    
    // Set a mask
    BufferedImage mask = ...;
    layerRecord.setMask(mask);
```

## Writing a PSD file

```js
    BufferedImage image = ...;

    PSDDocument document = new PSDDocument(image);

    // destination can be a File or OutputStream
    document.writeTo(destination);    
```

## Writing a PSD file with multiple layers

```js
    // composite, layer1, layer2, layer3 are BufferedImage 

    // height and width of composite or layer1 should be same
    PSDDocument document = new PSDDocument(height, width);
    document.setCompositeImage(composite);
    
    LayerInfo layersInfo = document.getLayersInfo();

    // First two arguments are the offset from top left in pixels
    LayerRecord record1 = new LayerRecord(0, 0, "BackGround", layer1);
    LayerRecord record2 = new LayerRecord(50, 0, "Layer One", layer2);
    LayerRecord record3 = new LayerRecord(100, 0, "Layer Two", layer3);

    // May change parameters as required
    record2.setBlendingMode(BlendingMode.dark);
    record3.setBlendingMode(BlendingMode.lite);
    record3.setOpacity((byte) 127);

    // Add the records to layer info
    layersInfo.addLayer(record1);
    layersInfo.addLayer(record2);
    layersInfo.addLayer(record3);

    document.writeTo(destination);
```