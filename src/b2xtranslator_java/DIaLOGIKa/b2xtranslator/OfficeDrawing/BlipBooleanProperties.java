//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class BlipBooleanProperties   
{
    public boolean fPictureActive;
    public boolean fPictureBiLevel;
    public boolean fPictureGray;
    public boolean fNoHitTestPicture;
    public boolean fLooping;
    public boolean fRewind;
    public boolean fPicturePreserveGrays;
    public boolean fusefPictureActive;
    public boolean fusefPictureBiLevel;
    public boolean fusefPictureGray;
    public boolean fusefNoHitTestPicture;
    public boolean fusefLooping;
    public boolean fusefRewind;
    public boolean fusefPicturePreserveGrays;
    public BlipBooleanProperties(uint entryOperand) throws Exception {
        fPictureActive = Utils.BitmaskToBool(entryOperand, 0x1);
        fPictureBiLevel = Utils.BitmaskToBool(entryOperand, 0x1 << 1);
        fPictureGray = Utils.BitmaskToBool(entryOperand, 0x1 << 2);
        fNoHitTestPicture = Utils.BitmaskToBool(entryOperand, 0x1 << 3);
        fLooping = Utils.BitmaskToBool(entryOperand, 0x1 << 4);
        fRewind = Utils.BitmaskToBool(entryOperand, 0x1 << 5);
        fPicturePreserveGrays = Utils.BitmaskToBool(entryOperand, 0x1 << 6);
        //unused 9 bits
        fusefPictureActive = Utils.BitmaskToBool(entryOperand, 0x1 << 16);
        fusefPictureBiLevel = Utils.BitmaskToBool(entryOperand, 0x1 << 17);
        fusefPictureGray = Utils.BitmaskToBool(entryOperand, 0x1 << 18);
        fusefNoHitTestPicture = Utils.BitmaskToBool(entryOperand, 0x1 << 19);
        fusefLooping = Utils.BitmaskToBool(entryOperand, 0x1 << 20);
        fusefRewind = Utils.BitmaskToBool(entryOperand, 0x1 << 21);
        fusefPicturePreserveGrays = Utils.BitmaskToBool(entryOperand, 0x1 << 22);
    }

}


//unused 9 bits