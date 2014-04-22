//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextCirclePour  extends ShapeType 
{
    public TextCirclePour() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "-11730944,5400";
        this.Path = "al10800,10800,10800,10800@2@5al10800,10800@0@0@2@5e";
        this.ConnectorLocations = "@17,10800;@12,@13;@16,10800;@12,@14";
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #1");
        this.Formulas.add("val #0");
        this.Formulas.add("sum 0 0 #0");
        this.Formulas.add("prod #0 2 1");
        this.Formulas.add("sumangle @3 0 360");
        this.Formulas.add("if @3 @4 @3");
        this.Formulas.add("val 10800");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("prod #1 1 2");
        this.Formulas.add("sum @8 5400 0");
        this.Formulas.add("cos @9 #0");
        this.Formulas.add("sin @9 #0");
        this.Formulas.add("sum @10 10800 0");
        this.Formulas.add("sum @11 10800 0");
        this.Formulas.add("sum 10800 0 @11");
        this.Formulas.add("sum #1 10800 0");
        this.Formulas.add("if #0 @7 @15");
        this.Formulas.add("if #0 0 21600");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "#1,#0";
        h1.polar = "10800,10800";
        h1.radiusrange = "0,10800";
        this.Handles.Add(h1);
    }

}


