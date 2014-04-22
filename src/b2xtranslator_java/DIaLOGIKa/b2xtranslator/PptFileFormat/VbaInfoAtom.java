//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class VbaInfoAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long persistIdRef;
    public boolean fHasMacros;
    public long version;
    public VbaInfoAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.persistIdRef = _reader.ReadUInt32();
        this.fHasMacros = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool((byte)_reader.ReadUInt32());
        this.version = _reader.ReadUInt32();
    }

}


