package in.mcxiv.jpsd.data.resource.types;

import in.mcxiv.jpsd.data.primitive.ShortEntry;
import in.mcxiv.jpsd.data.resource.ImageResourceBlock;
import in.mcxiv.jpsd.data.resource.ImageResourceID;

public class ResolutionRBlock extends ImageResourceBlock {

    public enum ResUnit implements ShortEntry {
        PxPerInch,
        PxPerCm;

        @Override
        public short getValue() {
            return (short) (ordinal() + 1);
        }

        public static ResUnit of(short resUnit) {
            return values()[resUnit - 1];
        }
    }

    public enum Unit implements ShortEntry {
        Inches,
        Centimeters,
        Points,
        Picas,
        Columns;

        @Override
        public short getValue() {
            return (short) (ordinal() + 1);
        }

        public static Unit of(short unit) {
            return values()[unit - 1];
        }
    }

    private float HDpi;
    private ResUnit HResDisplayUnit;
    private Unit WidthDisplayUnit;

    private float VDpi;
    private ResUnit VResDisplayUnit;
    private Unit HeightDisplayUnit;

    public ResolutionRBlock(ImageResourceID identity, String pascalString, long length, float HDpi, ResUnit HResDisplayUnit, Unit widthDisplayUnit, float VDpi, ResUnit VResDisplayUnit, Unit heightDisplayUnit) {
        super(identity, pascalString, length);
        this.HDpi = HDpi;
        this.HResDisplayUnit = HResDisplayUnit;
        WidthDisplayUnit = widthDisplayUnit;
        this.VDpi = VDpi;
        this.VResDisplayUnit = VResDisplayUnit;
        HeightDisplayUnit = heightDisplayUnit;
    }

    public float getHDpi() {
        return HDpi;
    }

    public ResUnit getHResDisplayUnit() {
        return HResDisplayUnit;
    }

    public Unit getWidthDisplayUnit() {
        return WidthDisplayUnit;
    }

    public float getVDpi() {
        return VDpi;
    }

    public ResUnit getVResDisplayUnit() {
        return VResDisplayUnit;
    }

    public Unit getHeightDisplayUnit() {
        return HeightDisplayUnit;
    }

    @Override
    public String toString() {
        return "ResolutionRBlock{" +
                "HDpi=" + HDpi +
                ", HResDisplayUnit=" + HResDisplayUnit +
                ", WidthDisplayUnit=" + WidthDisplayUnit +
                ", VDpi=" + VDpi +
                ", VResDisplayUnit=" + VResDisplayUnit +
                ", HeightDisplayUnit=" + HeightDisplayUnit +
                '}';
    }
}
