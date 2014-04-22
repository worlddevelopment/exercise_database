//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class OriginalMainMasterId  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public long MainMasterId;
    public OriginalMainMasterId(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.MainMasterId = this.Reader.ReadUInt32();
    }

    public String toString(uint depth) throws Exception {
        return String.format(StringSupport.CSFmtStrToJFmtStr("{0}\n{1}MainMasterId = {2}"),super.toString(depth),indentationForDepth(depth + 1),this.MainMasterId);
    }

}


