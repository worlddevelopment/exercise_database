//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TabStops;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class DefaultRulerAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public uint flags;
    public boolean fDefaultTabSize;
    public boolean fCLevels;
    public boolean fTabStops;
    public boolean fLeftMargin1;
    public boolean fLeftMargin2;
    public boolean fLeftMargin3;
    public boolean fLeftMargin4;
    public boolean fLeftMargin5;
    public boolean fIndent1;
    public boolean fIndent2;
    public boolean fIndent3;
    public boolean fIndent4;
    public boolean fIndent5;
    public int cLevels;
    public int defaultTabSize;
    public TabStops tabs;
    public int leftMargin1;
    public int leftMargin2;
    public int leftMargin3;
    public int leftMargin4;
    public int leftMargin5;
    public int indent1;
    public int indent2;
    public int indent3;
    public int indent4;
    public int indent5;
    public DefaultRulerAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        flags = Reader.ReadUInt32();
        fDefaultTabSize = Utils.BitmaskToBool(flags, 0x1);
        fCLevels = Utils.BitmaskToBool(flags, 0x1 << 1);
        fTabStops = Utils.BitmaskToBool(flags, 0x1 << 2);
        fLeftMargin1 = Utils.BitmaskToBool(flags, 0x1 << 3);
        fLeftMargin2 = Utils.BitmaskToBool(flags, 0x1 << 4);
        fLeftMargin3 = Utils.BitmaskToBool(flags, 0x1 << 5);
        fLeftMargin4 = Utils.BitmaskToBool(flags, 0x1 << 6);
        fLeftMargin5 = Utils.BitmaskToBool(flags, 0x1 << 7);
        fIndent1 = Utils.BitmaskToBool(flags, 0x1 << 8);
        fIndent2 = Utils.BitmaskToBool(flags, 0x1 << 9);
        fIndent3 = Utils.BitmaskToBool(flags, 0x1 << 10);
        fIndent4 = Utils.BitmaskToBool(flags, 0x1 << 11);
        fIndent5 = Utils.BitmaskToBool(flags, 0x1 << 12);
        if (fCLevels)
            cLevels = Reader.ReadInt16();
         
        if (fDefaultTabSize)
            defaultTabSize = Reader.ReadInt16();
         
        if (fTabStops)
            tabs = new TabStops(Reader);
         
        if (fLeftMargin1)
            leftMargin1 = Reader.ReadInt16();
         
        if (fIndent1)
            indent1 = Reader.ReadInt16();
         
        if (fLeftMargin2)
            leftMargin2 = Reader.ReadInt16();
         
        if (fIndent2)
            indent3 = Reader.ReadInt16();
         
        if (fLeftMargin3)
            leftMargin3 = Reader.ReadInt16();
         
        if (fIndent3)
            indent3 = Reader.ReadInt16();
         
        if (fLeftMargin4)
            leftMargin4 = Reader.ReadInt16();
         
        if (fIndent4)
            indent4 = Reader.ReadInt16();
         
        if (fLeftMargin5)
            leftMargin5 = Reader.ReadInt16();
         
        if (fIndent5)
            indent5 = Reader.ReadInt16();
         
    }

}


