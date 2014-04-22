//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:37 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffHeader;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SSTData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.WorkBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Extractor;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExtractorException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BoundSheet8;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BoundSheet8.SheetType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CodeName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CRN;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ExternName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ExternSheet;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Font;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Format;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Lbl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Palette;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SST;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Style;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SupBook;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.XCT;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.XF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkbookExtractor;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkSheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorksheetExtractor;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* Extracts the workbook stream !!
*/
public class WorkbookExtractor  extends Extractor implements IVisitable
{
    public String buffer;
    public long oldOffset;
    public CSList<BoundSheet8> boundsheets;
    public CSList<ExternSheet> externSheets;
    public CSList<SupBook> supBooks;
    public CSList<XCT> XCTList;
    public CSList<CRN> CRNList;
    public WorkBookData workBookData;
    /**
    * Ctor
    * 
    *  @param reader Reader
    */
    public WorkbookExtractor(VirtualStreamReader reader, WorkBookData workBookData) throws Exception {
        super(reader);
        this.boundsheets = new CSList<BoundSheet8>();
        this.supBooks = new CSList<SupBook>();
        this.externSheets = new CSList<ExternSheet>();
        this.XCTList = new CSList<XCT>();
        this.CRNList = new CSList<CRN>();
        this.workBookData = workBookData;
        this.oldOffset = 0;
        this.extractData();
    }

