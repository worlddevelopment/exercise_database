//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:05 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.ByteParseException;

public class PictureBulletInformation   
{
    public boolean fPicBullet;
    public boolean fNoAutoSize;
    public boolean fDefaultPic;
    public boolean fTemporary;
    public boolean fFormatting;
    public int iBullet;
    public PictureBulletInformation(byte[] bytes) throws Exception {
        if (bytes.length == 6)
        {
            short flag = System.BitConverter.ToInt16(bytes, 0);
            this.fPicBullet = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x0001);
            this.fNoAutoSize = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x0002);
            this.fDefaultPic = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x0004);
            this.fTemporary = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x0008);
            this.fFormatting = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flag, 0x1000);
            this.iBullet = System.BitConverter.ToInt32(bytes, 2);
        }
        else
        {
            throw new ByteParseException("Cannot parse the struct PBI, the length of the struct doesn't match");
        } 
    }

}


