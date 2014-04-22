//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeEffectBehaviorAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public boolean fTransitionPropertyUsed;
    public boolean fTypePropertyUsed;
    public boolean fProgressPropertyUsed;
    public boolean fRuntimeContextObsolete;
    public uint effectTransition;
    public TimeEffectBehaviorAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int flags = this.Reader.ReadInt32();
        fTransitionPropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fTypePropertyUsed = Utils.bitmaskToBool(flags,0x1 << 1);
        fProgressPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 2);
        fRuntimeContextObsolete = Utils.bitmaskToBool(flags,0x1 << 3);
        effectTransition = this.Reader.ReadUInt32();
    }

}


