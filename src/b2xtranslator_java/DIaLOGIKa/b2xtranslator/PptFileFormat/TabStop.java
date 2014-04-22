//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.PptFileFormat.TextTabTypeEnum;

public class TabStop   
{
    public uint position;
    public TextTabTypeEnum TabType = TextTabTypeEnum.TABLeft;
    public TabStop(BinaryReader reader) throws Exception {
        position = reader.ReadUInt16();
        TabType = (TextTabTypeEnum)reader.ReadInt16();
    }

}


