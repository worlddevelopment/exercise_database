//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ContinueFrt12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RichTextStream;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.TextPropsStream;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class TextPropsSequence  extends BiffRecordSequence 
{
    public RichTextStream RichTextStream;
    public TextPropsStream TextPropsStream;
    public CSList<ContinueFrt12> ContinueFrt12s;
    public TextPropsSequence(IStreamReader reader) throws Exception {
        super(reader);
        // TEXTPROPS = (RichTextStream / TextPropsStream) *ContinueFrt12
        // (RichTextStream / TextPropsStream)
        if (BiffRecord.getNextRecordType(reader) == RecordType.TextPropsStream)
        {
            this.TextPropsStream = (TextPropsStream)BiffRecord.readRecord(reader);
        }
        else
        {
            this.RichTextStream = (RichTextStream)BiffRecord.readRecord(reader);
        } 
        //*ContinueFrt12
        this.ContinueFrt12s = new CSList<ContinueFrt12>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ContinueFrt12)
        {
            this.ContinueFrt12s.add((ContinueFrt12)BiffRecord.readRecord(reader));
        }
    }

}


