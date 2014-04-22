//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CustomToolbar;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ToolbarDelta;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class ToolbarCustomization  extends ByteStructure 
{
    /**
    * Signed integer that specifies if customizationData contains a CTB structure or an array of TBDelta structures.  
    * MUST be greater than or equal to 0x00000000.  
    * If value equals 0x00000000, customizationData MUST contain a CTB structure. 
    * If value does not equal 0x00000000, customizationData MUST contain an array of TBDelta structures
    * and the value of this field specifies the toolbar identifier of the toolbar affected by
    * the TBDelta structures contained in the array.
    */
    public int tbidForTBD;
    /**
    * Signed integer that specifies, if tbidForTBD is not equal to 0x00000000, the number of TBDelta
    * structures contained in the customizationData array. 
    * MUST be 0x0000 if tbidForTBD equals 0x00000000.
    */
    public short ctbds;
    /**
    * 
    */
    public CustomToolbar customToolbar;
    public CSList<ToolbarDelta> customToolbarDeltas;
    public ToolbarCustomization(VirtualStreamReader reader) throws Exception {
        super(reader, ByteStructure.VARIABLE_LENGTH);
        this.tbidForTBD = reader.readInt32();
        reader.readBytes(2);
        this.ctbds = reader.readInt16();
        //read the cutomization data
        if (this.tbidForTBD == 0)
        {
            this.customToolbar = new CustomToolbar(reader);
        }
        else
        {
            this.customToolbarDeltas = new CSList<ToolbarDelta>();
            for (int i = 0;i < this.ctbds;i++)
            {
                this.customToolbarDeltas.add(new ToolbarDelta(reader));
            }
        } 
    }

}


