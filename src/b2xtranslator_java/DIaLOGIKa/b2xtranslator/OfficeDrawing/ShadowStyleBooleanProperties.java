//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class ShadowStyleBooleanProperties   
{
    public boolean fShadowObscured;
    public boolean fShadow;
    public boolean fUsefshadowObscured;
    public boolean fUsefShadow;
    public ShadowStyleBooleanProperties(long entryOperand) throws Exception {
        fShadowObscured = Utils.BitmaskToBool(entryOperand, 0x1);
        fShadow = Utils.BitmaskToBool(entryOperand, 0x2);
        fUsefshadowObscured = Utils.BitmaskToBool(entryOperand, 0x10000);
        fUsefShadow = Utils.BitmaskToBool(entryOperand, 0x20000);
    }

}


