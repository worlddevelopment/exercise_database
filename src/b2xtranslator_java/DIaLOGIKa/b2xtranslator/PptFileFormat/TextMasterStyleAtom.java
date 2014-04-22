//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextStyleAtom;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

public class TextMasterStyleAtom  extends TextStyleAtom 
{
    public UInt16 IndentLevelCount = new UInt16();
    public byte[] Bytes;
    public TextMasterStyleAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Bytes = this.Reader.ReadBytes((int)this.BodySize);
        this.Reader.BaseStream.Position = 0;
        this.IndentLevelCount = Reader.ReadUInt16();
        for (int i = 0;i < this.IndentLevelCount;i++)
        {
            long pos = this.Reader.BaseStream.Position;
            if ((this.Instance >= 5))
            {
                // & (this.Instance < this.IndentLevelCount))
                UInt16 level = Reader.ReadUInt16();
            }
             
            this.PRuns.add(new ParagraphRun(this.Reader,true));
            TraceLogger.debugInternal("Read paragraph run. Before pos = {0}, after pos = {1} of {2}: {3}",pos,this.Reader.BaseStream.Position,this.Reader.BaseStream.Length,this.PRuns.get(i).toString());
            pos = this.Reader.BaseStream.Position;
            this.CRuns.add(new CharacterRun(this.Reader));
            TraceLogger.debugInternal("Read character run. Before pos = {0}, after pos = {1} of {2}: {3}",pos,this.Reader.BaseStream.Position,this.Reader.BaseStream.Length,this.CRuns.get(i).toString());
        }
        /**
        * / XXX: I'm not sure why but in some cases there is trailing garbage -- flgr
        */
        if (this.Reader.BaseStream.Position != this.Reader.BaseStream.Length)
        {
            this.Reader.BaseStream.Position = this.Reader.BaseStream.Length;
        }
         
    }

    public ParagraphRun paragraphRunForIndentLevel(int level) throws Exception {
        return this.PRuns.get(level);
    }

    public CharacterRun characterRunForIndentLevel(int level) throws Exception {
        return this.CRuns.get(level);
    }

}


