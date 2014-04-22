//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AiSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AlRuns;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DataLabExtContents;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.FontX;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjectLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pos;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Text;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.TextPropsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AttachedLabelSequence  extends BiffRecordSequence implements IVisitable
{
    public Text Text;
    public Begin Begin;
    public Pos Pos;
    public FontX FontX;
    public AlRuns AlRuns;
    public AiSequence AiSequence;
    public FrameSequence FrameSequence;
    public ObjectLink ObjectLink;
    public DataLabExtContents DataLabExtContents;
    public CrtLayout12 CrtLayout12;
    public TextPropsSequence TextPropsSequence;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public End End;
    public AttachedLabelSequence(IStreamReader reader) throws Exception {
        super(reader);
        //ATTACHEDLABEL = Text Begin Pos [FontX] [AlRuns] AI [FRAME] [ObjectLink] [DataLabExtContents] [CrtLayout12] [TEXTPROPS] [CRTMLFRT] End
        //Text
        this.Text = (Text)BiffRecord.readRecord(reader);
        //Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        //Pos
        this.Pos = (Pos)BiffRecord.readRecord(reader);
        //[FontX]
        if (BiffRecord.getNextRecordType(reader) == RecordType.FontX)
        {
            this.FontX = (FontX)BiffRecord.readRecord(reader);
        }
         
        //[AlRuns]
        if (BiffRecord.getNextRecordType(reader) == RecordType.AlRuns)
        {
            this.AlRuns = (AlRuns)BiffRecord.readRecord(reader);
        }
         
        //AI
        this.AiSequence = new AiSequence(reader);
        //[FRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Frame)
        {
            this.FrameSequence = new FrameSequence(reader);
        }
         
        //[ObjectLink]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ObjectLink)
        {
            this.ObjectLink = (ObjectLink)BiffRecord.readRecord(reader);
        }
         
        //[DataLabExtContents]
        if (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExtContents)
        {
            this.DataLabExtContents = (DataLabExtContents)BiffRecord.readRecord(reader);
        }
         
        //[CrtLayout12]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12)
        {
            this.CrtLayout12 = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        //[TEXTPROPS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.RichTextStream || BiffRecord.getNextRecordType(reader) == RecordType.TextPropsStream)
        {
            this.TextPropsSequence = new TextPropsSequence(reader);
        }
         
        //[CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        //End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<AttachedLabelSequence>)mapping).apply(this);
    }

}


