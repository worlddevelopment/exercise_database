//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextWave3  extends ShapeType 
{
    public TextWave3() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "1404,10800";
        this.Path = "m@37@0c@38@3@39@1@40@0@41@3@42@1@43@0m@30@4c@31@5@32@6@33@4@34@5@35@6@36@4e";
        this.ConnectorLocations = "@40,@0;@51,10800;@33,@4;@50,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod @0 41 9");
        this.Formulas.add("prod @0 23 9");
        this.Formulas.add("sum 0 0 @2");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 @1");
        this.Formulas.add("sum 21600 0 @3");
        this.Formulas.add("sum #1 0 10800");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("prod @8 1 3");
        this.Formulas.add("prod @8 2 3");
        this.Formulas.add("prod @8 4 3");
        this.Formulas.add("prod @8 5 3");
        this.Formulas.add("prod @8 2 1");
        this.Formulas.add("sum 21600 0 @9");
        this.Formulas.add("sum 21600 0 @10");
        this.Formulas.add("sum 21600 0 @8");
        this.Formulas.add("sum 21600 0 @11");
        this.Formulas.add("sum 21600 0 @12");
        this.Formulas.add("sum 21600 0 @13");
        this.Formulas.add("prod #1 1 3");
        this.Formulas.add("prod #1 2 3");
        this.Formulas.add("prod #1 4 3");
        this.Formulas.add("prod #1 5 3");
        this.Formulas.add("prod #1 2 1");
        this.Formulas.add("sum 21600 0 @20");
        this.Formulas.add("sum 21600 0 @21");
        this.Formulas.add("sum 21600 0 @22");
        this.Formulas.add("sum 21600 0 @23");
        this.Formulas.add("sum 21600 0 @24");
        this.Formulas.add("if @7 @19 0");
        this.Formulas.add("if @7 @18 @20");
        this.Formulas.add("if @7 @17 @21");
        this.Formulas.add("if @7 @16 #1");
        this.Formulas.add("if @7 @15 @22");
        this.Formulas.add("if @7 @14 @23");
        this.Formulas.add("if @7 21600 @24");
        this.Formulas.add("if @7 0 @29");
        this.Formulas.add("if @7 @9 @28");
        this.Formulas.add("if @7 @10 @27");
        this.Formulas.add("if @7 @8 @8");
        this.Formulas.add("if @7 @11 @26");
        this.Formulas.add("if @7 @12 @25");
        this.Formulas.add("if @7 @13 21600");
        this.Formulas.add("sum @36 0 @30");
        this.Formulas.add("sum @4 0 @0");
        this.Formulas.add("max @30 @37");
        this.Formulas.add("min @36 @43");
        this.Formulas.add("prod @0 2 1");
        this.Formulas.add("sum 21600 0 @48");
        this.Formulas.add("mid @36 @43");
        this.Formulas.add("mid @30 @37");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "topLeft,#0";
        h1.yrange = "0,2229";
        this.Handles.Add(h1);
        Handle h2 = new Handle();
        h2.position = "#1,bottomRight";
        h2.xrange = "8640,12960";
        this.Handles.Add(h2);
    }

}


