//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import docx2tex.Library.ListTypeEnum;

public class ListInfo   
{
    public ListInfo() {
    }

    public ListInfo(int numId, int level, ListTypeEnum style) throws Exception {
        this.NumId = numId;
        this.Level = level;
        this.Style = style;
    }

    public int NumId;
    public int Level;
    public ListTypeEnum Style = ListTypeEnum.None;
    public uint getHashCode() throws Exception {
        return (uint)NumId * 65536 + (uint)Level;
    }

}


