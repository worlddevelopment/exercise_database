//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteStructure;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

public class MacroData  extends ByteStructure 
{
    /**
    * Unsigned integer that specifies the name of the macro.
    * Macro name is specified by MacroName.xstz of the MacroName entry in
    * the MacroNames such that MacroName.ibst equals ibst. 
    * MacroNames MUST contain such an entry.
    */
    public short ibst;
    /**
    * An unsigned integer that specifies the index into the
    * CommandStringTable (CommandTable.CommandStringTable)
    * where the macro�s name and arguments are specified.
    */
    public short ibstName;
    private static final int MCD_LENGTH = 24;
    public MacroData(VirtualStreamReader reader) throws Exception {
        super(reader, MCD_LENGTH);
        //first 2 bytes are reserved
        reader.readBytes(2);
        this.ibst = reader.readInt16();
        this.ibstName = reader.readInt16();
        //last 18 bytes are reserved
        reader.readBytes(18);
    }

}


