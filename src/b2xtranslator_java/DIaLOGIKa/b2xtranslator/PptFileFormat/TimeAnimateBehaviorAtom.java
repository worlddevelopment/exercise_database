//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeAnimateBehaviorValueTypeEnum;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeAnimateBehaviorAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public uint calcMode;
    //0 discrete
    //1 linear
    //2 formula
    public boolean fByPropertyUsed;
    public boolean fFromPropertyUsed;
    public boolean fToPropertyUsed;
    public boolean fCalcModePropertyUsed;
    public boolean fAnimationValuesPropertyUsed;
    public boolean fValueTypePropertyUsed;
    public TimeAnimateBehaviorValueTypeEnum valueType = TimeAnimateBehaviorValueTypeEnum.String;
    public TimeAnimateBehaviorAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        calcMode = this.Reader.ReadUInt32();
        int flags = this.Reader.ReadInt32();
        fByPropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fFromPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 1);
        fToPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 2);
        fCalcModePropertyUsed = Utils.bitmaskToBool(flags,0x1 << 3);
        fAnimationValuesPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 4);
        fValueTypePropertyUsed = Utils.bitmaskToBool(flags,0x1 << 5);
        valueType = (TimeAnimateBehaviorValueTypeEnum)this.Reader.ReadInt32();
    }

}


