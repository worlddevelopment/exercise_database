//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class PlusType  extends ShapeType 
{
    public PlusType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l@0@0,0@0,0@2@0@2@0,21600@1,21600@1@2,21600@2,21600@0@1@0@1,xe";
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
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "@8,0;0,@9;@8,@7;@6,@9";
        this.TextboxRectangle = "0,0,21600,21600;5400,5400,16200,16200;10800,10800,10800,10800";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.switchHandle = "true";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
        this.Limo = "10800,10800";
    }

}


