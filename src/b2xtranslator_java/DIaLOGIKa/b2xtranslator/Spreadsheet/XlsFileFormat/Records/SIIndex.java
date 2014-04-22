//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:47 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SIIndex  extends BiffRecord 
{
    public enum SeriesDataType
    {
        __dummyEnum__0,
        SeriesValues,
        CategoryLabels,
        BubbleSizes
    }
    /**
    * An unsigned integer that specifies the type of the data records contained by the Number records following it. 
    * MUST be a value from the following table:
    */
    public SeriesDataType numIndex = SeriesDataType.SeriesValues;
    public SIIndex(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.numIndex = (SeriesDataType)reader.readUInt16();
    }

}


