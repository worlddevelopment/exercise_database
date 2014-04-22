//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class HorizontalScrollType  extends ShapeType 
{
    public HorizontalScrollType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m0@5qy@2@1l@0@1@0@2qy@7,,21600@2l21600@9qy@7@10l@1@10@1@11qy@2,21600,0@11xem0@5nfqy@2@6@1@5@3@4@2@5l@2@6em@1@5nfl@1@10em21600@2nfqy@7@1l@0@1em@0@2nfqy@8@3@7@2l@7@1e";
        this.AdjustmentValues = "2700";
        this.ConnectorLocations = "@13,@1;0,@14;@13,@10;@12,@14";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@1,@1,@7,@10";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("val #0 ");
        this.Formulas.add("prod @1 1 2");
        this.Formulas.add("prod @1 3 4 ");
        this.Formulas.add("prod @1 5 4 ");
        this.Formulas.add("prod @1 3 2 ");
        this.Formulas.add("prod @1 2 1 ");
        this.Formulas.add("sum width 0 @2 ");
        this.Formulas.add("sum width 0 @3 ");
        this.Formulas.add("sum height 0 @5 ");
        this.Formulas.add("sum height 0 @1 ");
        this.Formulas.add("sum height 0 @2 ");
        this.Formulas.add("val width ");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("prod height 1 2");
        this.Handles = new CSList<Handle>();
        Handle handleOne = new Handle();
        handleOne.position = "#0,topLeft";
        handleOne.xrange = "0,5400";
        this.Handles.Add(handleOne);
        this.Limo = "10800,10800";
    }

}


