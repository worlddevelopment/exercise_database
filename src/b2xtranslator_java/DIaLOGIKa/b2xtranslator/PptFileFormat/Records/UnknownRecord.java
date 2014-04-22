//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:57 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat.Records;

import CS2JNet.System.LCC.Disposable;

public class UnknownRecord  extends DIaLOGIKa.b2xtranslator.PptFileFormat.Records.Record 
{
    public UnknownRecord(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.Reader.ReadBytes((int)size);
    }

}


