//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxisLineFormatGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AreaFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ContinueFrt12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.FontX;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.GelFrame;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.IFmtRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TextPropsStream;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Tick;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ShapePropsSequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AxsSequence  extends BiffRecordSequence 
{
    public IFmtRecord IFmtRecord;
    public Tick Tick;
    public FontX FontX;
    public CSList<AxisLineFormatGroup> AxisLineFormatGroups;
    public AreaFormat AreaFormat;
    public GelFrame GelFrame;
    public CSList<ShapePropsSequence> ShapePropsSequences;
    public TextPropsStream TextPropsStream;
    public CSList<ContinueFrt12> ContinueFrt12s;
    public AxsSequence(IStreamReader reader) throws Exception {
        super(reader);
        //AXS = [IFmtRecord] [Tick] [FontX] *4(AxisLine LineFormat) [AreaFormat] [GELFRAME] *4SHAPEPROPS [TextPropsStream *ContinueFrt12]
        //[IFmtRecord]
        if (BiffRecord.getNextRecordType(reader) == RecordType.IFmtRecord)
        {
            this.IFmtRecord = (IFmtRecord)BiffRecord.readRecord(reader);
        }
         
        //[Tick]
        if (BiffRecord.getNextRecordType(reader) == RecordType.Tick)
        {
            this.Tick = (Tick)BiffRecord.readRecord(reader);
        }
         
        //[FontX]
        if (BiffRecord.getNextRecordType(reader) == RecordType.FontX)
        {
            this.FontX = (FontX)BiffRecord.readRecord(reader);
        }
         
        //*4(AxisLine LineFormat)
        AxisLineFormatGroups = new CSList<AxisLineFormatGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.AxisLine)
        {
            this.AxisLineFormatGroups.add(new AxisLineFormatGroup(reader));
        }
        //[AreaFormat]
        if (BiffRecord.getNextRecordType(reader) == RecordType.AreaFormat)
        {
            this.AreaFormat = (AreaFormat)BiffRecord.readRecord(reader);
        }
         
        //[GELFRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.GelFrame)
        {
            this.GelFrame = (GelFrame)BiffRecord.readRecord(reader);
        }
         
        //*4SHAPEPROPS
        this.ShapePropsSequences = new CSList<ShapePropsSequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ShapePropsStream)
        {
            this.ShapePropsSequences.add(new ShapePropsSequence(reader));
        }
        //[TextPropsStream *ContinueFrt12]
        if (BiffRecord.getNextRecordType(reader) == RecordType.TextPropsStream)
        {
            this.TextPropsStream = (TextPropsStream)BiffRecord.readRecord(reader);
            while (BiffRecord.getNextRecordType(reader) == RecordType.ContinueFrt12)
            {
                this.ContinueFrt12s.add((ContinueFrt12)BiffRecord.readRecord(reader));
            }
        }
         
    }

}


