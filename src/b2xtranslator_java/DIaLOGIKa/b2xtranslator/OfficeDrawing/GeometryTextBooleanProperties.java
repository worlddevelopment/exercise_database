//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:32 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class GeometryTextBooleanProperties   
{
    public boolean gtextFStrikethrough;
    public boolean gtextFSmallcaps;
    public boolean gtextFShadow;
    public boolean gtextFUnderline;
    public boolean gtextFItalic;
    public boolean gtextFBold;
    public boolean gtextFDxMeasure;
    public boolean gtextFNormalize;
    public boolean gtextFBestFit;
    public boolean gtextFShrinkFit;
    public boolean gtextFStretch;
    public boolean gtextFTight;
    public boolean gtextFKern;
    public boolean gtextFVertical;
    public boolean fGtext;
    public boolean gtextFReverseRows;
    public boolean fUsegtextFSStrikeThrough;
    public boolean fUsegtextFSmallcaps;
    public boolean fUsegtextFShadow;
    public boolean fUsegtextFUnderline;
    public boolean fUsegtextFItalic;
    public boolean fUsegtextFBold;
    public boolean fUsegtextFDxMeasure;
    public boolean fUsegtextFNormalize;
    public boolean fUsegtextFBestFit;
    public boolean fUsegtextFShrinkFit;
    public boolean fUsegtextFStretch;
    public boolean fUsegtextFTight;
    public boolean fUsegtextFKern;
    public boolean fUsegtextFVertical;
    public boolean fUsefGtext;
    public boolean fUsegtextFReverseRows;
    public GeometryTextBooleanProperties(long entryOperand) throws Exception {
        gtextFStrikethrough = Utils.BitmaskToBool(entryOperand, 0x1);
        gtextFSmallcaps = Utils.BitmaskToBool(entryOperand, 0x1 << 1);
        gtextFShadow = Utils.BitmaskToBool(entryOperand, 0x1 << 2);
        gtextFUnderline = Utils.BitmaskToBool(entryOperand, 0x1 << 3);
        gtextFItalic = Utils.BitmaskToBool(entryOperand, 0x1 << 4);
        gtextFBold = Utils.BitmaskToBool(entryOperand, 0x1 << 5);
        gtextFDxMeasure = Utils.BitmaskToBool(entryOperand, 0x1 << 6);
        gtextFNormalize = Utils.BitmaskToBool(entryOperand, 0x1 << 7);
        gtextFBestFit = Utils.BitmaskToBool(entryOperand, 0x1 << 8);
        gtextFShrinkFit = Utils.BitmaskToBool(entryOperand, 0x1 << 9);
        gtextFStretch = Utils.BitmaskToBool(entryOperand, 0x1 << 10);
        gtextFTight = Utils.BitmaskToBool(entryOperand, 0x1 << 11);
        gtextFKern = Utils.BitmaskToBool(entryOperand, 0x1 << 12);
        gtextFVertical = Utils.BitmaskToBool(entryOperand, 0x1 << 13);
        fGtext = Utils.BitmaskToBool(entryOperand, 0x1 << 14);
        gtextFReverseRows = Utils.BitmaskToBool(entryOperand, 0x1 << 15);
        fUsegtextFSStrikeThrough = Utils.BitmaskToBool(entryOperand, 0x1 << 16);
        fUsegtextFSmallcaps = Utils.BitmaskToBool(entryOperand, 0x1 << 17);
        fUsegtextFShadow = Utils.BitmaskToBool(entryOperand, 0x1 << 18);
        fUsegtextFUnderline = Utils.BitmaskToBool(entryOperand, 0x1 << 19);
        fUsegtextFItalic = Utils.BitmaskToBool(entryOperand, 0x1 << 20);
        fUsegtextFBold = Utils.BitmaskToBool(entryOperand, 0x1 << 21);
        fUsegtextFDxMeasure = Utils.BitmaskToBool(entryOperand, 0x1 << 22);
        fUsegtextFNormalize = Utils.BitmaskToBool(entryOperand, 0x1 << 23);
        fUsegtextFBestFit = Utils.BitmaskToBool(entryOperand, 0x1 << 24);
        fUsegtextFShrinkFit = Utils.BitmaskToBool(entryOperand, 0x1 << 25);
        fUsegtextFStretch = Utils.BitmaskToBool(entryOperand, 0x1 << 26);
        fUsegtextFTight = Utils.BitmaskToBool(entryOperand, 0x1 << 27);
        fUsegtextFKern = Utils.BitmaskToBool(entryOperand, 0x1 << 28);
        fUsegtextFVertical = Utils.BitmaskToBool(entryOperand, 0x1 << 29);
        fUsefGtext = Utils.BitmaskToBool(entryOperand, 0x1 << 30);
        fUsegtextFReverseRows = Utils.BitmaskToBool(entryOperand, 0x40000000);
    }

}


