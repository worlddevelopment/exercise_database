//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxmSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Axis;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ValueRange;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class DvAxisSequence  extends BiffRecordSequence 
{
    public Axis Axis;
    public Begin Begin;
    public ValueRange ValueRange;
    public AxmSequence AxmSequence;
    public AxsSequence AxsSequence;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public End End;
    public DvAxisSequence(IStreamReader reader) throws Exception {
        super(reader);
        // DVAXIS = Axis Begin [ValueRange] [AXM] AXS [CRTMLFRT] End
        // Axis
        this.Axis = (Axis)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // [ValueRange]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ValueRange)
        {
            this.ValueRange = (ValueRange)BiffRecord.readRecord(reader);
        }
         
        // [AXM]
        if (BiffRecord.getNextRecordType(reader) == RecordType.YMult)
        {
            this.AxmSequence = new AxmSequence(reader);
        }
         
        // AXS
        this.AxsSequence = new AxsSequence(reader);
        // [CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

}


