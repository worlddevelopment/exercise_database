//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pane;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PLV;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Scl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Selection;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Window2;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WindowSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class WindowSequence  extends BiffRecordSequence implements IVisitable
{
    public Window2 Window2;
    public PLV PLV;
    public Scl Scl;
    public Pane Pane;
    public CSList<Selection> Selections;
    public WindowSequence(IStreamReader reader) throws Exception {
        super(reader);
        // Window2 [PLV] [Scl] [Pane] *Selection
        // Window2
        this.Window2 = (Window2)BiffRecord.readRecord(reader);
        // [PLV]
        if (BiffRecord.getNextRecordType(reader) == RecordType.PLV)
        {
            this.PLV = (PLV)BiffRecord.readRecord(reader);
        }
         
        // [Scl]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Scl)
        {
            this.Scl = (Scl)BiffRecord.readRecord(reader);
        }
         
        // [Pane]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Pane)
        {
            this.Pane = (Pane)BiffRecord.readRecord(reader);
        }
         
        //*Selection
        this.Selections = new CSList<Selection>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Selection)
        {
            this.Selections.add((Selection)BiffRecord.readRecord(reader));
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<WindowSequence>)mapping).apply(this);
    }

}


