//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphRun9;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextSIException;

public class StyleTextProp9Atom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public CSList<ParagraphRun9> P9Runs = new CSList<ParagraphRun9>();
    public StyleTextProp9Atom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        while (Reader.BaseStream.Position < Reader.BaseStream.Length)
        {
            try
            {
                ParagraphRun9 pr = new ParagraphRun9();
                ParagraphMask pmask = (ParagraphMask)Reader.ReadUInt32();
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
                 
                P9Runs.add(pr);
                CharacterMask cmask = (CharacterMask)Reader.ReadUInt32();
                if ((cmask & CharacterMask.pp11ext) != 0)
                {
                    byte[] rest = Reader.ReadBytes(4);
                }
                 
                TextSIException si = new TextSIException(Reader);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
    }

}


//ignore