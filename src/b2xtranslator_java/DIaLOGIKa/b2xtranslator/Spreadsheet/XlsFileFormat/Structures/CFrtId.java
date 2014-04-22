//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:51 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

public class CFrtId   
{
    /**
    * An unsigned integer that specifies the first Future Record Type in the range.
    * MUST be less than or equal to rtLast.
    */
    public UInt16 rtFirst = new UInt16();
    /**
    * An unsigned integer that specifies the last Future Record Type in the range.
    */
    public UInt16 rtLast = new UInt16();
    public CFrtId(IStreamReader reader) throws Exception {
        this.rtFirst = reader.readUInt16();
        this.rtLast = reader.readUInt16();
    }

}


