//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:09 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class ToolbarDelta  extends ByteStructure 
{
    public static final int TBDelta_LENGTH = 18;
    public int dopr;
    public boolean fAtEnd;
    /**
    * Unsigned integer that specifies the zero-based index of the toolbar control
    * associated to this TBDelta in the toolbar at the time the toolbar delta was created. 
    * It is possible for more than one TBDelta structure, that affects the same toolbar,
    * to have the same value in the ibts field because this field specifies the index of
    * the toolbar control associated to the TBDelta in the toolbar at the time the toolbar delta was created.
    */
    public byte ibts;
    /**
    * Signed integer. Refer to the following table for the value that this field MUST have:
    */
    public int cidNext;
    /**
    * Structure of type Cid that specifies the Word�s command identifier for the
    * toolbar control associated to this TBDelta. 
    * Toolbar controls MUST only have Cid structures that have Cmt values equal to 0x0001 or 0x0003.
    */
    public int cid;
    /**
    * Unsigned integer that specifies the file offset in the Table Stream where the
    * toolbar control associated to this TBDelta is stored. 
    * Value MUST be 0x00000000 if fOnDisk is not equal to 1.
    */
    public int fc;
    /**
    * A bit that specifies if a toolbar control associated to this TBDelta has been written to the file. 
    * A value of 1 specifies that a toolbar control associated to this TBDelta has been written to the file. 
    * MUST be 1 if dopr equals 0 or 1.
    */
    public boolean fOnDisk;
    /// <summary>
    /// This field MUST only be used when the toolbar control associated to this TBDelta is a
    /// custom toolbar control that drops a custom menu toolbar. <br/>
    /// Unsigned integer that specifies the index to the Customization structure,
    /// contained in the rCustomizations array, that also contains the Customization
    /// that contains the customizationData array that contains this structure,
    /// that contains the CTB structure that specifies the custom menu toolbar dropped by
    /// the toolbar control associated to this TBDelta. MUST be 0 if the toolbar control
    /// associated to this TBDelta is not a custom toolbar control that drops a custom menu toolbar. <br/>
    /// Value MUST be greater or equal to 0 and SHOULD <256> be less than the value of the cCust field of
    /// the CTBWRAPPER structure that contains the rCustomizations array that contains the Customization
    /// structure that contains the customizationData array that contains this structure.
    /// </summary>
    public int iTB;
    /**
    * A bit that specifies if the toolbar control associated to this TBDelta does not drop a menu toolbar.
    * A value of 1 specifies that the toolbar control associated to this TBDelta does not drop a custom menu toolbar. 
    * Value MUST be 0 if the toolbar control associated to this TBDelta is not a custom toolbar
    * control that drops a custom menu toolbar or if dopr does not equal 1.
    */
    public boolean fDead;
    /**
    * Unsigned integer that specifies the size, in bytes, of the toolbar control associated to this TBDelta. 
    * This field MUST only be used when fOnDisk equals 1.
    * If fOnDisk equals 0, value MUST be 0x0000.
    */
    public UInt16 cbTBC = new UInt16();
    public ToolbarDelta(VirtualStreamReader reader) throws Exception {
        super(reader, TBDelta_LENGTH);
        byte flags1 = reader.readByte();
        this.dopr = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags1,0x03);
        this.fAtEnd = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags1,0x04);
        this.ibts = reader.readByte();
        this.cidNext = reader.readInt32();
        this.cid = reader.readInt32();
        this.fc = reader.readInt32();
        UInt16 flags2 = reader.readUInt16();
        this.fOnDisk = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags2,0x0001);
        this.iTB = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)flags2,0x3FFE);
        this.fDead = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)flags2,0x8000);
        this.cbTBC = reader.readUInt16();
    }

}


