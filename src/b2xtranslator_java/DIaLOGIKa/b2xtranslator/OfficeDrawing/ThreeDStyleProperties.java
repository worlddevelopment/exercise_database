//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class ThreeDStyleProperties   
{
    public boolean fc3DFillHarsh;
    public boolean fc3DKeyHarsh;
    public boolean fc3DParallel;
    public boolean fc3DRotationCenterAuto;
    public boolean fc3DConstrainRotation;
    public boolean fUsefc3DFillHarsh;
    public boolean fUsefc3DKeyHarsh;
    public boolean fUsefc3DParallel;
    public boolean fUsefc3DRotationCenterAuto;
    public boolean fUsefc3DConstrainRotation;
    public ThreeDStyleProperties(long entryOperand) throws Exception {
        fc3DFillHarsh = Utils.BitmaskToBool(entryOperand, 0x1 << 0);
        fc3DKeyHarsh = Utils.BitmaskToBool(entryOperand, 0x1 << 1);
        fc3DParallel = Utils.BitmaskToBool(entryOperand, 0x1 << 2);
        fc3DRotationCenterAuto = Utils.BitmaskToBool(entryOperand, 0x1 << 3);
        fc3DConstrainRotation = Utils.BitmaskToBool(entryOperand, 0x1 << 4);
        //11 unused
        fUsefc3DFillHarsh = Utils.BitmaskToBool(entryOperand, 0x1 << 16);
        fUsefc3DKeyHarsh = Utils.BitmaskToBool(entryOperand, 0x1 << 17);
        fUsefc3DParallel = Utils.BitmaskToBool(entryOperand, 0x1 << 18);
        fUsefc3DRotationCenterAuto = Utils.BitmaskToBool(entryOperand, 0x1 << 19);
        fUsefc3DConstrainRotation = Utils.BitmaskToBool(entryOperand, 0x1 << 20);
    }

}


//11 unused