    /**
    * Extracts the data from the stream
    */
    public void extractData() throws Exception {
        BiffHeader bh = new BiffHeader();
        while (Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position") < this.StreamReader.getBaseStream().Length)
        {
            //try
            //{
            bh.id = (RecordType)this.StreamReader.readUInt16();
            bh.length = this.StreamReader.readUInt16();
            // Debugging output
            TraceLogger.debugInternal("BIFF {0}\t{1}\t",bh.id,bh.length);
            switch(bh.id)
            {
                case BoundSheet8: 
                    {
                        // Extracts the Boundsheet data
                        BoundSheet8 bs = new BoundSheet8(this.StreamReader, bh.id, bh.length);
                        TraceLogger.debugInternal(bs.toString());
                        SheetData sheetData = null;
                        switch(bs.dt)
                        {
                            case Worksheet: 
                                sheetData = new WorkSheetData();
                                this.oldOffset = Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position");
                                this.StreamReader.getBaseStream().Seek(bs.lbPlyPos, SeekOrigin.Begin);
                                WorksheetExtractor se = new WorksheetExtractor(this.StreamReader,sheetData instanceof WorkSheetData ? (WorkSheetData)sheetData : (WorkSheetData)null);
                                this.StreamReader.getBaseStream().Seek(oldOffset, SeekOrigin.Begin);
                                break;
                            case Chartsheet: 
                                ChartSheetData chartSheetData = new ChartSheetData();
                                this.oldOffset = Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position");
                                this.StreamReader.getBaseStream().Seek(bs.lbPlyPos, SeekOrigin.Begin);
                                chartSheetData.ChartSheetSequence = new ChartSheetSequence(this.StreamReader);
                                this.StreamReader.getBaseStream().Seek(oldOffset, SeekOrigin.Begin);
                                sheetData = chartSheetData;
                                break;
                            default: 
                                TraceLogger.info("Unsupported sheet type: {0}",bs.dt);
                                break;
                        
                        }
                        if (sheetData != null)
                        {
                            // add general sheet info
                            sheetData.boundsheetRecord = bs;
                        }
                         
                        this.workBookData.addBoundSheetData(sheetData);
                    }
                    break;
                case Template: 
                    {
                        this.workBookData.setTemplate(true);
                    }
                    break;
                case SST: 
                    {
                        /* reads the shared string table biff record and following continue records 
                                                         * creates an array of bytes and then puts that into a memory stream 
                                                         * this all is used to create a longer biffrecord then 8224 bytes. If theres a string 
                                                         * beginning in the SST that is then longer then the 8224 bytes, it continues in the 
                                                         * CONTINUE BiffRecord, so the parser has to read over the SST border. 
                                                         * The problem here is, that the parser has to overread the continue biff record header 
                                                        */
                        SST sst;
                        UInt16 length = bh.length;
                        // save the old offset from this record begin
                        this.oldOffset = Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position");
                        // create a list of bytearrays to store the following continue records
                        // List<byte[]> byteArrayList = new List<byte[]>();
                        byte[] buffer = new byte[length];
                        LinkedList<VirtualStreamReader> vsrList = new LinkedList<VirtualStreamReader>();
                        buffer = this.StreamReader.readBytes((int)length);
                        // byteArrayList.Add(buffer);
                        // create a new memory stream and a new virtualstreamreader
                        MemoryStream bufferstream = new MemoryStream(buffer);
                        VirtualStreamReader binreader = new VirtualStreamReader(bufferstream);
                        BiffHeader bh2 = new BiffHeader();
                        bh2.id = (RecordType)this.StreamReader.readUInt16();
                        while (bh2.id == RecordType.Continue)
                        {
                            bh2.length = (UInt16)(this.StreamReader.readUInt16());
                            buffer = new byte[bh2.length];
                            // create a buffer with the bytes from the records and put that array into the
                            // list
                            buffer = this.StreamReader.readBytes((int)bh2.length);
                            // byteArrayList.Add(buffer);
                            // create for each continue record a new streamreader !!
                            MemoryStream contbufferstream = new MemoryStream(buffer);
                            VirtualStreamReader contreader = new VirtualStreamReader(contbufferstream);
                            vsrList.AddLast(contreader);
                            // take next Biffrecord ID
                            bh2.id = (RecordType)this.StreamReader.readUInt16();
                        }
                        // set the old position of the stream
                        Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position = this.oldOffset");
                        sst = new SST(binreader, bh.id, length, vsrList);
                        Unsupported.throwUnsupported("this.StreamReader.getBaseStream().Position = this.oldOffset + bh.length");
                        this.workBookData.setSstData(new SSTData(sst));
                    }
                    break;
                case EOF: 
                    {
                        // Reads the end of the internal file !!!
                        this.StreamReader.getBaseStream().Seek(0, SeekOrigin.End);
                    }
                    break;
                case ExternSheet: 
                    {
                        ExternSheet extsheet = new ExternSheet(this.StreamReader, bh.id, bh.length);
                        this.externSheets.add(extsheet);
                        this.workBookData.addExternSheetData(extsheet);
                    }
                    break;
                case SupBook: 
                    {
                        SupBook supbook = new SupBook(this.StreamReader, bh.id, bh.length);
                        this.supBooks.add(supbook);
                        this.workBookData.addSupBookData(supbook);
                    }
                    break;
                case XCT: 
                    {
                        XCT xct = new XCT(this.StreamReader, bh.id, bh.length);
                        this.XCTList.add(xct);
                        this.workBookData.addXCT(xct);
                    }
                    break;
                case CRN: 
                    {
                        CRN crn = new CRN(this.StreamReader, bh.id, bh.length);
                        this.CRNList.add(crn);
                        this.workBookData.addCRN(crn);
                    }
                    break;
                case ExternName: 
                    {
                        ExternName externname = new ExternName(this.StreamReader, bh.id, bh.length);
                        this.workBookData.addEXTERNNAME(externname);
                    }
                    break;
                case Format: 
                    {
                        Format format = new Format(this.StreamReader, bh.id, bh.length);
                        this.workBookData.styleData.addFormatValue(format);
                    }
                    break;
                case XF: 
                    {
                        XF xf = new XF(this.StreamReader, bh.id, bh.length);
                        this.workBookData.styleData.addXFDataValue(xf);
                    }
                    break;
                case Style: 
                    {
                        Style style = new Style(this.StreamReader, bh.id, bh.length);
                        this.workBookData.styleData.addStyleValue(style);
                    }
                    break;
                case Font: 
                    {
                        Font font = new Font(this.StreamReader, bh.id, bh.length);
                        this.workBookData.styleData.addFontData(font);
                    }
                    break;
                case NAME: 
                case Lbl: 
                    {
                        Lbl name = new Lbl(this.StreamReader, bh.id, bh.length);
                        this.workBookData.addDefinedName(name);
                    }
                    break;
                case BOF: 
                    {
                        this.workBookData.BOF = new BOF(this.StreamReader, bh.id, bh.length);
                    }
                    break;
                case CodeName: 
                    {
                        this.workBookData.CodeName = new CodeName(this.StreamReader, bh.id, bh.length);
                    }
                    break;
                case FilePass: 
                    {
                        throw new ExtractorException(ExtractorException.FILEENCRYPTED);
                    }
                    break;
                case Palette: 
                    {
                        Palette palette = new Palette(this.StreamReader, bh.id, bh.length);
                        workBookData.styleData.setColorList(palette.rgbColorList);
                    }
                    break;
                default: 
                    {
                        // this else statement is used to read BiffRecords which aren't implemented
                        byte[] buffer = new byte[bh.length];
                        buffer = this.StreamReader.ReadBytes(bh.length);
                        TraceLogger.debug("Unknown record found. ID {0}",bh.id);
                    }
                    break;
            
            }
        }
    }

    //}
    //catch (Exception ex)
    //{
    //    TraceLogger.Error(ex.Message);
    //    TraceLogger.Debug(ex.ToString());
    //}
    /**
    * A normal overload ToString Method
    * 
    *  @return
    */
    public String toString() {
        try
        {
            return "Workbook";
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<WorkbookExtractor>)mapping).apply(this);
    }

}


