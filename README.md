# JPSD

[![](https://jitpack.io/v/in.mcxiv/JPSD.svg)](https://jitpack.io/#in.mcxiv/JPSD)
[![](https://img.shields.io/discord/872811194170347520?color=%237289da&logoColor=%23424549)](https://discord.gg/Ar6Zuj2m82)

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
    
    ArrayList<LayerRecord> layers = document.getLayers();
    
    // Total number of layers
    int nol = layers.size();
    
    // Get first layer
    LayerRecord layerRecord = layers.get(0);
    
    // Get the image
    BufferedImage image = layerRecord.getImage(document);
    
    // Change the blending mode
    layerRecord.setBlendingMode(BlendingMode.LIGHTEN);
    
    // Set opacity
    layerRecord.setOpacity(1.0);
    
    // Set a mask
    BufferedImage mask = ...;
    layerRecord.setMask(mask);
```

### Altering other Layer Properties

```js
    // To check if a layer has a mask
    if(layerRecord.hasMask()) {
        ...
    }

    // To change a layer's visibility
    layerRecord.setLayerVisible(#boolean)

    // To invert the effect of mask
    layerRecord.setInvertMask(#boolean)

    // To disable a mask
    layerRecord.setMaskDisabled(#boolean)

    // To change mask location behaviour
    layerRecord.setMaskPositionRelativeToLayer(#boolean)
    
    // To change the bounds of mask
    layerRecord.getLayerMaskData().setMaskEncloser(#Rectangle)
```

### Writing a PSD file

```js
    BufferedImage image = ...;

    PSDDocument document = new PSDDocument(image);

    // destination can be a File or OutputStream
    document.writeTo(destination);    
```

### Writing a PSD file with multiple layers

```js
    // composite, layer1, layer2, layer3 are BufferedImage 

    // height and width of composite and layer1 should be same
    PSDDocument document = new PSDDocument(height, width);
    document.setCompositeImage(composite);
    
    List<LayerRecord> layers = document.getLayers();

    // First two arguments are the offset from top left in pixels
    LayerRecord record1 = new LayerRecord(0, 0, "BackGround", layer1);
    LayerRecord record2 = new LayerRecord(50, 0, "Layer One", layer2);
    LayerRecord record3 = new LayerRecord(100, 0, "Layer Two", layer3);

    // May change parameters as required
    record2.setBlendingMode(BlendingMode.DARKER_COLOR);
    record3.setBlendingMode(BlendingMode.LIGHTER_COLOR);
    record3.setOpacity(0.5f);

    // Add the records to layer info
    layers.add(record1);
    layers.add(record2);
    layers.add(record3);

    document.writeTo(destination);
```

[![](https://jitpack.io/v/Minecraftian14/JPSD.svg)](https://jitpack.io/#Minecraftian14/JPSD)
