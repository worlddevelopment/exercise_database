//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class GeometryBooleans   
{
    public boolean fFillOK;
    public boolean fFillShadeShapeOK;
    public boolean fGtextOK;
    public boolean fLineOK;
    public boolean f3DOK;
    public boolean fShadowOK;
    public boolean fUsefFillOK;
    public boolean fUsefFillShadeShapeOK;
    public boolean fUsefGtextOK;
    public boolean fUsefLineOK;
    public boolean fUsef3DOK;
    public boolean fUsefShadowOK;
    public GeometryBooleans(long entryOperand) throws Exception {
        fFillOK = Utils.BitmaskToBool(entryOperand, 0x1);
        fFillShadeShapeOK = Utils.BitmaskToBool(entryOperand, 0x2);
        fGtextOK = Utils.BitmaskToBool(entryOperand, 0x4);
        fLineOK = Utils.BitmaskToBool(entryOperand, 0x8);
        f3DOK = Utils.BitmaskToBool(entryOperand, 0x10);
        fShadowOK = Utils.BitmaskToBool(entryOperand, 0x20);
        fUsefFillOK = Utils.BitmaskToBool(entryOperand, 0x10000);
        fUsefFillShadeShapeOK = Utils.BitmaskToBool(entryOperand, 0x20000);
        fUsefGtextOK = Utils.BitmaskToBool(entryOperand, 0x40000);
        fUsefLineOK = Utils.BitmaskToBool(entryOperand, 0x80000);
        fUsef3DOK = Utils.BitmaskToBool(entryOperand, 0x100000);
        fUsefShadowOK = Utils.BitmaskToBool(entryOperand, 0x200000);
    }

}


