//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class LeftRightUpArrow  extends ShapeType 
{
    public LeftRightUpArrow() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m10800,l@0@2@1@2@1@6@7@6@7@5,0@8@7,21600@7@9@10@9@10,21600,21600@8@10@5@10@6@4@6@4@2@3@2xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0 ");
        this.Formulas.add("val #1 ");
        this.Formulas.add("val #2 ");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("prod @0 21600 @3 ");
        this.Formulas.add("prod @1 21600 @3 ");
        this.Formulas.add("prod @2 @3 21600 ");
        this.Formulas.add("prod 10800 21600 @3 ");
        this.Formulas.add("prod @4 21600 @3 ");
        this.Formulas.add("sum 21600 0 @7 ");
        this.Formulas.add("sum @5 0 @8 ");
        this.Formulas.add("sum @6 0 @8 ");
        this.Formulas.add("prod @12 @7 @11 ");
        this.Formulas.add("sum 21600 0 @13 ");
        this.Formulas.add("sum @0 0 10800 ");
        this.Formulas.add("sum @1 0 10800 ");
        this.Formulas.add("prod @2 @16 @15");
        this.AdjustmentValues = "6480,8640,6171";
        this.ConnectorLocations = "10800,0;0,@8;10800,@9;21600,@8";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@13,@6,@14,@9;@1,@17,@4,@9";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "@2,@1";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#1,#2";
        HandleTwo.xrange = "@0,10800";
        HandleTwo.yrange = "0,@5";
        this.Handles.Add(HandleTwo);
    }

}


