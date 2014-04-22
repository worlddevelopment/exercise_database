//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat;

import b2xtranslator_java.DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.CommandTable;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.CustomToolbarWrapper;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.KeyMapEntry;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.MacroData;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.DocFileFormat.StringTable;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.star.auth.UnsupportedException;

public class CommandTable   implements IVisitable
{
    public StringTable CommandStringTable;
    public List<MacroData> MacroDatas;
    public HashMap<Integer,String> MacroNames;
    public List<KeyMapEntry> KeyMapEntries;
    public CustomToolbarWrapper CustomToolbars;
    private boolean breakWhile;
    public CommandTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
    	//set position in stream:
        tableStream.Seek(fib.fcCmds, System.IO.SeekOrigin.Begin); //TODO READ INTO BUFFER, THEN ACCESS LIKE ARRAY!!
        VirtualStreamReader reader = new VirtualStreamReader(tableStream);
        //byte[] bytes = reader.ReadBytes((int)fib.lcbCmds);
        this.MacroDatas = new ArrayList<MacroData>();
        this.KeyMapEntries = new ArrayList<KeyMapEntry>();
        this.MacroNames = new HashMap<Integer,String>();
        //skip the version
        reader.readByte();
        while (reader.getBaseStream().getByteCount() < (fib.fcCmds + fib.lcbCmds) && !breakWhile)
        {
            //parse the commandtable
            //read the type
            byte ch = reader.readByte();
            byte __dummyScrutVar0 = ch;
            if (__dummyScrutVar0.equals(0x1))
            {
                //it's a PlfMcd
                int iMacMcd = reader.readInt32();
                for (int i = 0;i < iMacMcd;i++)
                {
                    this.MacroDatas.add(new MacroData(reader));
                }
            }
            else if (__dummyScrutVar0.equals(0x2))
            {
                //it's a PlfAcd
                //skip the ACDs
                int iMacAcd = reader.readInt32();
                reader.readBytes(iMacAcd * 4);
            }
            else if (__dummyScrutVar0.equals(0x3))
            {
                //Keymap Entries
                int iMacKme = reader.readInt32();
                for (int i = 0;i < iMacKme;i++)
                {
                    this.KeyMapEntries.add(new KeyMapEntry(reader));
                }
            }
            else if (__dummyScrutVar0.equals(0x4))
            {
                //Keymap Entries
                int iMacKmeInvalid = reader.readInt32();
                for (int i = 0;i < iMacKmeInvalid;i++)
                {
                    this.KeyMapEntries.add(new KeyMapEntry(reader));
                }
            }
            else if (__dummyScrutVar0.equals(0x10))
            {
                //it's a TcgSttbf
                this.CommandStringTable = new StringTable(String.class,reader);
            }
            else if (__dummyScrutVar0.equals(0x11))
            {
                //it's a MacroNames table
                int iMacMn = reader.readInt16();
                for (int i = 0;i < iMacMn;i++)
                {
                    short ibst = reader.readInt16();
                    short cch = reader.readInt16();
                    this.MacroNames.put(ibst, Encoding.Unicode.GetString(reader.ReadBytes(cch * 2)));
                    //skip the terminating zero
                    reader.readBytes(2);
                }
            }
            else if (__dummyScrutVar0.equals(0x12))
            {
                //it's a CTBWRAPPER structure
                this.CustomToolbars = new CustomToolbarWrapper(reader);
            }
            else
            {
                breakWhile = true;
            }       
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<CommandTable>)mapping).apply(this);
    }

}


