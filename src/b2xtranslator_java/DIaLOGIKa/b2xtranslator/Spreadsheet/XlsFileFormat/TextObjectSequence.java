//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TxO;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class TextObjectSequence  extends BiffRecordSequence 
{
    public TxO TxO;
    public CSList<Continue> Continue;
    public TextObjectSequence(IStreamReader reader) throws Exception {
        super(reader);
        //TEXTOBJECT = TxO *Continue
        // TxO
        this.TxO = (TxO)BiffRecord.readRecord(reader);
        // Continue
        this.Continue = new CSList<Continue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
        {
            this.Continue.add((Continue)BiffRecord.readRecord(reader));
        }
    }

}


