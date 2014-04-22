//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PicF;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class PicfSequence  extends BiffRecordSequence 
{
    public Begin Begin;
    public PicF PicF;
    public End End;
    public PicfSequence(IStreamReader reader) throws Exception {
        super(reader);
        // PICF = Begin PicF End
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // PicF
        this.PicF = (PicF)BiffRecord.readRecord(reader);
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

}


