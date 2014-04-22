//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class AnnotationReferenceDescriptorExtra  extends ByteStructure 
{
    public DateAndTime Date;
    public int CommentDepth;
    public int ParentOffset;
    public AnnotationReferenceDescriptorExtra(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        this.Date = new DateAndTime(_reader.readBytes(4));
        _reader.readBytes(2);
        this.CommentDepth = _reader.readInt32();
        this.ParentOffset = _reader.readInt32();
        if (length > 16)
        {
            int flag = _reader.readInt32();
        }
         
    }

}


