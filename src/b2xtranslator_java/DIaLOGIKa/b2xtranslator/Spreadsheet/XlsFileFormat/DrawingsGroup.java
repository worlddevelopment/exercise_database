//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MsoDrawing;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class DrawingsGroup   
{
    public MsoDrawing MsoDrawing;
    public CSList<Continue> Continues;
    public CSList<ObjectGroup> Objects;
    public DrawingsGroup(IStreamReader reader) throws Exception {
        // MsoDrawing *Continue *(TEXTOBJECT / OBJ / CHART)
        // MsoDrawing
        this.MsoDrawing = (MsoDrawing)BiffRecord.readRecord(reader);
        // *Continue
        this.Continues = new CSList<Continue>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
        {
            this.Continues.add((Continue)BiffRecord.readRecord(reader));
        }
        // *(TEXTOBJECT / OBJ / CHART)
        this.Objects = new CSList<ObjectGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Obj || BiffRecord.getNextRecordType(reader) == RecordType.TxO || BiffRecord.getNextRecordType(reader) == RecordType.BOF)
        {
            this.Objects.add(new ObjectGroup(reader));
        }
    }

}


