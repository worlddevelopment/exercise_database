//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class WaveType  extends ShapeType 
{
    public WaveType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@28@0c@27@1@26@3@25@0l@21@4c@22@5@23@6@24@4xe";
        this.AdjustmentValues = "2809,10800";
        this.ConnectorLocations = "@35,@0;@38,10800;@37,@4;@36,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@31,@33,@32,@34";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod @0 41 9");
        this.Formulas.add("prod @0 23 9 ");
        this.Formulas.add("sum 0 0 @2 ");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 @1 ");
        this.Formulas.add("sum 21600 0 @3 ");
        this.Formulas.add("sum #1 0 10800 ");
        this.Formulas.add("sum 21600 0 #1 ");
        this.Formulas.add("prod @8 2 3 ");
        this.Formulas.add("prod @8 4 3 ");
        this.Formulas.add("prod @8 2 1 ");
        this.Formulas.add("sum 21600 0 @9 ");
        this.Formulas.add("sum 21600 0 @10 ");
        this.Formulas.add("sum 21600 0 @11 ");
        this.Formulas.add("prod #1 2 3 ");
        this.Formulas.add("prod #1 4 3 ");
        this.Formulas.add("prod #1 2 1 ");
        this.Formulas.add("sum 21600 0 @15");
        this.Formulas.add("sum 21600 0 @16 ");
        this.Formulas.add("sum 21600 0 @17 ");
        this.Formulas.add("if @7 @14 0 ");
        this.Formulas.add("if @7 @13 @15 ");
        this.Formulas.add("if @7 @12 @16 ");
        this.Formulas.add("if @7 21600 @17 ");
        this.Formulas.add("if @7 0 @20 ");
        this.Formulas.add("if @7 @9 @19 ");
        this.Formulas.add("if @7 @10 @18 ");
        this.Formulas.add("if @7 @11 21600 ");
        this.Formulas.add("sum @24 0 @21 ");
        this.Formulas.add("sum @4 0 @0 ");
        this.Formulas.add("max @21 @25 ");
        this.Formulas.add("min @24 @28 ");
        this.Formulas.add("prod @0 2 1 ");
        this.Formulas.add("sum 21600 0 @33");
        this.Formulas.add("mid @26 @27 ");
        this.Formulas.add("mid @24 @28 ");
        this.Formulas.add("mid @22 @23 ");
        this.Formulas.add("mid @21 @25");
        this.Handles = new CSList<Handle>();
        Handle handleOne = new Handle();
        handleOne.position = "topLeft,#0";
        handleOne.yrange = "0,4459";
        this.Handles.Add(handleOne);
        Handle handleTwo = new Handle();
        handleTwo.position = "#1,bottomRight";
        handleTwo.xrange = "8640,12960";
        this.Handles.Add(handleTwo);
    }

}


