//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.JavaSupport.Unsupported;
import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ToolbarControl;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ToolbarCustomization;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class CustomToolbarWrapper  extends ByteStructure 
{
    /**
    * Signed integer that specifies the size in bytes of a TBDelta structure. 
    * MUST be 0x0012.
    */
    public short cbTBD;
    /**
    * Signed integer that specifies the number of elements in the rCustomizations array. 
    * MUST be greater than 0x0000.
    */
    public short cCust;
    /**
    * Signed integer that specifies the size, in bytes, of the rtbdc array.
    * MUST be greater or equal to 0x00000000.
    */
    public int cbDTBC;
    /**
    * 
    */
    public CSList<ToolbarControl> rTBDC;
    /**
    * 
    */
    public CSList<ToolbarCustomization> rCustomizations;
    public CustomToolbarWrapper(VirtualStreamReader reader) throws Exception {
        super(reader, ByteStructure.VARIABLE_LENGTH);
        long startPos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        //skip the first 7 bytes
        byte[] skipped = reader.readBytes(7);
        this.cbTBD = reader.readInt16();
        this.cCust = reader.readInt16();
        this.cbDTBC = reader.readInt32();
        this.rTBDC = new CSList<ToolbarControl>();
        int rTbdcEndPos = (int)(Unsupported.throwUnsupported("reader.getBaseStream().Position") + cbDTBC);
        while (Unsupported.throwUnsupported("reader.getBaseStream().Position") < rTbdcEndPos)
        {
            this.rTBDC.add(new ToolbarControl(reader));
        }
        reader.getBaseStream().Seek(rTbdcEndPos, System.IO.SeekOrigin.Begin);
        this.rCustomizations = new CSList<ToolbarCustomization>();
        for (int i = 0;i < cCust;i++)
        {
            this.rCustomizations.add(new ToolbarCustomization(reader));
        }
        long endPos = Unsupported.throwUnsupported("reader.getBaseStream().Position");
        //read the raw bytes
        reader.getBaseStream().Seek(startPos - 1, System.IO.SeekOrigin.Begin);
        this._rawBytes = reader.readBytes((int)(endPos - startPos + 1));
    }

}


