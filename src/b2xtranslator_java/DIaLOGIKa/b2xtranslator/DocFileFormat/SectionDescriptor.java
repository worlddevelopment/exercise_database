//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:06 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class SectionDescriptor  extends ByteStructure 
{
    public short fn;
    public short fnMpr;
    public int fcMpr;
    /**
    * A signed integer that specifies the position in the WordDocument Stream where a Sepx structure is located.
    */
    public int fcSepx;
    private static final int SED_LENGTH = 12;
    public SectionDescriptor(VirtualStreamReader reader, int length) throws Exception {
        super(reader, length);
        this.fn = _reader.readInt16();
        this.fcSepx = _reader.readInt32();
        this.fnMpr = _reader.readInt16();
        this.fcMpr = _reader.readInt32();
    }

}


