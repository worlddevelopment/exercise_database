//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:52 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/**
* A structure that specifies a range of cells on the sheet.
*/
public class RefU   
{
    /**
    * A RwU that specifies the first row in the range. The value MUST be less than or equal to rwLast.
    */
    public UInt16 rwFirst = new UInt16();
    /**
    * A RwU that specifies the last row in the range.
    */
    public UInt16 rwLast = new UInt16();
    /**
    * A ColByteU that specifies the first column in the range. The value MUST be less than or equal to colLast.
    */
    public byte colFirst;
    /**
    * A ColByteU that specifies the last column in the range.
    */
    public byte colLast;
    public RefU(IStreamReader reader) throws Exception {
        this.rwFirst = reader.readUInt16();
        this.rwLast = reader.readUInt16();
        this.colFirst = reader.readByte();
        this.colLast = reader.readByte();
    }

}


