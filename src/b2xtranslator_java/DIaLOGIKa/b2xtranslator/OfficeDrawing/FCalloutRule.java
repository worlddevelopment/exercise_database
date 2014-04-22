//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class FCalloutRule  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long ruid;
    public long spid;
    public FCalloutRule(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        ruid = this.Reader.ReadUInt32();
        spid = this.Reader.ReadUInt32();
    }

}


