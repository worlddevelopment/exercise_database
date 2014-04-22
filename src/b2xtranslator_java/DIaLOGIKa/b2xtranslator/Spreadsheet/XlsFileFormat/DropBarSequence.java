//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.GelFrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AreaFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DropBar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ShapePropsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class DropBarSequence  extends BiffRecordSequence 
{
    public DropBar DropBar;
    public Begin Begin;
    public LineFormat LineFormat;
    public AreaFormat AreaFormat;
    public GelFrameSequence GelFrameSequence;
    public ShapePropsSequence ShapePropsSequence;
    public End End;
    public DropBarSequence(IStreamReader reader) throws Exception {
        super(reader);
        // DROPBAR = DropBar Begin LineFormat AreaFormat [GELFRAME] [SHAPEPROPS] End
        // DropBar
        this.DropBar = (DropBar)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // LineFormat
        this.LineFormat = (LineFormat)BiffRecord.readRecord(reader);
        // AreaFormat
        this.AreaFormat = (AreaFormat)BiffRecord.readRecord(reader);
        // [GELFRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.GelFrame)
        {
            this.GelFrameSequence = new GelFrameSequence(reader);
        }
         
        // [SHAPEPROPS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ShapePropsStream)
        {
            this.ShapePropsSequence = new ShapePropsSequence(reader);
        }
         
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

}


