//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class FConnectorRule  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long ruid;
    public long spidA;
    public long spidB;
    public long spidC;
    public long cptiA;
    public long cptiB;
    public FConnectorRule(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        ruid = this.Reader.ReadUInt32();
        spidA = this.Reader.ReadUInt32();
        spidB = this.Reader.ReadUInt32();
        spidC = this.Reader.ReadUInt32();
        cptiA = this.Reader.ReadUInt32();
        cptiB = this.Reader.ReadUInt32();
    }

}


