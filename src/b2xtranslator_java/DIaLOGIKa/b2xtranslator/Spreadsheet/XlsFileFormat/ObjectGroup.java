//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Obj;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.TextObjectSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ObjectGroup   
{
    public TextObjectSequence TextObjectSequence;
    public Obj Obj;
    public ChartSheetSequence ChartSheetSequence;
    public ObjectGroup(IStreamReader reader) throws Exception {
        if (BiffRecord.getNextRecordType(reader) == RecordType.Obj)
        {
            this.Obj = (Obj)BiffRecord.readRecord(reader);
        }
        else if (BiffRecord.getNextRecordType(reader) == RecordType.TxO)
        {
            this.TextObjectSequence = new TextObjectSequence(reader);
        }
        else if (BiffRecord.getNextRecordType(reader) == RecordType.BOF)
        {
            this.ChartSheetSequence = new ChartSheetSequence(reader);
        }
           
    }

}


