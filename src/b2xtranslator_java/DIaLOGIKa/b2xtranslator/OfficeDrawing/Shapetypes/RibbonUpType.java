//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class RibbonUpType  extends ShapeType 
{
    public RibbonUpType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m0@29l@3@29qx@4@19l@4@10@5@10@5@19qy@6@29l@28@29@26@22@28@23@9@23@9@24qy@8,l@1,qx@0@24l@0@23,0@23,2700@22xem@4@19nfqy@3@20l@1@20qx@0@21@1@10l@4@10em@5@19nfqy@6@20l@8@20qx@9@21@8@10l@5@10em@0@21nfl@0@23em@9@21nfl@9@23e";
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
        this.Formulas.add("prod @10 1 2");
        this.Formulas.add("prod @10 3 4");
        this.Formulas.add("prod height 3 4");
        this.Formulas.add("prod height 1 2");
        this.Formulas.add("prod height 1 4");
        this.Formulas.add("prod height 3 2");
        this.Formulas.add("prod height 2 3");
        this.Formulas.add("sum @11 @14 0");
        this.Formulas.add("sum @12 @15 0");
        this.Formulas.add("sum @13 @16 0");
        this.Formulas.add("sum @17 0 @20");
        this.Formulas.add("sum height 0 @10");
        this.Formulas.add("sum height 0 @19");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("sum width 0 2700");
        this.Formulas.add("sum @25 0 2700");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.AdjustmentValues = "5400,18900";
        this.ConnectorLocations = "@25,0;2700,@22;@25,@10;@26,@22";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@0,0,@9,@10";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        Handle HandleTwo = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "2700,8100";
        HandleTwo.position = "center,#1";
        HandleTwo.yrange = "14400,21600";
        this.Handles.Add(HandleOne);
        this.Handles.Add(HandleTwo);
    }

}


