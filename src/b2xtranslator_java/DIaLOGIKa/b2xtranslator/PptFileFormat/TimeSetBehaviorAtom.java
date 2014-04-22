//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeAnimateBehaviorValueTypeEnum;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeSetBehaviorAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public boolean fToPropertyUsed;
    public boolean fValueTypePropertyUsed;
    public TimeAnimateBehaviorValueTypeEnum valueType = TimeAnimateBehaviorValueTypeEnum.String;
    public TimeSetBehaviorAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int flags = this.Reader.ReadInt32();
        fToPropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fValueTypePropertyUsed = Utils.bitmaskToBool(flags,0x1 << 1);
        valueType = (TimeAnimateBehaviorValueTypeEnum)this.Reader.ReadInt32();
    }

}


