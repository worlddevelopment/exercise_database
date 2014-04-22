//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.Tools.Utils;

public class DiagramBooleans   
{
    public boolean fPseudoInline;
    public boolean fDoLayout;
    public boolean fReverse;
    public boolean fDoFormat;
    public boolean fUsefPseudoInline;
    public boolean fUsefDoLayout;
    public boolean fUsefReverse;
    public boolean fUsefDoFormat;
    public DiagramBooleans(long entryOperand) throws Exception {
        this.fPseudoInline = Utils.BitmaskToBool(entryOperand, 0x1);
        this.fDoLayout = Utils.BitmaskToBool(entryOperand, 0x2);
        this.fReverse = Utils.BitmaskToBool(entryOperand, 0x4);
        this.fDoFormat = Utils.BitmaskToBool(entryOperand, 0x8);
        //unused: 0x10 - 0x8000
        this.fUsefPseudoInline = Utils.BitmaskToBool(entryOperand, 0x10000);
        this.fUsefDoLayout = Utils.BitmaskToBool(entryOperand, 0x20000);
        this.fUsefReverse = Utils.BitmaskToBool(entryOperand, 0x40000);
        this.fUsefDoFormat = Utils.BitmaskToBool(entryOperand, 0x80000);
    }

}


