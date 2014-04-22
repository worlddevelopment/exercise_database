//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextWave1  extends ShapeType 
{
    public TextWave1() throws Exception {
        this.TextPath = true;
        this.AdjustmentValues = "2809,10800";
        this.Path = "m@25@0c@26@3@27@1@28@0m@21@4c@22@5@23@6@24@4e";
        this.ConnectorLocations = "@35,@0;@38,10800;@37,@4;@36,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "topLeft,#0";
        h1.yrange = "0,4459";
        Handle h2 = new Handle();
        h2.position = "#1,bottomRight";
        h2.xrange = "8640,12960";
        this.Handles.Add(h1);
        this.Handles.Add(h2);
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
        this.Formulas.add("prod @8 2 3");
        this.Formulas.add("prod @8 4 3");
        this.Formulas.add("prod @8 2 1");
        this.Formulas.add("sum 21600 0 @9");
        this.Formulas.add("sum 21600 0 @10");
        this.Formulas.add("sum 21600 0 @11");
        this.Formulas.add("prod #1 2 3");
        this.Formulas.add("prod #1 4 3");
        this.Formulas.add("prod #1 2 1");
        this.Formulas.add("sum 21600 0 @15");
        this.Formulas.add("sum 21600 0 @16");
        this.Formulas.add("sum 21600 0 @17");
        this.Formulas.add("if @7 @14 0");
        this.Formulas.add("if @7 @13 @15");
        this.Formulas.add("if @7 @12 @16");
        this.Formulas.add("if @7 21600 @17");
        this.Formulas.add("if @7 0 @20");
        this.Formulas.add("if @7 @9 @19");
        this.Formulas.add("if @7 @10 @18");
        this.Formulas.add("if @7 @11 21600");
        this.Formulas.add("sum @24 0 @21");
        this.Formulas.add("sum @4 0 @0");
        this.Formulas.add("max @21 @25");
        this.Formulas.add("min @24 @28");
        this.Formulas.add("prod @0 2 1");
        this.Formulas.add("sum 21600 0 @33");
        this.Formulas.add("mid @26 @27");
        this.Formulas.add("mid @24 @28");
        this.Formulas.add("mid @22 @23");
        this.Formulas.add("mid @21 @25");
    }

}


