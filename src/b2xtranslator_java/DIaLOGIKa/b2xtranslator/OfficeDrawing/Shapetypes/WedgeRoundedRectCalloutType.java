//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class WedgeRoundedRectCalloutType  extends ShapeType 
{
    public WedgeRoundedRectCalloutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m3600,qx,3600l0@8@12@24,0@9,,18000qy3600,21600l@6,21600@15@27@7,21600,18000,21600qx21600,18000l21600@9@18@30,21600@8,21600,3600qy18000,l@7,0@21@33@6,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum 10800 0 #0");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("sum #0 0 #1");
        this.Formulas.add("sum @0 @1 0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("if @0 3600 12600");
        this.Formulas.add("if @0 9000 18000");
        this.Formulas.add("if @1 3600 12600");
        this.Formulas.add("if @1 9000 18000");
        this.Formulas.add("if @2 0 #0");
        this.Formulas.add("if @3 @10 0");
        this.Formulas.add("if #0 0 @11");
        this.Formulas.add("if @2 @6 #0");
        this.Formulas.add("if @3 @6 @13");
        this.Formulas.add("if @5 @6 @14");
        this.Formulas.add("if @2 #0 21600");
        this.Formulas.add("if @3 21600 @16");
        this.Formulas.add("if @4 21600 @17");
        this.Formulas.add("if @2 #0 @6");
        this.Formulas.add("if @3 @19 @6");
        this.Formulas.add("if #1 @6 @20");
        this.Formulas.add("if @2 @8 #1");
        this.Formulas.add("if @3 @22 @8");
        this.Formulas.add("if #0 @8 @23");
        this.Formulas.add("if @2 21600 #1");
        this.Formulas.add("if @3 21600 @25");
        this.Formulas.add("if @5 21600 @26");
        this.Formulas.add("if @2 #1 @8");
        this.Formulas.add("if @3 @8 @28");
        this.Formulas.add("if @4 @8 @29");
        this.Formulas.add("if @2 #1 0");
        this.Formulas.add("if @3 @31 0");
        this.Formulas.add("if #1 0 @32");
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.AdjustmentValues = "1350,25920";
        this.ConnectorLocations = "10800,0;0,10800;10800,21600;21600,10800;@34,@35";
        this.TextboxRectangle = "791,791,20809,20809";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        Handles.Add(HandleOne);
    }

}


