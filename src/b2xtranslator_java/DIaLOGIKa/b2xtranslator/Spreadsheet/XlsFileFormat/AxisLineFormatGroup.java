//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AxisLine;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AxisLineFormatGroup   
{
    public AxisLine AxisLine;
    public LineFormat LineFormat;
    public AxisLineFormatGroup(IStreamReader reader) throws Exception {
        // *4(AxisLine LineFormat)
        this.AxisLine = (AxisLine)BiffRecord.readRecord(reader);
        this.LineFormat = (LineFormat)BiffRecord.readRecord(reader);
    }

}


