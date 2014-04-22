//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AutoFilter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AutoFilter12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ContinueFrt12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AutoFilterGroup   
{
    public AutoFilter AutoFilter;
    public AutoFilter12 AutoFilter12;
    public CSList<ContinueFrt12> ContinueFrt12s;
    public AutoFilterGroup(IStreamReader reader) throws Exception {
        if (BiffRecord.getNextRecordType(reader) == RecordType.AutoFilter)
        {
            this.AutoFilter = (AutoFilter)BiffRecord.readRecord(reader);
        }
        else
        {
            this.AutoFilter12 = (AutoFilter12)BiffRecord.readRecord(reader);
            this.ContinueFrt12s = new CSList<ContinueFrt12>();
            while (BiffRecord.getNextRecordType(reader) == RecordType.ContinueFrt12)
            {
                this.ContinueFrt12s.add((ContinueFrt12)BiffRecord.readRecord(reader));
            }
        } 
    }

}


