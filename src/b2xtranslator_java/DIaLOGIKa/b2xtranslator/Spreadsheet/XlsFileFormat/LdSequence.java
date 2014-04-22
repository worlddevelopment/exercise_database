//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.LdSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Legend;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pos;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.TextPropsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class LdSequence  extends BiffRecordSequence implements IVisitable
{
    public Legend Legend;
    public Begin Begin;
    public Pos Pos;
    public AttachedLabelSequence AttachedLabelSequence;
    public FrameSequence FrameSequence;
    public CrtLayout12 CrtLayout12;
    public TextPropsSequence TextPropsSequence;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public End End;
    public LdSequence(IStreamReader reader) throws Exception {
        super(reader);
        /**
        * Legend Begin Pos ATTACHEDLABEL [FRAME] [CrtLayout12] [TEXTPROPS] [CRTMLFRT] End
        */
        // Legend
        this.Legend = (Legend)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // Pos
        this.Pos = (Pos)BiffRecord.readRecord(reader);
        // [ATTACHEDLABEL]
        this.AttachedLabelSequence = new AttachedLabelSequence(reader);
        // [FRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Frame)
        {
            this.FrameSequence = new FrameSequence(reader);
        }
         
        // [CrtLayout12]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12)
        {
            this.CrtLayout12 = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // [TEXTPROPS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.RichTextStream || BiffRecord.getNextRecordType(reader) == RecordType.TextPropsStream)
        {
            this.TextPropsSequence = new TextPropsSequence(reader);
        }
         
        //[CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<LdSequence>)mapping).apply(this);
    }

}


