//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class CommandIdentifier  extends ByteStructure 
{
    public enum CidType
    {
        __dummyEnum__0,
        cmtFci,
        cmtMacro,
        cmtAllocated,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        cmtNil
    }
    public static final int CID_LENGTH = 4;
    public short ibstMacro;
    public CommandIdentifier(VirtualStreamReader reader) throws Exception {
        super(reader, CID_LENGTH);
        byte[] bytes = reader.readBytes(4);
        CidType type = CidType.values()[DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToInt((int)bytes[0],0x07)];
        switch(type)
        {
            case cmtFci: 
                break;
            case cmtMacro: 
                this.ibstMacro = System.BitConverter.ToInt16(bytes, 2);
                break;
            case cmtAllocated: 
                break;
            case cmtNil: 
                break;
            default: 
                break;
        
        }
    }

}


