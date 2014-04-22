//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AbstractCellContent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SIIndex;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SeriesGroup   
{
    public SIIndex SIIndex;
    public CSList<AbstractCellContent> Data;
    public SeriesGroup(IStreamReader reader) throws Exception {
        // SIIndex *(Number / BoolErr / Blank / Label)
        // SIIndex
        this.SIIndex = (SIIndex)BiffRecord.readRecord(reader);
        // *(Number / BoolErr / Blank / Label)
        this.Data = new CSList<AbstractCellContent>();
        while (BiffRecord.getNextRecordType(reader) == RecordType.Number || BiffRecord.getNextRecordType(reader) == RecordType.BoolErr || BiffRecord.getNextRecordType(reader) == RecordType.Blank || BiffRecord.getNextRecordType(reader) == RecordType.Label)
        {
            this.Data.add((AbstractCellContent)BiffRecord.readRecord(reader));
        }
    }

}


