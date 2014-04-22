//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CommandIdentifier;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class KeyMapEntry  extends ByteStructure 
{
    public enum ActionType
    {
        ktCid,
        ktChar,
        ktMask
    }
    /**
    * 
    */
    public short kcm1;
    /**
    * 
    */
    public short kcm2;
    /**
    * 
    */
    public ActionType kt = ActionType.ktCid;
    /**
    * 
    */
    public char paramChar;
    public CommandIdentifier paramCid;
    private static final int KME_LENGTH = 14;
    public KeyMapEntry(VirtualStreamReader reader) throws Exception {
        super(reader, KME_LENGTH);
        //ignore the first 4 bytes
        reader.readBytes(4);
        //Primary KCM
        this.kcm1 = reader.readInt16();
        //Secondary KCM
        this.kcm2 = reader.readInt16();
        //Key Action Type
        this.kt = (ActionType)reader.readInt16();
        //read the params
        switch(kt)
        {
            case ktCid: 
                this.paramCid = new CommandIdentifier(reader);
                break;
            case ktChar: 
                this.paramChar = (char)reader.readInt32();
                break;
            default: 
                reader.readBytes(4);
                break;
        
        }
    }

}


