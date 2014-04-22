//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:42 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/**
* This record specifies layout information for a plot area.
*/
public class CrtLayout12  extends BiffRecord implements IVisitable
{
    public enum CrtLayout12Mode
    {
        L12MAUTO,
        L12MFACTOR,
        L12MEDGE
    }
    public enum AutoLayoutType
    {
        Bottom,
        TopRightCorner,
        Top,
        Right,
        Left
    }
    /**
    * 
    */
    public FrtHeader frtHeader;
    /**
    * An unsigned integer that specifies the checksum. 
    * MUST be a value from the following table:
    */
    public long dwCheckSum;
    /**
    * A bit that specifies the type of plot area for the layout target.
    * false = Outer plot area - The bounding rectangle that includes the axis labels, axis titles, data table and plot area of the chart.
    * true = Inner plot area – The rectangle bounded by the chart axes.
    */
    public boolean fLayoutTargetInner;
    /**
    * An unsigned integer that specifies the automatic layout type of the legend.
    * MUST be ignored when this record is in the sequence of records that
    * conforms to the ATTACHEDLABEL rule.
    */
    public DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12.AutoLayoutType autolayouttype = DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12.AutoLayoutType.Bottom;
    /**
    * A signed integer that specifies the horizontal offset of the plot area‘s upper-left corner,
    * relative to the upper-left corner of the chart area, in SPRC.
    */
    public short xTL;
    /**
    * A signed integer that specifies the vertical offset of the plot area‘s upper-left corner,
    * relative to the upper-left corner of the chart area, in SPRC.
    */
    public short yTL;
    /**
    * A signed integer that specifies the width of the plot area, in SPRC.
    */
    public short xBR;
    /**
    * A signed integer that specifies the height of the plot area, in SPRC.
    */
    public short yBR;
    /**
    * A CrtLayout12Mode that specifies the meaning of x.
    */
    public CrtLayout12Mode wXMode = CrtLayout12Mode.L12MAUTO;
    /**
    * A CrtLayout12Mode that specifies the meaning of y.
    */
    public CrtLayout12Mode wYMode = CrtLayout12Mode.L12MAUTO;
    /**
    * A CrtLayout12Mode that specifies the meaning of dx.
    */
    public CrtLayout12Mode wWidthMode = CrtLayout12Mode.L12MAUTO;
    /**
    * A CrtLayout12Mode that specifies the meaning of dy.
    */
    public CrtLayout12Mode wHeightMode = CrtLayout12Mode.L12MAUTO;
    /**
    * An Xnum that specifies a horizontal offset. The meaning is determined by wXMode.
    */
    public double x;
    /**
    * An Xnum that specifies a vertical offset. The meaning is determined by wYMode.
    */
    public double y;
    /**
    * An Xnum that specifies a width or a horizontal offset. The meaning is determined by wWidthMode.
    */
    public double dx;
    /**
    * An Xnum that specifies a height or a vertical offset. The meaning is determined by wHeightMode.
    */
    public double dy;
    public CrtLayout12(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.frtHeader = new FrtHeader(reader);
        this.dwCheckSum = reader.readUInt32();
        UInt16 flags = reader.readUInt16();
        this.fLayoutTargetInner = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1);
        this.autolayouttype = (DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12.AutoLayoutType)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(flags, 0xE);
        if (id == RecordType.CrtLayout12A)
        {
            this.xTL = reader.readInt16();
            this.yTL = reader.readInt16();
            this.xBR = reader.readInt16();
            this.yBR = reader.readInt16();
        }
         
        this.wXMode = (CrtLayout12Mode)reader.readUInt16();
        this.wYMode = (CrtLayout12Mode)reader.readUInt16();
        this.wWidthMode = (CrtLayout12Mode)reader.readUInt16();
        this.wHeightMode = (CrtLayout12Mode)reader.readUInt16();
        this.x = reader.readDouble();
        this.y = reader.readDouble();
        this.dx = reader.readDouble();
        this.dy = reader.readDouble();
        reader.readBytes(2);
        //reserved
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<CrtLayout12>)mapping).apply(this);
    }

}


