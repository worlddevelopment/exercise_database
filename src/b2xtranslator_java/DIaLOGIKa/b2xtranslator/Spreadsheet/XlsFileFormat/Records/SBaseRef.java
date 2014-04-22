//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:47 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class SBaseRef  extends BiffRecord 
{
    /**
    * A RwU that specifies the zero-based index of the first row in the range. 
    * MUST be less than or equal to rwLast.
    */
    public UInt16 rwFirst = new UInt16();
    /**
    * A RwU that specifies the zero-based index of the last row in the range. 
    * MUST be greater than or equal to rwFirst.
    */
    public UInt16 rwLast = new UInt16();
    /**
    * A ColU that specifies the zero-based index of the first column in the range.
    * MUST be less than or equal to colLast.
    * MUST be less than or equal to 0x00FF.
    */
    public UInt16 colFirst = new UInt16();
    /**
    * A ColU that specifies the zero-based index of the last column in the range. 
    * MUST be greater than or equal to colFirst.
    * MUST be less than or equal to 0x00FF.
    */
    public UInt16 colLast = new UInt16();
    public SBaseRef(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.rwFirst = reader.readUInt16();
        this.rwLast = reader.readUInt16();
        this.colFirst = reader.readUInt16();
        this.colLast = reader.readUInt16();
    }

}


