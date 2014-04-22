//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SeriesText;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AiSequence  extends BiffRecordSequence 
{
    public BRAI BRAI;
    public SeriesText SeriesText;
    public AiSequence(IStreamReader reader) throws Exception {
        super(reader);
        //AI = BRAI [SeriesText]
        //BRAI
        this.BRAI = (BRAI)BiffRecord.readRecord(reader);
        //[SeriesText]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SeriesText)
        {
            this.SeriesText = (SeriesText)BiffRecord.readRecord(reader);
        }
         
    }

}


