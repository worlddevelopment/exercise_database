//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLine;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CrtLineGroup   
{
    public CrtLine CrtLine;
    public LineFormat LineFormat;
    public CrtLineGroup(IStreamReader reader) throws Exception {
        // CrtLine LineFormat
        this.CrtLine = (CrtLine)BiffRecord.readRecord(reader);
        this.LineFormat = (LineFormat)BiffRecord.readRecord(reader);
    }

}


