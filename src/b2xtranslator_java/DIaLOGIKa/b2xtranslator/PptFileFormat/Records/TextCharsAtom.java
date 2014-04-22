//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.TextAtom;

public class TextCharsAtom  extends TextAtom 
{
    public static Encoding ENCODING = Encoding.Unicode;
    public TextCharsAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance, ENCODING);
    }

}


