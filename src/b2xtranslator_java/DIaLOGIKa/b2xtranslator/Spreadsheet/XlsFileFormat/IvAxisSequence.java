//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AxcExt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Axis;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CatLab;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CatSerRange;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class IvAxisSequence  extends BiffRecordSequence 
{
    public Axis Axis;
    public Begin Begin;
    public CatSerRange CatSerRange;
    public AxcExt AxcExt;
    public CatLab CatLab;
    public AxsSequence AxsSequence;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public End End;
    public IvAxisSequence(IStreamReader reader) throws Exception {
        super(reader);
        // IVAXIS = Axis Begin [CatSerRange] AxcExt [CatLab] AXS [CRTMLFRT] End
        // Axis
        this.Axis = (Axis)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // [CatSerRange]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CatSerRange)
        {
            this.CatSerRange = (CatSerRange)BiffRecord.readRecord(reader);
        }
         
        // AxcExt
        if (BiffRecord.getNextRecordType(reader) == RecordType.AxcExt)
        {
            // NOTE: we parse this as an optional record because then we can use the IvAxisSequence to
            //    parse a SeriesDataSequence as well. SeriesDataSequence is just a simple version of IvAxisSequence.
            //    This simplifies mapping later on.
            this.AxcExt = (AxcExt)BiffRecord.readRecord(reader);
        }
         
        // [CatLab]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CatLab)
        {
            this.CatLab = (CatLab)BiffRecord.readRecord(reader);
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


