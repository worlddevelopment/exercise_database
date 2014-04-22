//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class GroupShapeBooleans   
{
    public boolean fPrint;
    public boolean fHidden;
    public boolean fOneD;
    public boolean fIsButton;
    public boolean fOnDblClickNotify;
    public boolean fBehindDocument;
    public boolean fEditedWrap;
    public boolean fScriptAnchor;
    public boolean fReallyHidden;
    public boolean fAllowOverlap;
    public boolean fUserDrawn;
    public boolean fHorizRule;
    public boolean fNoshadeHR;
    public boolean fStandardHR;
    public boolean fIsBullet;
    public boolean fLayoutInCell;
    public boolean fUsefPrint;
    public boolean fUsefHidden;
    public boolean fUsefOneD;
    public boolean fUsefIsButton;
    public boolean fUsefOnDblClickNotify;
    public boolean fUsefBehindDocument;
    public boolean fUsefEditedWrap;
    public boolean fUsefScriptAnchor;
    public boolean fUsefReallyHidden;
    public boolean fUsefAllowOverlap;
    public boolean fUsefUserDrawn;
    public boolean fUsefHorizRule;
    public boolean fUsefNoshadeHR;
    public boolean fUsefStandardHR;
    public boolean fUsefIsBullet;
    public boolean fUsefLayoutInCell;
    public GroupShapeBooleans(long entryOperand) throws Exception {
        fPrint = Utils.BitmaskToBool(entryOperand, 0x1);
        fHidden = Utils.BitmaskToBool(entryOperand, 0x2);
        fOneD = Utils.BitmaskToBool(entryOperand, 0x4);
        fIsButton = Utils.BitmaskToBool(entryOperand, 0x8);
        fOnDblClickNotify = Utils.BitmaskToBool(entryOperand, 0x10);
        fBehindDocument = Utils.BitmaskToBool(entryOperand, 0x20);
        fEditedWrap = Utils.BitmaskToBool(entryOperand, 0x40);
        fScriptAnchor = Utils.BitmaskToBool(entryOperand, 0x80);
        fReallyHidden = Utils.BitmaskToBool(entryOperand, 0x100);
        fAllowOverlap = Utils.BitmaskToBool(entryOperand, 0x200);
        fUserDrawn = Utils.BitmaskToBool(entryOperand, 0x400);
        fHorizRule = Utils.BitmaskToBool(entryOperand, 0x800);
        fNoshadeHR = Utils.BitmaskToBool(entryOperand, 0x1000);
        fStandardHR = Utils.BitmaskToBool(entryOperand, 0x2000);
        fIsBullet = Utils.BitmaskToBool(entryOperand, 0x4000);
        fLayoutInCell = Utils.BitmaskToBool(entryOperand, 0x8000);
        fUsefPrint = Utils.BitmaskToBool(entryOperand, 0x10000);
        fUsefHidden = Utils.BitmaskToBool(entryOperand, 0x20000);
        fUsefOneD = Utils.BitmaskToBool(entryOperand, 0x40000);
        fUsefIsButton = Utils.BitmaskToBool(entryOperand, 0x80000);
        fUsefOnDblClickNotify = Utils.BitmaskToBool(entryOperand, 0x100000);
        fUsefBehindDocument = Utils.BitmaskToBool(entryOperand, 0x200000);
        fUsefEditedWrap = Utils.BitmaskToBool(entryOperand, 0x400000);
        fUsefScriptAnchor = Utils.BitmaskToBool(entryOperand, 0x800000);
        fUsefReallyHidden = Utils.BitmaskToBool(entryOperand, 0x1000000);
        fUsefAllowOverlap = Utils.BitmaskToBool(entryOperand, 0x2000000);
        fUsefUserDrawn = Utils.BitmaskToBool(entryOperand, 0x4000000);
        fUsefHorizRule = Utils.BitmaskToBool(entryOperand, 0x8000000);
        fUsefNoshadeHR = Utils.BitmaskToBool(entryOperand, 0x10000000);
        fUsefStandardHR = Utils.BitmaskToBool(entryOperand, 0x20000000);
        fUsefIsBullet = Utils.BitmaskToBool(entryOperand, 0x40000000);
        fUsefLayoutInCell = Utils.BitmaskToBool(entryOperand, 0x80000000);
    }

}


