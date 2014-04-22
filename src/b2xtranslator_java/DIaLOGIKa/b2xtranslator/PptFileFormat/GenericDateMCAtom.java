//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class GenericDateMCAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public int Position;
    public GenericDateMCAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        Position = Reader.ReadInt32();
    }

}


