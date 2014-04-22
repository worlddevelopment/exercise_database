//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:37 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffHeader;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Extractor;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ARRAY;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Blank;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF.DocumentType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BottomMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ColInfo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DefaultRowHeight;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DefColWidth;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Formula;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LabelSst;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LeftMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MergeCells;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MulBlank;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MulRk;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Number;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RightMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RK;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Row;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Setup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ShrFmla;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.STRING;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TopMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkSheetData;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

/**
* This class should extract the specific worksheet data.
*/
public class WorksheetExtractor  extends Extractor 
{
    /**
    * Datacontainer for the worksheet
    */
    private WorkSheetData bsd;
    /**
    * CTor
    * 
    *  @param reader 
    *  @param bsd  Boundsheetdata container
    */
    public WorksheetExtractor(VirtualStreamReader reader, WorkSheetData bsd) throws Exception {
        super(reader);
        this.bsd = bsd;
        this.extractData();
    }

    /**
    * Extracting the data from the stream
    */
    public void extractData() throws Exception {
        BiffHeader bh = new BiffHeader(), latestbiff = new BiffHeader();
        BOF firstBOF = null;
        while (Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position") < this.StreamReader.getBaseStream().Length)
        {
            //try
            //{
            bh.id = (RecordType)this.StreamReader.readUInt16();
            bh.length = this.StreamReader.readUInt16();
            TraceLogger.debugInternal("BIFF {0}\t{1}\t",bh.id,bh.length);
            switch(bh.id)
            {
                case EOF: 
                    {
                        this.StreamReader.getBaseStream().Seek(0, SeekOrigin.End);
                    }
                    break;
                case BOF: 
                    {
                        BOF bof = new BOF(this.StreamReader, bh.id, bh.length);
                        switch(bof.docType)
                        {
                            case WorkbookGlobals: 
                            case Worksheet: 
                                firstBOF = bof;
                                break;
                            case Chart: 
                                break;
                            default: 
                                // parse chart
                                this.readUnkownFile();
                                break;
                        
                        }
                    }
                    break;
                case LabelSst: 
                    {
                        LabelSst labelsst = new LabelSst(this.StreamReader, bh.id, bh.length);
                        this.bsd.addLabelSST(labelsst);
                    }
                    break;
                case MulRk: 
                    {
                        MulRk mulrk = new MulRk(this.StreamReader, bh.id, bh.length);
                        this.bsd.addMULRK(mulrk);
                    }
                    break;
                case Number: 
                    {
                        Number number = new Number(this.StreamReader, bh.id, bh.length);
                        this.bsd.addNUMBER(number);
                    }
                    break;
                case RK: 
                    {
                        RK rk = new RK(this.StreamReader, bh.id, bh.length);
                        this.bsd.addRK(rk);
                    }
                    break;
                case MergeCells: 
                    {
                        MergeCells mergecells = new MergeCells(this.StreamReader, bh.id, bh.length);
                        this.bsd.MERGECELLSData = mergecells;
                    }
                    break;
                case Blank: 
                    {
                        Blank blankcell = new Blank(this.StreamReader, bh.id, bh.length);
                        this.bsd.addBLANK(blankcell);
                    }
                    break;
                case MulBlank: 
                    {
                        MulBlank mulblank = new MulBlank(this.StreamReader, bh.id, bh.length);
                        this.bsd.addMULBLANK(mulblank);
                    }
                    break;
                case Formula: 
                    {
                        Formula formula = new Formula(this.StreamReader, bh.id, bh.length);
                        this.bsd.addFORMULA(formula);
                        TraceLogger.debugInternal(formula.toString());
                    }
                    break;
                case Array: 
                    {
                        ARRAY array = new ARRAY(this.StreamReader, bh.id, bh.length);
                        this.bsd.addARRAY(array);
                    }
                    break;
                case ShrFmla: 
                    {
                        ShrFmla shrfmla = new ShrFmla(this.StreamReader, bh.id, bh.length);
                        this.bsd.addSharedFormula(shrfmla);
                    }
                    break;
                case String: 
                    {
                        STRING formulaString = new STRING(this.StreamReader, bh.id, bh.length);
                        this.bsd.addFormulaString(formulaString.value);
                    }
                    break;
                case Row: 
                    {
                        Row row = new Row(this.StreamReader, bh.id, bh.length);
                        this.bsd.addRowData(row);
                    }
                    break;
                case ColInfo: 
                    {
                        ColInfo colinfo = new ColInfo(this.StreamReader, bh.id, bh.length);
                        this.bsd.addColData(colinfo);
                    }
                    break;
                case DefColWidth: 
                    {
                        DefColWidth defcolwidth = new DefColWidth(this.StreamReader, bh.id, bh.length);
                        this.bsd.addDefaultColWidth(defcolwidth.cchdefColWidth);
                    }
                    break;
                case DefaultRowHeight: 
                    {
                        DefaultRowHeight defrowheigth = new DefaultRowHeight(this.StreamReader, bh.id, bh.length);
                        this.bsd.addDefaultRowData(defrowheigth);
                    }
                    break;
                case LeftMargin: 
                    {
                        LeftMargin leftm = new LeftMargin(this.StreamReader, bh.id, bh.length);
                        this.bsd.leftMargin = leftm.value;
                    }
                    break;
                case RightMargin: 
                    {
                        RightMargin rightm = new RightMargin(this.StreamReader, bh.id, bh.length);
                        this.bsd.rightMargin = rightm.value;
                    }
                    break;
                case TopMargin: 
                    {
                        TopMargin topm = new TopMargin(this.StreamReader, bh.id, bh.length);
                        this.bsd.topMargin = topm.value;
                    }
                    break;
                case BottomMargin: 
                    {
                        BottomMargin bottomm = new BottomMargin(this.StreamReader, bh.id, bh.length);
                        this.bsd.bottomMargin = bottomm.value;
                    }
                    break;
                case Setup: 
                    {
                        Setup setup = new Setup(this.StreamReader, bh.id, bh.length);
                        this.bsd.addSetupData(setup);
                    }
                    break;
                case HLink: 
                    {
                        long oldStreamPos = Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position");
                        try
                        {
                            HLink hlink = new HLink(this.StreamReader, bh.id, bh.length);
                            bsd.addHyperLinkData(hlink);
                        }
                        catch (Exception ex)
                        {
                            this.StreamReader.getBaseStream().Seek(oldStreamPos, System.IO.SeekOrigin.Begin);
                            this.StreamReader.getBaseStream().Seek(bh.length, System.IO.SeekOrigin.Current);
                            TraceLogger.debug("Link parse error");
                            TraceLogger.error(ex.getStackTrace().toString());
                        }
                    
                    }
                    break;
                case MsoDrawing: 
                    {
                        // Record header has already been read. Reset position to record beginning.
                        Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position = Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position") - 2 *");
                        this.bsd.ObjectsSequence = new ObjectsSequence(this.StreamReader);
                    }
                    break;
                default: 
                    {
                        // this else statement is used to read BiffRecords which aren't implemented
                        byte[] buffer = new byte[bh.length];
                        buffer = this.StreamReader.ReadBytes(bh.length);
                    }
                    break;
            
            }
            latestbiff = bh;
        }
    }

    //}
    //catch (Exception ex)
    //{
    //    TraceLogger.Error(ex.Message);
    //    TraceLogger.Error(ex.StackTrace);
    //    TraceLogger.Debug(ex.ToString());
    //}
    /**
    * This method should read over every record which is inside a file in the worksheet file
    * For example this could be the diagram "file"
    * A diagram begins with the BOF Biffrecord and ends with the EOF record.
    */
    public void readUnkownFile() throws Exception {
        BiffHeader bh = new BiffHeader();
        do
        {
            //try
            //{
            bh.id = (RecordType)this.StreamReader.readUInt16();
            bh.length = this.StreamReader.readUInt16();
            this.StreamReader.ReadBytes(bh.length);
        }
        while (bh.id != RecordType.EOF);
    }

}


//}
//catch (Exception ex)
//{
//    TraceLogger.Error(ex.Message);
//    TraceLogger.Debug(ex.ToString());
//}