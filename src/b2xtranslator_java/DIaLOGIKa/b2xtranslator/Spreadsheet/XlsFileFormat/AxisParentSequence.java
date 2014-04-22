//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxesSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AxisParent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pos;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AxisParentSequence  extends BiffRecordSequence 
{
    public AxisParent AxisParent;
    public Begin Begin;
    public Pos Pos;
    public AxesSequence AxesSequence;
    public CSList<CrtSequence> CrtSequences;
    public End End;
    public AxisParentSequence(IStreamReader reader) throws Exception {
        super(reader);
        // AXISPARENT = AxisParent Begin Pos [AXES] 1*4CRT End
        // AxisParent
        this.AxisParent = (AxisParent)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // Pos
        this.Pos = (Pos)BiffRecord.readRecord(reader);
        // [AXES]
        RecordType next = BiffRecord.getNextRecordType(reader);
        if (next == RecordType.Axis || next == RecordType.Text || next == RecordType.PlotArea)
        {
            this.AxesSequence = new AxesSequence(reader);
        }
         
        // 1*4CRT
        this.CrtSequences = new CSList<CrtSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ChartFormat)
        {
            this.CrtSequences.add(new CrtSequence(reader));
        }
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

}


