//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:00 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class VBAInfoContainer  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long objStgDataRef;
    public long hasMacros;
    public long vbaVersion;
    public VBAInfoContainer(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        objStgDataRef = System.BitConverter.ToUInt32(this.RawData, 8);
        hasMacros = System.BitConverter.ToUInt32(this.RawData, 12);
        vbaVersion = System.BitConverter.ToUInt32(this.RawData, 16);
    }

}


