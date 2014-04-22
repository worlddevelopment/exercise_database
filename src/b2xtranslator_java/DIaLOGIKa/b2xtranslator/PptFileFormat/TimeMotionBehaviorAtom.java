//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeMotionBehaviorAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public boolean fByPropertyUsed;
    public boolean fFromPropertyUsed;
    public boolean fToPropertyUsed;
    public boolean fOriginPropertyUsed;
    public boolean fPathPropertyUsed;
    public boolean fEditRotationPropertyUsed;
    public boolean fPointsTypesPropertyUsed;
    public float fXBy;
    public float fYBy;
    public float fXFrom;
    public float fYFrom;
    public float fXTo;
    public float fYTo;
    public uint behaviorOrigin;
    public TimeMotionBehaviorAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        int flags = this.Reader.ReadInt32();
        fByPropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fFromPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 1);
        fToPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 2);
        fOriginPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 3);
        fPathPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 4);
        // 1 bit reserved
        fEditRotationPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 6);
        fPointsTypesPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 7);
        fXBy = this.Reader.ReadSingle();
        fYBy = this.Reader.ReadSingle();
        fXFrom = this.Reader.ReadSingle();
        fYFrom = this.Reader.ReadSingle();
        fXTo = this.Reader.ReadSingle();
        fYTo = this.Reader.ReadSingle();
        behaviorOrigin = this.Reader.ReadUInt32();
    }

}


