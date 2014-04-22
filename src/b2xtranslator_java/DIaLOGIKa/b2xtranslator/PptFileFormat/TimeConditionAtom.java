//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class TimeConditionAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public TriggerObjectEnum triggerObject = TriggerObjectEnum.None;
    public uint triggerEvent;
    public uint id;
    public int delay;
    public TimeConditionAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        triggerObject = (TriggerObjectEnum)this.Reader.ReadInt32();
        triggerEvent = this.Reader.ReadUInt32();
        id = this.Reader.ReadUInt32();
        delay = this.Reader.ReadInt32();
    }

    public enum TriggerObjectEnum
    {
        None,
        VisualElement,
        TimeNode,
        RuntimeNodeRef
    }
}


