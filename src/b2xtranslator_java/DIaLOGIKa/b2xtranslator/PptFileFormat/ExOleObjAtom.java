//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class ExOleObjAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long persistIdRef;
    public int exObjId;
    public ExOleObjAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int drawAspect = this.Reader.ReadInt32();
        int type = this.Reader.ReadInt32();
        exObjId = this.Reader.ReadInt32();
        int subType = this.Reader.ReadInt32();
        persistIdRef = this.Reader.ReadUInt32();
        int unused = this.Reader.ReadInt32();
    }

}


