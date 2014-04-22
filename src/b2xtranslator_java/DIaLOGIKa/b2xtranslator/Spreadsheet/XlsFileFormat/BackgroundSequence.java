//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BkHim;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class BackgroundSequence  extends BiffRecordSequence 
{
    public BkHim BkHim;
    public CSList<Continue> Continues;
    public BackgroundSequence(IStreamReader reader) throws Exception {
        super(reader);
        // BACKGROUND = BkHim *Continue
        // BkHim
        this.BkHim = (BkHim)BiffRecord.readRecord(reader);
        // *Continue
        this.Continues = new CSList<Continue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
        {
            this.Continues.add((Continue)BiffRecord.readRecord(reader));
        }
    }

}


