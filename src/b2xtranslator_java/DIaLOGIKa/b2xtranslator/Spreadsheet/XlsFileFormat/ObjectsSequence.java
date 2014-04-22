//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DrawingsGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MsoDrawingSelection;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ObjectsSequence  extends BiffRecordSequence implements IVisitable
{
    public MsoDrawingSelection MsoDrawingSelection;
    public CSList<DrawingsGroup> DrawingsGroup;
    public ObjectsSequence(IStreamReader reader) throws Exception {
        super(reader);
        // OBJECTS = *(MsoDrawing *Continue *(TEXTOBJECT / OBJ / CHART)) [MsoDrawingSelection]
        // *(MsoDrawing *Continue *(TEXTOBJECT / OBJ / CHART))
        this.DrawingsGroup = new CSList<DrawingsGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.MsoDrawing)
        {
            this.DrawingsGroup.add(new DrawingsGroup(reader));
        }
        // [MsoDrawingSelection]
        if (BiffRecord.getNextRecordType(reader) == RecordType.MsoDrawingSelection)
        {
            this.MsoDrawingSelection = (MsoDrawingSelection)BiffRecord.readRecord(reader);
        }
         
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ObjectsSequence>)mapping).apply(this);
    }

}


