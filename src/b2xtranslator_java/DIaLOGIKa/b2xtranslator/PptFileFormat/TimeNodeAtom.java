//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeNodeAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public uint restart;
    public TimeNodeTypeEnum type = TimeNodeTypeEnum.parallel;
    public uint fill;
    public int duration;
    public boolean fFillProperty;
    public boolean fRestartProperty;
    public boolean fGroupingTypeProperty;
    public boolean fDurationProperty;
    public TimeNodeAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Reader.ReadBytes(4);
        //reserved
        restart = this.Reader.ReadUInt32();
        type = (TimeNodeTypeEnum)this.Reader.ReadInt32();
        fill = this.Reader.ReadUInt32();
        this.Reader.ReadBytes(5);
        //reserved
        duration = this.Reader.ReadInt32();
        int flags = this.Reader.ReadInt32();
        fFillProperty = Utils.bitmaskToBool(flags,0x1);
        fRestartProperty = Utils.bitmaskToBool(flags,0x1 << 1);
        fGroupingTypeProperty = Utils.bitmaskToBool(flags,0x1 << 3);
        fDurationProperty = Utils.bitmaskToBool(flags,0x1 << 4);
        this.Reader.BaseStream.Position = this.Reader.BaseStream.Length;
    }

    public enum TimeNodeTypeEnum
    {
        parallel,
        sequential,
        behavior,
        media
    }
}


