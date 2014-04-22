//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextArchUpCurve  extends ShapeType 
{
    public TextArchUpCurve() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "11796480";
        this.Path = "al10800,10800,10800,10800@2@14e";
        this.ConnectorLocations = "10800,@22;@19,@20;@21,@20";
        this.PreferRelative = false;
        this.TextKerning = true;
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #1");
        this.Formulas.add("val #0");
        this.Formulas.add("sum 0 0 #0");
        this.Formulas.add("sumangle #0 0 180");
        this.Formulas.add("sumangle #0 0 90");
        this.Formulas.add("prod @4 2 1");
        this.Formulas.add("sumangle #0 90 0");
        this.Formulas.add("prod @6 2 1");
        this.Formulas.add("abs #0");
        this.Formulas.add("sumangle @8 0 90");
        this.Formulas.add("if @9 @7 @5");
        this.Formulas.add("sumangle @10 0 360");
        this.Formulas.add("if @10 @11 @10");
        this.Formulas.add("sumangle @12 0 360");
        this.Formulas.add("if @12 @13 @12");
        this.Formulas.add("sum 0 0 @14");
        this.Formulas.add("val 10800");
        this.Formulas.add("cos 10800 #0");
        this.Formulas.add("sin 10800 #0");
        this.Formulas.add("sum @17 10800 0");
        this.Formulas.add("sum @18 10800 0");
        this.Formulas.add("sum 10800 0 @17");
        this.Formulas.add("if @9 0 21600");
        this.Formulas.add("sum 10800 0 @18");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.polar = "10800,10800";
        h1.position = "@16,#0";
        this.Handles.Add(h1);
    }

}


