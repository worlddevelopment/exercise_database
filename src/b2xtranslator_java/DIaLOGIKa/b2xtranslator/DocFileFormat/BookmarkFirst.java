//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class BookmarkFirst  extends ByteStructure 
{
    /**
    * An unsigned integer that specifies a zero-based index into the PlcfBkl or PlcfBkld
    * that is paired with the PlcfBkf  or PlcfBkfd containing this FBKF. 
    * The entry found at said index specifies the location of the end of the bookmark associated with this FBKF. 
    * Ibkl MUST be unique for all FBKFs inside a given PlcfBkf or PlcfBkfd.
    */
    public short ibkl;
    /**
    * A BKC that specifies further information about the bookmark associated with this FBKF.
    */
    public short bkc;
    public BookmarkFirst(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        this.ibkl = reader.readInt16();
        this.bkc = reader.readInt16();
    }

}


