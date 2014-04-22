//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TabStop;

public class TabStops   
{
    public uint count;
    public CSList<TabStop> tabStops = new CSList<TabStop>();
    public TabStops(BinaryReader reader) throws Exception {
        count = reader.ReadUInt16();
        for (int i = 0;i < count;i++)
        {
            tabStops.add(new TabStop(reader));
        }
    }

}


