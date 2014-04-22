//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class ToolbarControlBitmap  extends ByteStructure 
{
    /**
    * Signed integer that specifies the count of total bytes, excluding this field,
    * in the TBCBitmap structure plus 10. Value is given by the following formula: 
    * cbDIB = sizeOf(biHeader) + sizeOf(colors) + sizeOf(bitmapData) + 10
    * MUST be greater or equal to 40, and MUST be less or equal to 65576.
    */
    public int cbDIB;
    public ToolbarControlBitmap(VirtualStreamReader reader) throws Exception {
        super(reader, ByteStructure.VARIABLE_LENGTH);
        this.cbDIB = reader.readInt32();
        //ToDo: Read TBCBitmap
        reader.readBytes(cbDIB - 10);
    }

}


