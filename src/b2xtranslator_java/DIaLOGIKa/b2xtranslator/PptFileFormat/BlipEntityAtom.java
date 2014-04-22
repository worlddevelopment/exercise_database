//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.BitmapBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.MetafilePictBlip;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;

public class BlipEntityAtom  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public BitmapBlip blip;
    public MetafilePictBlip mblip;
    public BlipEntityAtom(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        byte winBlipType = this.Reader.ReadByte();
        byte unused = this.Reader.ReadByte();
        DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.Reader);
        if (rec instanceof BitmapBlip)
        {
            blip = (BitmapBlip)rec;
        }
        else if (rec instanceof MetafilePictBlip)
        {
            mblip = (MetafilePictBlip)rec;
        }
          
    }

}


