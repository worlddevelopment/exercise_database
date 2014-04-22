//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeBehaviorAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public boolean fAdditivePropertyUsed;
    public boolean fAttributeNamesPropertyUsed;
    public uint behaviorAdditive;
    public uint behaviorAccumulate;
    public uint behaviorTransform;
    public TimeBehaviorAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int flags = this.Reader.ReadInt32();
        fAdditivePropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fAttributeNamesPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 2);
        behaviorAdditive = this.Reader.ReadUInt32();
        behaviorAccumulate = this.Reader.ReadUInt32();
        behaviorTransform = this.Reader.ReadUInt32();
    }

}


