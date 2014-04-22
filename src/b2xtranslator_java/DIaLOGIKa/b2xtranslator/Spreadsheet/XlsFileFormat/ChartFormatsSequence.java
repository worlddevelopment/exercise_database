//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxisParentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DftTextSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FontListSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AxesUsed;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Dat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DataLabExt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PlotGrowth;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Scl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ShtProps;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.TextPropsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ChartFormatsSequence  extends BiffRecordSequence implements IVisitable
{
    public Chart Chart;
    public Begin Begin;
    public CSList<FontListSequence> FontListSequences;
    public Scl Scl;
    public PlotGrowth PlotGrowth;
    public FrameSequence FrameSequence;
    public CSList<SeriesFormatSequence> SeriesFormatSequences;
    public CSList<SsSequence> SsSequences;
    public ShtProps ShtProps;
    public CSList<DftTextSequence> DftTextSequences;
    public AxesUsed AxesUsed;
    public CSList<AxisParentSequence> AxisParentSequences;
    public CSList<AttachedLabelSequence> AttachedLabelSequences;
    public CSList<DataLabelGroup> DataLabelGroups;
    public TextPropsSequence TextPropsSequence;
    public Dat Dat;
    public CrtLayout12 CrtLayout12A;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public CSList<CrtMlfrtSequence> CrtMlfrtSequences;
    public End End;
    public ChartFormatsSequence(IStreamReader reader) throws Exception {
        super(reader);
        // CHARTFOMATS = Chart Begin *2FONTLIST Scl PlotGrowth [FRAME] *SERIESFORMAT *SS ShtProps
        //    *2DFTTEXT AxesUsed 1*2AXISPARENT [CrtLayout12A] [DAT] *ATTACHEDLABEL [CRTMLFRT]
        //    *([DataLabExt StartObject] ATTACHEDLABEL [EndObject]) [TEXTPROPS] *2CRTMLFRT End
        // Chart
        this.Chart = (Chart)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // *2FONTLIST
        this.FontListSequences = new CSList<FontListSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.FrtFontList)
        {
            this.FontListSequences.add(new FontListSequence(reader));
        }
        // Scl
        this.Scl = (Scl)BiffRecord.readRecord(reader);
        // PlotGrowth
        this.PlotGrowth = (PlotGrowth)BiffRecord.readRecord(reader);
        // [FRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Frame)
        {
            this.FrameSequence = new FrameSequence(reader);
        }
         
        // *SERIESFORMAT
        this.SeriesFormatSequences = new CSList<SeriesFormatSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Series)
        {
            SeriesFormatSequence seriesFormatSequence = new SeriesFormatSequence(reader);
            // remember the index in the collection
            seriesFormatSequence.order = (UInt16)this.SeriesFormatSequences.size();
            this.SeriesFormatSequences.add(seriesFormatSequence);
        }
        // *SS
        this.SsSequences = new CSList<SsSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.DataFormat)
        {
            this.SsSequences.add(new SsSequence(reader));
        }
        // ShtProps
        this.ShtProps = (ShtProps)BiffRecord.readRecord(reader);
        // *2DFTTEXT
        this.DftTextSequences = new CSList<DftTextSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExt || BiffRecord.getNextRecordType(reader) == RecordType.DefaultText)
        {
            this.DftTextSequences.add(new DftTextSequence(reader));
        }
        // AxesUsed
        this.AxesUsed = (AxesUsed)BiffRecord.readRecord(reader);
        // 1*2AXISPARENT
        this.AxisParentSequences = new CSList<AxisParentSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.AxisParent)
        {
            this.AxisParentSequences.add(new AxisParentSequence(reader));
        }
        // [CrtLayout12A]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12A)
        {
            this.CrtLayout12A = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // [DAT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Dat)
        {
            this.Dat = (Dat)BiffRecord.readRecord(reader);
        }
         
        // *ATTACHEDLABEL
        this.AttachedLabelSequences = new CSList<AttachedLabelSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Text)
        {
            this.AttachedLabelSequences.add(new AttachedLabelSequence(reader));
        }
        // [CrtLayout12A]
        // NOTE: The occurence of a CrtLayout12A record at this position in the sequence
        //    is a deviation from the spec. However it has been encountered in certain
        //    test documents (even if these documents were re-saved using Excel 2003)
        //
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12A)
        {
            this.CrtLayout12A = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // [CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        // [CrtLayout12A]
        // NOTE: The occurence of a CrtLayout12A record at this position in the sequence
        //    is a deviation from the spec. However it has been encountered in certain
        //    test documents (even if these documents were re-saved using Excel 2003)
        //
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12A)
        {
            this.CrtLayout12A = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // *([DataLabExt StartObject] ATTACHEDLABEL [EndObject])
        this.DataLabelGroups = new CSList<DataLabelGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExt || BiffRecord.getNextRecordType(reader) == RecordType.Text)
        {
            this.DataLabelGroups.add(new DataLabelGroup(reader));
        }
        // [CrtLayout12A]
        // NOTE: The occurence of a CrtLayout12A record at this position in the sequence
        //    is a deviation from the spec. However it has been encountered in certain
        //    test documents (even if these documents were re-saved using Excel 2003)
        //
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12A)
        {
            this.CrtLayout12A = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // [TEXTPROPS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.RichTextStream || BiffRecord.getNextRecordType(reader) == RecordType.TextPropsStream)
        {
            this.TextPropsSequence = new TextPropsSequence(reader);
        }
         
        // [CrtLayout12A]
        // NOTE: The occurence of a CrtLayout12A record at this position in the sequence
        //    is a deviation from the spec. However it has been encountered in certain
        //    test documents (even if these documents were re-saved using Excel 2003)
        //
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtLayout12A)
        {
            this.CrtLayout12A = (CrtLayout12)BiffRecord.readRecord(reader);
        }
         
        // *2CRTMLFRT
        this.CrtMlfrtSequences = new CSList<CrtMlfrtSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequences.add(new CrtMlfrtSequence(reader));
        }
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public static class DataLabelGroup   
    {
        public DataLabExt DataLabExt;
        //public StartObject StartObject;
        public AttachedLabelSequence AttachedLabelSequence;
        //public EndObject EndObject;
        public DataLabelGroup(IStreamReader reader) throws Exception {
            // *([DataLabExt StartObject] ATTACHEDLABEL [EndObject])
            if (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExt)
            {
                this.DataLabExt = (DataLabExt)BiffRecord.readRecord(reader);
            }
             
            //this.StartObject = (StartObject)BiffRecord.ReadRecord(reader);
            this.AttachedLabelSequence = new AttachedLabelSequence(reader);
        }
    
    }

    //if (BiffRecord.GetNextRecordType(reader) == RecordType.EndObject)
    //{
    //    this.EndObject = (EndObject)BiffRecord.ReadRecord(reader);
    //}
    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ChartFormatsSequence>)mapping).apply(this);
    }

}


