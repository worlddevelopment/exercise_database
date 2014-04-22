//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ToolbarControl;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class CustomToolbar  extends ByteStructure 
{
    /**
    * Specifies the name of this custom toolbar.
    */
    public String name;
    /**
    * Signed integer that specifies the size, in bytes, of this structure excluding the name, cCtls, and rTBC fields.
    * Value is given by the following formula: cbTBData = sizeof(tb) + sizeof(rVisualData) + 12
    */
    public int cbTBData;
    /**
    * Structure of type TB, as specified in [MS-OSHARED], that contains toolbar data.
    */
    public byte[] tb;
    public byte[] rVisualData;
    /**
    * Signed integer that specifies the zero-based index of the Customization structure that
    * contains this structure in the rCustomizations array that contains the Customization
    * structure that contains this structure. 
    * Value MUST be greater or equal to 0x00000000 and MUST be less than the value of the
    * cCust field of the CTBWRAPPER structure that contains the rCustomizations array that
    * contains the Customization structure that contains this structure.
    */
    public int iWCTB;
    /**
    * Signed integer that specifies the number of toolbar controls in this toolbar.
    */
    public int cCtls;
    /**
    * Zero-based index array of TBC structures. 
    * The number of elements in this array MUST equal cCtls.
    */
    public CSList<ToolbarControl> rTBC;
    public CustomToolbar(VirtualStreamReader reader) throws Exception {
        super(reader, ByteStructure.VARIABLE_LENGTH);
        this.name = DIaLOGIKa.b2xtranslator.Tools.Utils.readXst(reader.getBaseStream());
        this.cbTBData = reader.readInt32();
        //cbTBData specifies the size of this structure excluding the name, cCtls, and rTBC fields
        //so it is the size of cbtb + tb + rVisualData + iWCTB + 4ignore bytes
        //so we can retrieve the size of tb:
        this.tb = reader.readBytes(this.cbTBData - 4 - 100 - 4 - 4);
        this.rVisualData = reader.readBytes(100);
        this.iWCTB = reader.readInt32();
        reader.readBytes(4);
        this.cCtls = reader.readInt32();
        this.rTBC = new CSList<ToolbarControl>();
        for (int i = 0;i < cCtls;i++)
        {
            this.rTBC.add(new ToolbarControl(reader));
        }
    }

}


