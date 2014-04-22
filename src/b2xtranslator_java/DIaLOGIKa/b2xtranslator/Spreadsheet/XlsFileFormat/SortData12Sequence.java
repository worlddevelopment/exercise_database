//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ContinueFrt12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SortData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SortData12Sequence  extends BiffRecordSequence 
{
    public SortData SortData;
    public CSList<ContinueFrt12> ContinueFrt12s;
    public SortData12Sequence(IStreamReader reader) throws Exception {
        super(reader);
        // SORTDATA12 = SortData *ContinueFrt12
        // SortData
        this.SortData = (SortData)BiffRecord.readRecord(reader);
        // *ContinueFrt12
        this.ContinueFrt12s = new CSList<ContinueFrt12>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.ContinueFrt12)
        {
            this.ContinueFrt12s.add((ContinueFrt12)BiffRecord.readRecord(reader));
        }
    }

}


