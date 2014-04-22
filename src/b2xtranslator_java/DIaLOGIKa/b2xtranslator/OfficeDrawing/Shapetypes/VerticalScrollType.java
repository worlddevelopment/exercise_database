//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class VerticalScrollType  extends ShapeType 
{
    public VerticalScrollType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@5,qx@1@2l@1@0@2@0qx0@7@2,21600l@9,21600qx@10@7l@10@1@11@1qx21600@2@11,xem@5,nfqx@6@2@5@1@4@3@5@2l@6@2em@5@1nfl@10@1em@2,21600nfqx@1@7l@1@0em@2@0nfqx@3@8@2@7l@1@7e";
        this.AdjustmentValues = "2700";
        this.ConnectorLocations = "@14,0;@1,@13;@14,@12;@10,@13";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@1,@1,@10,@7";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum height 0 #0 ");
        this.Formulas.add("val #0 ");
        this.Formulas.add("prod @1 1 2 ");
        this.Formulas.add("prod @1 3 4 ");
        this.Formulas.add("prod @1 5 4 ");
        this.Formulas.add("prod @1 3 2 ");
        this.Formulas.add("prod @1 2 1 ");
        this.Formulas.add("sum height 0 @2 ");
        this.Formulas.add("sum height 0 @3 ");
        this.Formulas.add("sum width 0 @5 ");
        this.Formulas.add("sum width 0 @1 ");
        this.Formulas.add("sum width 0 @2");
        this.Formulas.add("val height ");
        this.Formulas.add("prod height 1 2");
        this.Formulas.add("prod width 1 2");
        this.Handles = new CSList<Handle>();
        Handle handleOne = new Handle();
        handleOne.position = "topLeft,#0";
        handleOne.yrange = "0,5400";
        this.Handles.Add(handleOne);
        this.Limo = "10800,10800";
    }

}


