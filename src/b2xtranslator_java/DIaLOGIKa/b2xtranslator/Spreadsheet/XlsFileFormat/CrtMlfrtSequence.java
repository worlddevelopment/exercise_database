//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtMlFrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtMlFrtContinue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CrtMlfrtSequence  extends BiffRecordSequence 
{
    public CSList<CrtMlFrt> CrtMlFrts;
    public CSList<CrtMlFrtContinue> CrtMlFrtContinues;
    public CrtMlfrtSequence(IStreamReader reader) throws Exception {
        super(reader);
        //Spec says: CRTMLFRT = CrtMlFrt *CrtMlFrtContinue
        //Reality says: CRTMLFRT = *CrtMlFrt *CrtMlFrtContinue
        this.CrtMlFrts = new CSList<CrtMlFrt>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlFrts.add((CrtMlFrt)BiffRecord.readRecord(reader));
        }
        this.CrtMlFrtContinues = new CSList<CrtMlFrtContinue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrtContinue)
        {
            this.CrtMlFrtContinues.add((CrtMlFrtContinue)BiffRecord.readRecord(reader));
        }
    }

}


