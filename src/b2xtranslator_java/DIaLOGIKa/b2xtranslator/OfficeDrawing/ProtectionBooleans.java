//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class ProtectionBooleans   
{
    public boolean fLockAgainstGrouping;
    public boolean fLockAdjustHandles;
    public boolean fLockText;
    public boolean fLockVertices;
    public boolean fLockCropping;
    public boolean fLockAgainstSelect;
    public boolean fLockPosition;
    public boolean fLockAspectRatio;
    public boolean fLockRotation;
    public boolean fLockAgainstUngrouping;
    public boolean fUsefLockAgainstGrouping;
    public boolean fUsefLockAdjustHandles;
    public boolean fUsefLockText;
    public boolean fUsefLockVertices;
    public boolean fUsefLockCropping;
    public boolean fUsefLockAgainstSelect;
    public boolean fUsefLockPosition;
    public boolean fUsefLockAspectRatio;
    public boolean fUsefLockRotation;
    public boolean fUsefLockAgainstUngrouping;
    public ProtectionBooleans() throws Exception {
    }

    public ProtectionBooleans(long entryOperand) throws Exception {
        fLockAgainstGrouping = Utils.BitmaskToBool(entryOperand, 0x1);
        fLockAdjustHandles = Utils.BitmaskToBool(entryOperand, 0x2);
        fLockText = Utils.BitmaskToBool(entryOperand, 0x4);
        fLockVertices = Utils.BitmaskToBool(entryOperand, 0x8);
        fLockCropping = Utils.BitmaskToBool(entryOperand, 0x10);
        fLockAgainstSelect = Utils.BitmaskToBool(entryOperand, 0x20);
        fLockPosition = Utils.BitmaskToBool(entryOperand, 0x30);
        fLockAspectRatio = Utils.BitmaskToBool(entryOperand, 0x40);
        fLockRotation = Utils.BitmaskToBool(entryOperand, 0x100);
        fLockAgainstUngrouping = Utils.BitmaskToBool(entryOperand, 0x200);
        //unused 0x400 0x800 0x1000 0x2000 0x4000 0x8000
        fUsefLockAgainstGrouping = Utils.BitmaskToBool(entryOperand, 0x10000);
        fUsefLockAdjustHandles = Utils.BitmaskToBool(entryOperand, 0x20000);
        fUsefLockText = Utils.BitmaskToBool(entryOperand, 0x40000);
        fUsefLockVertices = Utils.BitmaskToBool(entryOperand, 0x80000);
        fUsefLockCropping = Utils.BitmaskToBool(entryOperand, 0x100000);
        fUsefLockAgainstSelect = Utils.BitmaskToBool(entryOperand, 0x200000);
        fUsefLockPosition = Utils.BitmaskToBool(entryOperand, 0x400000);
        fUsefLockAspectRatio = Utils.BitmaskToBool(entryOperand, 0x800000);
        fUsefLockRotation = Utils.BitmaskToBool(entryOperand, 0x1000000);
        fUsefLockAgainstUngrouping = Utils.BitmaskToBool(entryOperand, 0x2000000);
    }

}


