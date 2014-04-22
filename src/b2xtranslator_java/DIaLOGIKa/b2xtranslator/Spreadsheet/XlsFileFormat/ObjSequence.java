//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ObjSequence  extends BiffRecordSequence 
{
    public CSList<Continue> Continue;
    // public Obj Obj;
    public ObjSequence(IStreamReader reader) throws Exception {
        super(reader);
        //OBJ = Obj *Continue
        // Obj
        // this.Obj = (Obj)BiffRecord.ReadRecord(reader);
        // *Continue
        this.Continue = new CSList<Continue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
        {
            this.Continue.add((Continue)BiffRecord.readRecord(reader));
        }
    }

}


