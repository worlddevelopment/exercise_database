//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AutoFilterSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BottomMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Footer;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HCenter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HeaderFooter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HorizontalPageBreaks;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LeftMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pls;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.PrintSize;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RightMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Selection;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Setup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TopMargin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.UserSViewBegin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.UserSViewEnd;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.VCenter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.VerticalPageBreaks;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.Header;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CustomViewSequence  extends BiffRecordSequence 
{
    public UserSViewBegin UserSViewBegin;
    public CSList<Selection> Selections;
    public HorizontalPageBreaks HorizontalPageBreaks;
    public VerticalPageBreaks VerticalPageBreaks;
    public Header Header;
    public Footer Footer;
    public HCenter HCenter;
    public VCenter VCenter;
    public LeftMargin LeftMargin;
    public RightMargin RightMargin;
    public TopMargin TopMargin;
    public BottomMargin BottomMargin;
    public Pls Pls;
    public Setup Setup;
    public PrintSize PrintSize;
    public HeaderFooter HeaderFooter;
    public AutoFilterSequence AutoFilterSequence;
    public UserSViewEnd UserSViewEnd;
    public CustomViewSequence(IStreamReader reader) throws Exception {
        super(reader);
        // CUSTOMVIEW = UserSViewBegin *Selection [HorizontalPageBreaks] [VerticalPageBreaks] [Header]
        //    [Footer] [HCenter] [VCenter] [LeftMargin] [RightMargin] [TopMargin] [BottomMargin]
        //    [Pls] [Setup] [PrintSize] [HeaderFooter] [AUTOFILTER] UserSViewEnd
        // NOTE: UserSViewBegin and UserSViewEnd seem to be optional to!
        // UserSViewBegin
        if (BiffRecord.getNextRecordType(reader) == RecordType.UserSViewBegin)
        {
            this.UserSViewBegin = (UserSViewBegin)BiffRecord.readRecord(reader);
        }
         
        // *Selection
        this.Selections = new CSList<Selection>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Selection)
        {
            this.Selections.add((Selection)BiffRecord.readRecord(reader));
        }
        // [HorizontalPageBreaks]
        if (BiffRecord.getNextRecordType(reader) == RecordType.HorizontalPageBreaks)
        {
            this.HorizontalPageBreaks = (HorizontalPageBreaks)BiffRecord.readRecord(reader);
        }
         
        // [VerticalPageBreaks]
        if (BiffRecord.getNextRecordType(reader) == RecordType.VerticalPageBreaks)
        {
            this.VerticalPageBreaks = (VerticalPageBreaks)BiffRecord.readRecord(reader);
        }
         
        // [Header]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Header)
        {
            this.Header = (Header)BiffRecord.readRecord(reader);
        }
         
        // [Footer]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Footer)
        {
            this.Footer = (Footer)BiffRecord.readRecord(reader);
        }
         
        // [HCenter]
        if (BiffRecord.getNextRecordType(reader) == RecordType.HCenter)
        {
            this.HCenter = (HCenter)BiffRecord.readRecord(reader);
        }
         
        // [VCenter]
        if (BiffRecord.getNextRecordType(reader) == RecordType.VCenter)
        {
            this.VCenter = (VCenter)BiffRecord.readRecord(reader);
        }
         
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
         
        // [Pls]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Pls)
        {
            this.Pls = (Pls)BiffRecord.readRecord(reader);
        }
         
        // [Setup]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Setup)
        {
            this.Setup = (Setup)BiffRecord.readRecord(reader);
        }
         
        // [PrintSize]
        if (BiffRecord.getNextRecordType(reader) == RecordType.PrintSize)
        {
            this.PrintSize = (PrintSize)BiffRecord.readRecord(reader);
        }
         
        // [HeaderFooter]
        if (BiffRecord.getNextRecordType(reader) == RecordType.HeaderFooter)
        {
            this.HeaderFooter = (HeaderFooter)BiffRecord.readRecord(reader);
        }
         
        // [AUTOFILTER]
        if (BiffRecord.getNextRecordType(reader) == RecordType.AutoFilterInfo)
        {
            this.AutoFilterSequence = new AutoFilterSequence(reader);
        }
         
        // UserSViewEnd
        if (BiffRecord.getNextRecordType(reader) == RecordType.UserSViewEnd)
        {
            this.UserSViewEnd = (UserSViewEnd)BiffRecord.readRecord(reader);
        }
         
    }

}


