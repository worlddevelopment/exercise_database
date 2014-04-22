//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AbstractCellContent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Dimensions;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesGroup;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SeriesDataSequence  extends BiffRecordSequence 
{
    public Dimensions Dimensions;
    public SeriesGroup[] SeriesGroups;
    public AbstractCellContent[][][] DataMatrix;
    public SeriesDataSequence(IStreamReader reader) throws Exception {
        super(reader);
        // SERIESDATA = Dimensions 3(SIIndex *(Number / BoolErr / Blank / Label))
        // Dimensions
        this.Dimensions = (Dimensions)BiffRecord.readRecord(reader);
        // 3(SIIndex *(Number / BoolErr / Blank / Label))
        this.SeriesGroups = new SeriesGroup[3];
        this.DataMatrix = new AbstractCellContent[3];
        for (int i = 0;i < 3;i++)
        {
            this.SeriesGroups[i] = new SeriesGroup(reader);
            // build matrix from series data
            this.DataMatrix[(UInt16)this.SeriesGroups[i].SIIndex.numIndex - 1] = new AbstractCellContent[this.Dimensions.colMac - this.Dimensions.colMic, this.Dimensions.rwMac - this.Dimensions.rwMic];
            for (AbstractCellContent cellContent : this.SeriesGroups[i].Data)
            {
                this.DataMatrix[(UInt16)this.SeriesGroups[i].SIIndex.numIndex - 1][cellContent.col, cellContent.rw] = cellContent;
            }
        }
    }

}


