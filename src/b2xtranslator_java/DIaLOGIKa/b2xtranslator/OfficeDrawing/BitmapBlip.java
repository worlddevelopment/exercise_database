//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Record;

public class BitmapBlip  extends Record 
{
    /**
    * The secondary, or data, UID - should always be set.
    */
    public byte[] m_rgbUid;
    /**
    * The primary UID - this defaults to 0, in which case the primary ID is that of the internal data. 
    * NOTE!: The primary UID is only saved to disk if (blip_instance ^ blip_signature == 1). 
    * Blip_instance is MSOFBH.finst and blip_signature is one of the values defined in MSOBI
    */
    public byte[] m_rgbUidPrimary;
    /**
    * 
    */
    public byte m_bTag;
    /**
    * Raster bits of the blip
    */
    public byte[] m_pvBits;
    public BitmapBlip(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        this.m_rgbUid = this.Reader.ReadBytes(16);
        if (this.Instance == 0x6E1)
        {
            this.m_rgbUidPrimary = this.Reader.ReadBytes(16);
            this.m_bTag = this.Reader.ReadByte();
            this.m_pvBits = this.Reader.ReadBytes((int)(size - 33));
        }
        else
        {
            this.m_rgbUidPrimary = new byte[16];
            this.m_bTag = this.Reader.ReadByte();
            this.m_pvBits = this.Reader.ReadBytes((int)(size - 17));
        } 
    }

}


