//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TextSIException   
{
    private uint flags;
    public boolean spell;
    public boolean lang;
    public boolean altLang;
    public boolean fPp10ext;
    public boolean fBidi;
    public boolean smartTag;
    public UInt16 spellInfo = new UInt16();
    public UInt16 lid = new UInt16();
    public UInt16 bidi = new UInt16();
    public UInt16 altLid = new UInt16();
    public TextSIException(BinaryReader reader) throws Exception {
        flags = reader.ReadUInt32();
        spell = Utils.BitmaskToBool(flags, 0x1);
        lang = Utils.BitmaskToBool(flags, 0x1 << 1);
        altLang = Utils.BitmaskToBool(flags, 0x1 << 2);
        fPp10ext = Utils.BitmaskToBool(flags, 0x1 << 5);
        fBidi = Utils.BitmaskToBool(flags, 0x1 << 6);
        smartTag = Utils.BitmaskToBool(flags, 0x1 << 9);
        if (spell)
            spellInfo = reader.ReadUInt16();
         
        if (lang)
            lid = reader.ReadUInt16();
         
        if (altLang)
            altLid = reader.ReadUInt16();
         
        if (fBidi)
            bidi = reader.ReadUInt16();
         
        long dummy;
        if (fPp10ext)
            dummy = reader.ReadUInt32();
         
        byte[] smartTags;
        if (smartTag)
            smartTags = reader.ReadBytes((int)(reader.BaseStream.Length - reader.BaseStream.Position));
         
    }

}


