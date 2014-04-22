//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class ThreeDObjectProperties   
{
    public boolean fc3DLightFace;
    public boolean fc3UseExtrusionColor;
    public boolean fc3DMetallic;
    public boolean fc3D;
    public boolean fUsefc3DLightFace;
    public boolean fUsefc3DUseExtrusionColor;
    public boolean fUsefc3DMetallic;
    public boolean fUsefc3D;
    public ThreeDObjectProperties(long entryOperand) throws Exception {
        fc3DLightFace = Utils.BitmaskToBool(entryOperand, 0x1 << 0);
        fc3UseExtrusionColor = Utils.BitmaskToBool(entryOperand, 0x1 << 1);
        fc3DMetallic = Utils.BitmaskToBool(entryOperand, 0x1 << 2);
        fc3D = Utils.BitmaskToBool(entryOperand, 0x1 << 3);
        //12 unused
        fUsefc3DLightFace = Utils.BitmaskToBool(entryOperand, 0x1 << 16);
        fUsefc3DUseExtrusionColor = Utils.BitmaskToBool(entryOperand, 0x1 << 17);
        fUsefc3DMetallic = Utils.BitmaskToBool(entryOperand, 0x1 << 18);
        fUsefc3D = Utils.BitmaskToBool(entryOperand, 0x1 << 19);
    }

}


//12 unused