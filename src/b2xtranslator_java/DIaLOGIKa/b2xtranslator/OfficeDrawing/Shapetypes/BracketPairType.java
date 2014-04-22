//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BracketPairType  extends ShapeType 
{
    public BracketPairType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.round;
        //Encaps: Flat
        this.Path = "m@0,nfqx0@0l0@2qy@0,21600em@1,nfqx21600@0l21600@2qy@1,21600em@0,nsqx0@0l0@2qy@0,21600l@1,21600qx21600@2l21600@0qy@1,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("prod @0 2929 10000");
        this.Formulas.add("sum width 0 @3");
        this.Formulas.add("sum height 0 @3");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("prod height 1 2");
        this.AdjustmentValues = "3600";
        this.ConnectorLocations = "@8,0;0,@9;@8,@7;@6,@9";
        this.TextboxRectangle = "@3,@3,@4,@5";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.switchHandle = "true";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
        this.Limo = "10800,10800";
    }

}


