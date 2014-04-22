//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtLineGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DftTextSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DropBarSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.LdSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BopPopCustom;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart3d;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ChartFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DataLabExtContents;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SeriesList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ShapePropsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CrtSequence  extends BiffRecordSequence implements IVisitable
{
    public ChartFormat ChartFormat;
    public Begin Begin;
    public BiffRecord ChartType;
    public BopPopCustom BopPopCustom;
    public CrtLink CrtLink;
    public SeriesList SeriesList;
    public Chart3d Chart3d;
    public LdSequence LdSequence;
    public DropBarSequence[] DropBarSequence;
    public CSList<CrtLineGroup> CrtLineGroups;
    public CSList<DftTextSequence> DftTextSequences;
    public DataLabExtContents DataLabExtContents;
    public SsSequence SsSequence;
    public CSList<ShapePropsSequence> ShapePropsSequences;
    public End End;
    public CrtSequence(IStreamReader reader) throws Exception {
        super(reader);
        // ChartFormat
        //   Begin
        //     (Bar / Line / (BopPop [BopPopCustom]) / Pie / Area / Scatter / Radar / RadarArea / Surf)
        //     CrtLink [SeriesList] [Chart3d] [LD] [2DROPBAR] *4(CrtLine LineFormat) *2DFTTEXT [DataLabExtContents] [SS] *4SHAPEPROPS
        //   End
        // ChartFormat
        this.ChartFormat = (ChartFormat)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // (Bar / Line / (BopPop [BopPopCustom]) / Pie / Area / Scatter / Radar / RadarArea / Surf)
        this.ChartType = BiffRecord.readRecord(reader);
        if (BiffRecord.getNextRecordType(reader) == RecordType.BopPopCustom)
        {
            this.BopPopCustom = (BopPopCustom)BiffRecord.readRecord(reader);
        }
         
        // CrtLink
        this.CrtLink = (CrtLink)BiffRecord.readRecord(reader);
        // [SeriesList]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SeriesList)
        {
            this.SeriesList = (SeriesList)BiffRecord.readRecord(reader);
        }
         
        // [Chart3d]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Chart3d)
        {
            this.Chart3d = (Chart3d)BiffRecord.readRecord(reader);
        }
         
        // [LD]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Legend)
        {
            this.LdSequence = new LdSequence(reader);
        }
         
        // [2DROPBAR]
        if (BiffRecord.getNextRecordType(reader) == RecordType.DropBar)
        {
            this.DropBarSequence = new DropBarSequence[2];
            for (int i = 0;i < 2;i++)
            {
                this.DropBarSequence[i] = new DropBarSequence(reader);
            }
        }
         
        //*4(CrtLine LineFormat)
        this.CrtLineGroups = new CSList<CrtLineGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.CrtLine)
        {
            this.CrtLineGroups.add(new CrtLineGroup(reader));
        }
        //*2DFTTEXT
        this.DftTextSequences = new CSList<DftTextSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExt || BiffRecord.getNextRecordType(reader) == RecordType.DefaultText)
        {
            this.DftTextSequences.add(new DftTextSequence(reader));
        }
        //[DataLabExtContents]
        if (BiffRecord.getNextRecordType(reader) == RecordType.DataLabExtContents)
        {
            this.DataLabExtContents = (DataLabExtContents)BiffRecord.readRecord(reader);
        }
         
        //[SS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.DataFormat)
        {
            this.SsSequence = new SsSequence(reader);
        }
         
        //*4SHAPEPROPS
        this.ShapePropsSequences = new CSList<ShapePropsSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ShapePropsStream)
        {
            this.ShapePropsSequences.add(new ShapePropsSequence(reader));
        }
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            CrtMlfrtSequence crtmlfrtseq = new CrtMlfrtSequence(reader);
        }
         
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<CrtSequence>)mapping).apply(this);
    }

}


