//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class WedgeEllipseCalloutType  extends ShapeType 
{
    public WedgeEllipseCalloutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "wr,,21600,21600@15@16@17@18l@21@22xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum 10800 0 #0");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("atan2 @2 @3");
        this.Formulas.add("sumangle @4 11 0");
        this.Formulas.add("sumangle @4 0 11");
        this.Formulas.add("cos 10800 @4");
        this.Formulas.add("sin 10800 @4");
        this.Formulas.add("cos 10800 @5");
        this.Formulas.add("sin 10800 @5");
        this.Formulas.add("cos 10800 @6");
        this.Formulas.add("sin 10800 @6");
        this.Formulas.add("sum 10800 0 @7");
        this.Formulas.add("sum 10800 0 @8");
        this.Formulas.add("sum 10800 0 @9");
        this.Formulas.add("sum 10800 0 @10");
        this.Formulas.add("sum 10800 0 @11");
        this.Formulas.add("sum 10800 0 @12");
        this.Formulas.add("mod @2 @3 0");
        this.Formulas.add("sum @19 0 10800");
        this.Formulas.add("if @20 #0 @13");
        this.Formulas.add("if @20 #1 @14");
        this.AdjustmentValues = "1350,25920";
        this.ConnectorLocations = "10800,0;3163,3163;0,10800;3163,18437;10800,21600;18437,18437;21600,10800;18437,3163;@21,@22";
        this.TextboxRectangle = "3163,3163,18437,18437";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        Handles.Add(HandleOne);
    }

}


