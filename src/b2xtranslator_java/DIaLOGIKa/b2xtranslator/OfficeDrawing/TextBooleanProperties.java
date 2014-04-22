//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class TextBooleanProperties   
{
    public boolean fFitShapeToText;
    public boolean fAutoTextMargin;
    public boolean fSelectText;
    public boolean fUsefFitShapeToText;
    public boolean fUsefAutoTextMargin;
    public boolean fUsefSelectText;
    public TextBooleanProperties(long entryOperand) throws Exception {
        //1 is unused
        fFitShapeToText = Utils.BitmaskToBool(entryOperand, 0x100000 >> 0);
        //1 is unused
        fAutoTextMargin = Utils.BitmaskToBool(entryOperand, 0x100000 >> 2);
        fSelectText = Utils.BitmaskToBool(entryOperand, 0x100000 >> 3);
        //12 unused
        fUsefFitShapeToText = Utils.BitmaskToBool(entryOperand, 0x100000 >> 16);
        //1 is unused
        fUsefAutoTextMargin = Utils.BitmaskToBool(entryOperand, 0x100000 >> 18);
        fUsefSelectText = Utils.BitmaskToBool(entryOperand, 0x100000 >> 19);
    }

}


