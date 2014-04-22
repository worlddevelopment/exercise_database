//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BentUpArrowType  extends ShapeType 
{
    public BentUpArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@4,l@0@2@5@2@5@12,0@12,,21600@1,21600@1@2,21600@2xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum @3 10800 0");
        this.Formulas.add("sum 21600 #0 #1");
        this.Formulas.add("sum #1 #2 0");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("prod #1 2 1");
        this.Formulas.add("sum @8 0 21600");
        this.Formulas.add("prod 21600 @0 @1");
        this.Formulas.add("prod 21600 @4 @1");
        this.Formulas.add("prod 21600 @5 @1");
        this.Formulas.add("prod 21600 @7 @1");
        this.Formulas.add("prod #1 1 2");
        this.Formulas.add("sum @5 0 @4");
        this.Formulas.add("sum @0 0 @4");
        this.Formulas.add("prod @2 @15 @16");
        this.AdjustmentValues = "9257,18514,7200";
        this.ConnectorLocations = "@4,0;@0,@2;0,@11;@14,21600;@1,@13;21600,@2";
        this.ConnectorAngles = "270,180,180,90,0,0";
        this.TextboxRectangle = "0,@12,@1,21600;@5,@17,@1,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "@2,@9";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#1,#2";
        HandleTwo.xrange = "@4,21600";
        HandleTwo.yrange = "0,@0";
        this.Handles.Add(HandleTwo);
    }

}


