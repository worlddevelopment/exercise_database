//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class ExOleEmbedAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public ExOleEmbedAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int exColorFollow = this.Reader.ReadInt32();
        byte fCantLockServer = this.Reader.ReadByte();
        byte fNoSizeToServer = this.Reader.ReadByte();
        byte fIsTable = this.Reader.ReadByte();
    }

}


