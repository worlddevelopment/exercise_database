//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxesSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartAxisIdGenerator;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DvAxisSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.IvAxisSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Axis;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PlotArea;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AxesSequence  extends BiffRecordSequence implements IVisitable
{
    public IvAxisSequence IvAxisSequence;
    public DvAxisSequence DvAxisSequence;
    public DvAxisSequence DvAxisSequence2;
    // NOTE: SeriesAxisSequence is just a simplified IvAxisSequence
    //public SeriesAxisSequence SeriesAxisSequence;
    public IvAxisSequence SeriesAxisSequence;
    public CSList<AttachedLabelSequence> AttachedLabelSequences;
    public PlotArea PlotArea;
    public FrameSequence Frame;
    public AxesSequence(IStreamReader reader) throws Exception {
        super(reader);
        //AXES = [IVAXIS DVAXIS [SERIESAXIS] / DVAXIS DVAXIS] *3ATTACHEDLABEL [PlotArea FRAME]
        // [IVAXIS DVAXIS [SERIESAXIS] / DVAXIS DVAXIS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Axis)
        {
            long position = Unsupported.throwUnsupported("reader.getBaseStream().Position");
            Axis axis = (Axis)BiffRecord.readRecord(reader);
            Begin begin = (Begin)BiffRecord.readRecord(reader);
            if (BiffRecord.getNextRecordType(reader) == RecordType.CatSerRange || BiffRecord.getNextRecordType(reader) == RecordType.AxcExt)
            {
                Unsupported.throwUnsupported("reader.getBaseStream().Position = position");
                ChartAxisIdGenerator.getInstance().startNewAxisGroup();
                //IVAXIS
                this.IvAxisSequence = new IvAxisSequence(reader);
                //DVAXIS
                this.DvAxisSequence = new DvAxisSequence(reader);
                //[SERIESAXIS]
                if (BiffRecord.getNextRecordType(reader) == RecordType.Axis)
                {
                    this.SeriesAxisSequence = new IvAxisSequence(reader);
                }
                 
            }
            else
            {
                Unsupported.throwUnsupported("reader.getBaseStream().Position = position");
                ChartAxisIdGenerator.getInstance().startNewAxisGroup();
                //DVAXIS
                this.DvAxisSequence = new DvAxisSequence(reader);
                //DVAXIS
                this.DvAxisSequence2 = new DvAxisSequence(reader);
            } 
        }
         
        //*3ATTACHEDLABEL
        this.AttachedLabelSequences = new CSList<AttachedLabelSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Text)
        {
            this.AttachedLabelSequences.add(new AttachedLabelSequence(reader));
        }
        //[PlotArea FRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.PlotArea)
        {
            this.PlotArea = (PlotArea)BiffRecord.readRecord(reader);
            this.Frame = new FrameSequence(reader);
        }
         
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<AxesSequence>)mapping).apply(this);
    }

}


