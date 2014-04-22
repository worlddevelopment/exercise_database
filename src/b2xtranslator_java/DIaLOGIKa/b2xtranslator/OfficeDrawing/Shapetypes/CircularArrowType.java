//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CircularArrowType  extends ShapeType 
{
    public CircularArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "al10800,10800@8@8@4@6,10800,10800,10800,10800@9@7l@30@31@17@18@24@25@15@16@32@33xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #1");
        this.Formulas.add("val #0");
        this.Formulas.add("sum #1 0 #0");
        this.Formulas.add("val 10800");
        this.Formulas.add("sum 0 0 #1");
        this.Formulas.add("sumangle @2 360 0");
        this.Formulas.add("if @2 @2 @5");
        this.Formulas.add("sum 0 0 @6");
        this.Formulas.add("val #2");
        this.Formulas.add("sum 0 0 #0");
        this.Formulas.add("sum #2 0 2700");
        this.Formulas.add("cos @10 #1 ");
        this.Formulas.add("sin @10 #1 ");
        this.Formulas.add("cos 13500 #1");
        this.Formulas.add("sin 13500 #1 ");
        this.Formulas.add("sum @11 10800 0");
        this.Formulas.add("sum @12 10800 0 ");
        this.Formulas.add("sum @13 10800 0 ");
        this.Formulas.add("sum @14 10800 0 ");
        this.Formulas.add("prod #2 1 2 ");
        this.Formulas.add("sum @19 5400 0");
        this.Formulas.add("cos @20 #1");
        this.Formulas.add("sin @20 #1");
        this.Formulas.add("sum @21 10800 0 ");
        this.Formulas.add("sum @12 @23 @22");
        this.Formulas.add("sum @22 @23 @11");
        this.Formulas.add("cos 10800 #1");
        this.Formulas.add("sin 10800 #1");
        this.Formulas.add("cos #2 #1 ");
        this.Formulas.add("sin #2 #1 ");
        this.Formulas.add("sum @26 10800 0");
        this.Formulas.add("sum @27 10800 0");
        this.Formulas.add("sum @28 10800 0");
        this.Formulas.add("sum @29 10800 0");
        this.Formulas.add("sum @19 5400 0 ");
        this.Formulas.add("cos @34 #0 ");
        this.Formulas.add("sin @34 #0 ");
        this.Formulas.add("mid #0 #1 ");
        this.Formulas.add("sumangle @37 180 0 ");
        this.Formulas.add("if @2 @37 @38");
        this.Formulas.add("cos 10800 @39 ");
        this.Formulas.add("sin 10800 @39 ");
        this.Formulas.add("cos #2 @39 ");
        this.Formulas.add("sin #2 @39 ");
        this.Formulas.add("sum @40 10800 0");
        this.Formulas.add("sum @41 10800 0 ");
        this.Formulas.add("sum @42 10800 0 ");
        this.Formulas.add("sum @43 10800 0 ");
        this.Formulas.add("sum @35 10800 0 ");
        this.Formulas.add("sum @36 10800 0");
        this.AdjustmentValues = "-11796480,,5400";
        this.ConnectorLocations = "@44,@45;@48,@49;@46,@47;@17,@18;@24,@25;@15,@16";
        this.TextboxRectangle = "3163,3163,18437,18437";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "@3,#0";
        HandleOne.polar = "10800,10800";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#2,#1";
        HandleTwo.polar = "10800,10800";
        HandleTwo.radiusrange = "0,10800";
        this.Handles.Add(HandleTwo);
    }

}


