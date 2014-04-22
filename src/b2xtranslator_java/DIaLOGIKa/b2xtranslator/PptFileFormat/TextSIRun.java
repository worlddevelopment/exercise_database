//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.PptFileFormat.TextSIException;

public class TextSIRun   
{
    public uint count;
    public TextSIException si;
    public TextSIRun(BinaryReader reader) throws Exception {
        count = reader.ReadUInt32();
        si = new TextSIException(reader);
    }

}


