//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Records.OfficeRecordAttribute;

public class TextHeaderAtom  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public long TextType;
    public TextHeaderAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.TextType = this.Reader.ReadUInt32();
    }

}


