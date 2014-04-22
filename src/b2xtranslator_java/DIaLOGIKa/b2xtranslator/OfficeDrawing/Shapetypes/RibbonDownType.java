//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class RibbonDownType  extends ShapeType 
{
    public RibbonDownType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l@3,qx@4@11l@4@10@5@10@5@11qy@6,l@21,0@19@15@21@16@9@16@9@17qy@8@22l@1@22qx@0@17l@0@16,0@16,2700@15xem@4@11nfqy@3@12l@1@12qx@0@13@1@10l@4@10em@5@11nfqy@6@12l@8@12qx@9@13@8@10l@5@10em@0@13nfl@0@16em@9@13nfl@9@16e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum @0 675 0");
        this.Formulas.add("sum @1 675 0");
        this.Formulas.add("sum @2 675 0");
        this.Formulas.add("sum @3 675 0");
        this.Formulas.add("sum width 0 @4");
        this.Formulas.add("sum width 0 @3");
        this.Formulas.add("sum width 0 @2");
        this.Formulas.add("sum width 0 @1");
        this.Formulas.add("sum width 0 @0");
        this.Formulas.add("val #1");
        this.Formulas.add("prod @10 1 4");
        this.Formulas.add("prod @11 2 1");
        this.Formulas.add("prod @11 3 1");
        this.Formulas.add("prod height 1 2");
        this.Formulas.add("sum @14 0 @12");
        this.Formulas.add("sum height 0 @10");
        this.Formulas.add("sum height 0 @11");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("sum width 0 2700");
        this.Formulas.add("sum @18 0 2700");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.AdjustmentValues = "5400,2700";
        this.ConnectorLocations = "@18,@10;2700,@15;@18,21600;@19,@15";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@0,@10,@9,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        Handle HandleTwo = new Handle();
        HandleOne.position = "#0,bottomRight";
        HandleOne.xrange = "2700,8100";
        HandleTwo.position = "center,#1";
        HandleTwo.yrange = "0,7200";
        this.Handles.Add(HandleOne);
        this.Handles.Add(HandleTwo);
    }

}


