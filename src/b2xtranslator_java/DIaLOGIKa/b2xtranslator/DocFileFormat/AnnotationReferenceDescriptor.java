//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class AnnotationReferenceDescriptor  extends ByteStructure 
{
    /**
    * The initials of the user who left the annotation.
    */
    public String UserInitials;
    /**
    * An index into the string table of comment author names.
    */
    public UInt16 AuthorIndex = new UInt16();
    /**
    * Identifies a bookmark.
    */
    public int BookmarkId;
    public AnnotationReferenceDescriptor(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        //read the user initials (LPXCharBuffer9)
        short cch = _reader.readInt16();
        byte[] chars = _reader.readBytes(18);
        this.UserInitials = Encoding.Unicode.GetString(chars, 0, cch * 2);
        this.AuthorIndex = _reader.readUInt16();
        //skip 4 bytes
        _reader.readBytes(4);
        this.BookmarkId = _reader.readInt32();
    }

}


