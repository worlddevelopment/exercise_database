//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class TimeModifierAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public uint type;
    //0 repeat count
    //1 repeat duration
    //2 speed
    //3 accelerate
    //4 decelerate
    //5 automatic reverse
    public uint value;
    public TimeModifierAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        type = this.Reader.ReadUInt32();
        value = this.Reader.ReadUInt32();
    }

}


