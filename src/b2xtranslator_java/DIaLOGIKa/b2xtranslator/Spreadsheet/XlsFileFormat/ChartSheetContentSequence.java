//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BackgroundSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartAxisIdGenerator;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatIdGenerator;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtMlfrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CustomViewSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.PageSetupSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ProtectionSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ClrtClient;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CodeName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.EOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Fbi;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Fbi2;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HeaderFooter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HFPicture;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MsoDrawingGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Palette;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PivotChartBits;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PrintSize;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SBaseRef;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SheetExt;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SXViewLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Units;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.WebPub;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.WriteProtect;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesDataSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WindowSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class ChartSheetContentSequence  extends BiffRecordSequence implements IVisitable
{
    public WriteProtect WriteProtect;
    public SheetExt SheetExt;
    public WebPub WebPub;
    public CSList<HFPicture> HFPictures;
    public PageSetupSequence PageSetupSequence;
    public PrintSize PrintSize;
    public HeaderFooter HeaderFooter;
    public BackgroundSequence BackgroundSequence;
    public CSList<Fbi> Fbis;
    public CSList<Fbi2> Fbi2s;
    public ClrtClient ClrtClient;
    public ProtectionSequence ProtectionSequence;
    public Palette Palette;
    public CodeName CodeName;
    public Units Units;
    public SXViewLink SXViewLink;
    public ChartFormatsSequence ChartFormatsSequence;
    public ObjectsSequence ObjectsSequence;
    public CrtMlfrtSequence CrtMlfrtSequence;
    public SeriesDataSequence SeriesDataSequence;
    public PivotChartBits PivotChartBits;
    public SBaseRef SBaseRef;
    public MsoDrawingGroup MsoDrawingGroup;
    public CSList<WindowSequence> WindowSequences;
    public CSList<CustomViewSequence> CustomViewSequences;
    public EOF EOF;
    public ChartSheetContentSequence(IStreamReader reader) throws Exception {
        super(reader);
        // reset id counter for chart groups
        ChartFormatIdGenerator.getInstance().startNewChartsheetSubstream();
        ChartAxisIdGenerator.getInstance().startNewChartsheetSubstream();
        // CHARTSHEETCONTENT = [WriteProtect] [SheetExt] [WebPub] *HFPicture PAGESETUP PrintSize [HeaderFooter] [BACKGROUND] *Fbi *Fbi2 [ClrtClient] [PROTECTION]
        //          [Palette] [SXViewLink] [PivotChartBits] [SBaseRef] [MsoDrawingGroup] OBJECTS Units CHARTFOMATS SERIESDATA *WINDOW *CUSTOMVIEW [CodeName] [CRTMLFRT] EOF
        // [WriteProtect]
        if (BiffRecord.getNextRecordType(reader) == RecordType.WriteProtect)
        {
            this.WriteProtect = (WriteProtect)BiffRecord.readRecord(reader);
        }
         
        // [SheetExt]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SheetExt)
        {
            this.SheetExt = (SheetExt)BiffRecord.readRecord(reader);
        }
         
        // [WebPub]
        if (BiffRecord.getNextRecordType(reader) == RecordType.WebPub)
        {
            this.WebPub = (WebPub)BiffRecord.readRecord(reader);
        }
         
        while (BiffRecord.getNextRecordType(reader) == RecordType.HFPicture)
        {
            // *HFPicture
            this.HFPictures.add((HFPicture)BiffRecord.readRecord(reader));
        }
        // PAGESETUP
        this.PageSetupSequence = new PageSetupSequence(reader);
        // PrintSize
        if (BiffRecord.getNextRecordType(reader) == RecordType.PrintSize)
        {
            this.PrintSize = (PrintSize)BiffRecord.readRecord(reader);
        }
         
        // [HeaderFooter]
        if (BiffRecord.getNextRecordType(reader) == RecordType.HeaderFooter)
        {
            this.HeaderFooter = (HeaderFooter)BiffRecord.readRecord(reader);
        }
         
        // [BACKGROUND]
        if (BiffRecord.getNextRecordType(reader) == RecordType.BkHim)
        {
            this.BackgroundSequence = new BackgroundSequence(reader);
        }
         
        // *Fbi
        this.Fbis = new CSList<Fbi>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Fbi)
        {
            this.Fbis.add((Fbi)BiffRecord.readRecord(reader));
        }
        // *Fbi2
        this.Fbi2s = new CSList<Fbi2>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Fbi2)
        {
            this.Fbi2s.add((Fbi2)BiffRecord.readRecord(reader));
        }
        // [ClrtClient]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ClrtClient)
        {
            this.ClrtClient = (ClrtClient)BiffRecord.readRecord(reader);
        }
         
        // [PROTECTION]
        this.ProtectionSequence = new ProtectionSequence(reader);
        // [Palette]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Palette)
        {
            this.Palette = (Palette)BiffRecord.readRecord(reader);
        }
         
        // [SXViewLink]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SXViewLink)
        {
            this.SXViewLink = (SXViewLink)BiffRecord.readRecord(reader);
        }
         
        // [PivotChartBits]
        if (BiffRecord.getNextRecordType(reader) == RecordType.PivotChartBits)
        {
            this.PivotChartBits = (PivotChartBits)BiffRecord.readRecord(reader);
        }
         
        // [SBaseRef]
        if (BiffRecord.getNextRecordType(reader) == RecordType.SBaseRef)
        {
            this.SBaseRef = (SBaseRef)BiffRecord.readRecord(reader);
        }
         
        // [MsoDrawingGroup]
        if (BiffRecord.getNextRecordType(reader) == RecordType.MsoDrawingGroup)
        {
            this.MsoDrawingGroup = (MsoDrawingGroup)BiffRecord.readRecord(reader);
        }
         
        // OBJECTS
        this.ObjectsSequence = new ObjectsSequence(reader);
        // Units
        this.Units = (Units)BiffRecord.readRecord(reader);
        // CHARTFOMATS
        this.ChartFormatsSequence = new ChartFormatsSequence(reader);
        // SERIESDATA
        this.SeriesDataSequence = new SeriesDataSequence(reader);
        // *WINDOW
        this.WindowSequences = new CSList<WindowSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Window2)
        {
            this.WindowSequences.add(new WindowSequence(reader));
        }
        // *CUSTOMVIEW
        this.CustomViewSequences = new CSList<CustomViewSequence>();
        while (BiffRecord.getNextRecordType(reader) != RecordType.CodeName && BiffRecord.getNextRecordType(reader) != RecordType.CrtMlFrt && BiffRecord.getNextRecordType(reader) != RecordType.EOF)
        {
            // CUSTOMVIEW seems to be totally optional,
            // so check for the existence of the next sequences
            this.CustomViewSequences.add(new CustomViewSequence(reader));
        }
        //this.CustomViewSequences = new List<CustomViewSequence>();
        //while (BiffRecord.GetNextRecordType(reader) == RecordType.UserSViewBegin)
        //{
        //    this.CustomViewSequences.Add(new CustomViewSequence(reader));
        //}
        // [CodeName]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CodeName)
        {
            this.CodeName = (CodeName)BiffRecord.readRecord(reader);
        }
         
        // [CRTMLFRT]
        if (BiffRecord.getNextRecordType(reader) == RecordType.CrtMlFrt)
        {
            this.CrtMlfrtSequence = new CrtMlfrtSequence(reader);
        }
         
        // EOF
        this.EOF = (EOF)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ChartSheetContentSequence>)mapping).apply(this);
    }

}


