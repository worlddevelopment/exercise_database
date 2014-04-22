//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.GelFrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AreaFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AttachedLabel;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart3DBarShape;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DataFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MarkerFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PieFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SerFmt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ShapePropsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SsSequence  extends BiffRecordSequence implements IVisitable
{
    public DataFormat DataFormat;
    public Begin Begin;
    public Chart3DBarShape Chart3DBarShape;
    public LineFormat LineFormat;
    public AreaFormat AreaFormat;
    public PieFormat PieFormat;
    public SerFmt SerFmt;
    //public LineFormat LineFormat2;
    //public AreaFormat AreaFormat2;
    public GelFrameSequence GelFrameSequence;
    public MarkerFormat MarkerFormat;
    public AttachedLabel AttachedLabel;
    public CSList<ShapePropsSequence> ShapePropsSequences;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public End End;
    public SsSequence(IStreamReader reader) throws Exception {
        super(reader);
        // SS = DataFormat Begin [Chart3DBarShape] [LineFormat AreaFormat PieFormat]
        // [SerFmt] [GELFRAME] [MarkerFormat] [AttachedLabel] *2SHAPEPROPS [CRTMLFRT] End
        /**
        * / SS = DataFormat Begin [Chart3DBarShape] [LineFormat AreaFormat PieFormat]
        * / [SerFmt] [LineFormat] [AreaFormat] [GELFRAME] [MarkerFormat] [AttachedLabel] *2SHAPEPROPS [CRTMLFRT] End
        */
        // DataFormat
        this.DataFormat = (DataFormat)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // [Chart3DBarShape]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Chart3DBarShape)
        {
            this.Chart3DBarShape = (Chart3DBarShape)BiffRecord.readRecord(reader);
        }
         
        // [LineFormat AreaFormat PieFormat]
        if (BiffRecord.getNextRecordType(reader) == RecordType.LineFormat)
        {
            this.LineFormat = (LineFormat)BiffRecord.readRecord(reader);
        }
         
        if (BiffRecord.getNextRecordType(reader) == RecordType.AreaFormat)
        {
            this.AreaFormat = (AreaFormat)BiffRecord.readRecord(reader);
        }
         
        if (BiffRecord.getNextRecordType(reader) == RecordType.PieFormat)
        {
            this.PieFormat = (PieFormat)BiffRecord.readRecord(reader);
        }
         
        /**
        * / this is for the case that LineFormat and AreaFormat
        * / exists and is behind the SerFmt which doesn't exists
        */
        //if (this.PieFormat == null)
        //{
        //    if (this.LineFormat1 != null)
        //    {
        //        this.LineFormat2 = this.LineFormat1;
        //        this.LineFormat1 = null;
        //    }
        //    if (this.AreaFormat1 != null)
        //    {
        //        this.AreaFormat2 = this.AreaFormat1;
        //        this.AreaFormat1 = null;
        //    }
        //}
        // [SerFmt]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SerFmt)
        {
            this.SerFmt = (SerFmt)BiffRecord.readRecord(reader);
        }
         
        // [LineFormat] [AreaFormat] [GELFRAME] [MarkerFormat] [AttachedLabel] End
        //if (BiffRecord.GetNextRecordType(reader) == RecordType.LineFormat)
        //{
        //    this.LineFormat2 = (LineFormat)BiffRecord.ReadRecord(reader);
        //}
        /**
        * / [AreaFormat]
        */
        //if (BiffRecord.GetNextRecordType(reader) ==
        //    RecordType.AreaFormat)
        //{
        //    this.AreaFormat2 = (AreaFormat)BiffRecord.ReadRecord(reader);
        //}
        // [GELFRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.GelFrame)
        {
            this.GelFrameSequence = new GelFrameSequence(reader);
        }
         
        // [MarkerFormat]
        if (BiffRecord.getNextRecordType(reader) == RecordType.MarkerFormat)
        {
            this.MarkerFormat = (MarkerFormat)BiffRecord.readRecord(reader);
        }
         
        // [AttachedLabel]
        if (BiffRecord.getNextRecordType(reader) == RecordType.AttachedLabel)
        {
            this.AttachedLabel = (AttachedLabel)BiffRecord.readRecord(reader);
        }
         
        // *2SHAPEPROPS
        this.ShapePropsSequences = new CSList<ShapePropsSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ShapePropsStream)
        {
            this.ShapePropsSequences.add(new ShapePropsSequence(reader));
        }
        // [CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<SsSequence>)mapping).apply(this);
    }

}


