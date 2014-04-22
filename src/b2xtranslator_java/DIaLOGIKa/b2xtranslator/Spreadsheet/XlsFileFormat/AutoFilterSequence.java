//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:33 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AutoFilterGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AutoFilterInfo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SortData12Sequence;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class AutoFilterSequence  extends BiffRecordSequence 
{
    public AutoFilterInfo AutoFilterInfo;
    public CSList<AutoFilterGroup> AutoFilterGroups;
    public CSList<SortData12Sequence> SortData12Sequences;
    public AutoFilterSequence(IStreamReader reader) throws Exception {
        super(reader);
        // AUTOFILTER = AutoFilterInfo *(AutoFilter / (AutoFilter12 *ContinueFrt12)) *SORTDATA12
        // AutoFilterInfo
        this.AutoFilterInfo = (AutoFilterInfo)BiffRecord.readRecord(reader);
        // *(AutoFilter / (AutoFilter12 *ContinueFrt12))
        this.AutoFilterGroups = new CSList<AutoFilterGroup>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.AutoFilter || BiffRecord.getNextRecordType(reader) == RecordType.AutoFilter12)
        {
            this.AutoFilterGroups.add(new AutoFilterGroup(reader));
        }
        // *SORTDATA12
        this.SortData12Sequences = new CSList<SortData12Sequence>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.SortData)
        {
            this.SortData12Sequences.add(new SortData12Sequence(reader));
        }
    }

}


