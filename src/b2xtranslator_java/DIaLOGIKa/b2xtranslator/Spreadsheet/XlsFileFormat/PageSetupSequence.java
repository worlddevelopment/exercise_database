//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.PageSetupSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BottomMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Continue;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Footer;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HCenter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LeftMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pls;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RightMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Setup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TopMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.VCenter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Header;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class PageSetupSequence  extends BiffRecordSequence implements IVisitable
{
    public Header Header;
    public Footer Footer;
    public HCenter HCenter;
    public VCenter VCenter;
    public LeftMargin LeftMargin;
    public RightMargin RightMargin;
    public TopMargin TopMargin;
    public BottomMargin BottomMargin;
    public Pls Pls;
    public CSList<Continue> Continues;
    public Setup Setup;
    public PageSetupSequence(IStreamReader reader) throws Exception {
        super(reader);
        //PAGESETUP = Header Footer HCenter VCenter [LeftMargin] [RightMargin] [TopMargin] [BottomMargin] [Pls *Continue] Setup
        // Header
        this.Header = (Header)BiffRecord.readRecord(reader);
        // Footer
        this.Footer = (Footer)BiffRecord.readRecord(reader);
        // HCenter
        this.HCenter = (HCenter)BiffRecord.readRecord(reader);
        // VCenter
        this.VCenter = (VCenter)BiffRecord.readRecord(reader);
        // [LeftMargin]
        if (BiffRecord.getNextRecordType(reader) == RecordType.LeftMargin)
        {
            this.LeftMargin = (LeftMargin)BiffRecord.readRecord(reader);
        }
         
        // [RightMargin]
        if (BiffRecord.getNextRecordType(reader) == RecordType.RightMargin)
        {
            this.RightMargin = (RightMargin)BiffRecord.readRecord(reader);
        }
         
        // [TopMargin]
        if (BiffRecord.getNextRecordType(reader) == RecordType.TopMargin)
        {
            this.TopMargin = (TopMargin)BiffRecord.readRecord(reader);
        }
         
        // [BottomMargin]
        if (BiffRecord.getNextRecordType(reader) == RecordType.BottomMargin)
        {
            this.BottomMargin = (BottomMargin)BiffRecord.readRecord(reader);
        }
         
        // [Pls *Continue]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Pls)
        {
            this.Pls = (Pls)BiffRecord.readRecord(reader);
            this.Continues = new CSList<Continue>();
            while (BiffRecord.getNextRecordType(reader) == RecordType.Continue)
            {
                this.Continues.add((Continue)BiffRecord.readRecord(reader));
            }
        }
         
        // Setup
        this.Setup = (Setup)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<PageSetupSequence>)mapping).apply(this);
    }

}


