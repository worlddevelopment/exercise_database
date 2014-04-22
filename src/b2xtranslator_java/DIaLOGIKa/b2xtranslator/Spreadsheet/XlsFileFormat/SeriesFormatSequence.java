//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AiSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LegendException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SerAuxErrBar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SerAuxTrend;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Series;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SerParent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SerToCrt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SeriesFormatSequence  extends BiffRecordSequence implements IVisitable
{
    public Series Series;
    public Begin Begin;
    public CSList<AiSequence> AiSequences;
    public CSList<SsSequence> SsSequence;
    public SerToCrt SerToCrt;
    public SerParent SerParent;
    public SerAuxTrend SerAuxTrend;
    public SerAuxErrBar SerAuxErrBar;
    public LegendExceptionGroup LegendExceptionSequence;
    public End End;
    /**
    * An unsigned integer that specifies the order of the series in the collection. It is 0 based.
    * 
    * NOTE: This value is set at parse time. It is not stored in the binary file.
    */
    public UInt16 order = new UInt16();
    public SeriesFormatSequence(IStreamReader reader) throws Exception {
        super(reader);
        // SERIESFORMAT =
        // Series Begin 4AI *SS
        // (SerToCrt / (SerParent (SerAuxTrend / SerAuxErrBar)))
        // *(LegendException [Begin ATTACHEDLABEL End])
        // End
        // Series
        this.Series = (Series)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // 4AI
        this.AiSequences = new CSList<AiSequence>();
        for (int i = 0;i < 4;i++)
        {
            this.AiSequences.add(new AiSequence(reader));
        }
        // *SS
        this.SsSequence = new CSList<SsSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.DataFormat)
        {
            this.SsSequence.add(new SsSequence(reader));
        }
        // (SerToCrt / (SerParent (SerAuxTrend / SerAuxErrBar)))
        if (BiffRecord.getNextRecordType(reader) == RecordType.SerToCrt)
        {
            this.SerToCrt = (SerToCrt)BiffRecord.readRecord(reader);
        }
        else
        {
            this.SerParent = (SerParent)BiffRecord.readRecord(reader);
            // (SerAuxTrend / SerAuxErrBar)
            if (BiffRecord.getNextRecordType(reader) == RecordType.SerAuxTrend)
            {
                this.SerAuxTrend = (SerAuxTrend)BiffRecord.readRecord(reader);
            }
            else
            {
                this.SerAuxErrBar = (SerAuxErrBar)BiffRecord.readRecord(reader);
            } 
        } 
        while (BiffRecord.getNextRecordType(reader) == RecordType.LegendException)
        {
            // *(LegendException [Begin ATTACHEDLABEL End])
            this.LegendExceptionSequence = new LegendExceptionGroup(reader);
        }
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<SeriesFormatSequence>)mapping).apply(this);
    }

    public static class LegendExceptionGroup  extends BiffRecordSequence 
    {
        public LegendException LegendException;
        public Begin Begin;
        public AttachedLabelSequence AttachedLabelSequence;
        public End End;
        public LegendExceptionGroup(IStreamReader reader) throws Exception {
            super(reader);
            // *(LegendException [Begin ATTACHEDLABEL End])
            this.LegendException = (LegendException)BiffRecord.readRecord(reader);
            // [Begin ATTACHEDLABEL End]
            if (BiffRecord.getNextRecordType(reader) == RecordType.Begin)
            {
                // Begin
                this.Begin = (Begin)BiffRecord.readRecord(reader);
                // ATTACHEDLABEL
                this.AttachedLabelSequence = new AttachedLabelSequence(reader);
                // End
                this.End = (End)BiffRecord.readRecord(reader);
            }
             
        }
    
    }

}


