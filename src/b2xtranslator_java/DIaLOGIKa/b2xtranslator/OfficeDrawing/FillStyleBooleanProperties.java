//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class FillStyleBooleanProperties   
{
    public boolean fNoFillHitTest;
    public boolean fillUseRect;
    public boolean fillShape;
    public boolean fHitTestFill;
    public boolean fFilled;
    public boolean fUseShapeAnchor;
    public boolean fRecolorFillAsPicture;
    public boolean fUsefNoFillHitTest;
    public boolean fUsefillUseRect;
    public boolean fUsefillShape;
    public boolean fUseHitTestFill;
    public boolean fUsefFilled;
    public boolean fUsefUseShapeAnchor;
    public boolean fUsefRecolorFillAsPicture;
    public FillStyleBooleanProperties(long entryOperand) throws Exception {
        fNoFillHitTest = Utils.BitmaskToBool(entryOperand, 0x1);
        fillUseRect = Utils.BitmaskToBool(entryOperand, 0x1 << 1);
        fillShape = Utils.BitmaskToBool(entryOperand, 0x1 << 2);
        fHitTestFill = Utils.BitmaskToBool(entryOperand, 0x1 << 3);
        fFilled = Utils.BitmaskToBool(entryOperand, 0x1 << 4);
        fUseShapeAnchor = Utils.BitmaskToBool(entryOperand, 0x1 << 5);
        fRecolorFillAsPicture = Utils.BitmaskToBool(entryOperand, 0x1 << 6);
        // 0x1 << 7-15 is ununsed
        fUsefNoFillHitTest = Utils.BitmaskToBool(entryOperand, 0x1 << 16);
        fUsefillUseRect = Utils.BitmaskToBool(entryOperand, 0x1 << 17);
        fUsefillShape = Utils.BitmaskToBool(entryOperand, 0x1 << 18);
        fUseHitTestFill = Utils.BitmaskToBool(entryOperand, 0x1 << 19);
        fUsefFilled = Utils.BitmaskToBool(entryOperand, 0x1 << 20);
        fUsefUseShapeAnchor = Utils.BitmaskToBool(entryOperand, 0x1 << 21);
        fUsefRecolorFillAsPicture = Utils.BitmaskToBool(entryOperand, 0x1 << 22);
    }

}


