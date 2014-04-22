//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.LdSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Dat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class DatSequence  extends BiffRecordSequence 
{
    public Dat Dat;
    public Begin Begin;
    public LdSequence LdSequence;
    public End End;
    public DatSequence(IStreamReader reader) throws Exception {
        super(reader);
        // DAT = Dat Begin LD End
        this.Dat = (Dat)BiffRecord.readRecord(reader);
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        this.LdSequence = new LdSequence(reader);
        this.End = (End)BiffRecord.readRecord(reader);
    }

}


