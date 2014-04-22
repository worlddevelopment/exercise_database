//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun9;

public class TextMasterStyle9Atom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public CSList<ParagraphRun9> pruns = new CSList<ParagraphRun9>();
    public TextMasterStyle9Atom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        uint level = this.Reader.ReadUInt16();
        for (int i = 0;i < level;i++)
        {
            ParagraphMask pmask = (ParagraphMask)Reader.ReadUInt32();
            ParagraphRun9 pr = new ParagraphRun9();
            pr.mask = pmask;
            if ((pmask & ParagraphMask.BulletBlip) != 0)
            {
                int bulletblipref = Reader.ReadInt16();
                pr.bulletblipref = bulletblipref;
            }
             
            if ((pmask & ParagraphMask.BulletHasScheme) != 0)
            {
                pr.fBulletHasAutoNumber = Reader.ReadInt16();
            }
             
            if ((pmask & ParagraphMask.BulletScheme) != 0)
            {
                pr.bulletAutoNumberScheme = Reader.ReadInt32();
            }
             
            this.pruns.add(pr);
            CharacterMask cmask = (CharacterMask)Reader.ReadUInt32();
            if ((cmask & CharacterMask.pp11ext) != 0)
            {
                byte[] rest = Reader.ReadBytes(4);
            }
             
        }
    }

}


