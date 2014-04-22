//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TimeSequenceDataAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public uint concurrency;
    public uint nextAction;
    public uint previousAction;
    public boolean fConcurrencyPropertyUsed;
    public boolean fNextActionPropertyUsed;
    public boolean fPreviousActionPropertyUsed;
    public TimeSequenceDataAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        concurrency = this.Reader.ReadUInt32();
        nextAction = this.Reader.ReadUInt32();
        previousAction = this.Reader.ReadUInt32();
        this.Reader.ReadInt32();
        //reserved
        int flags = this.Reader.ReadInt32();
        fConcurrencyPropertyUsed = Utils.bitmaskToBool(flags,0x1);
        fNextActionPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 1);
        fPreviousActionPropertyUsed = Utils.bitmaskToBool(flags,0x1 << 2);
    }

}


