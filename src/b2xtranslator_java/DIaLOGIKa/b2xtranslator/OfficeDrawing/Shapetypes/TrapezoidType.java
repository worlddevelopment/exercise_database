//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TrapezoidType  extends ShapeType 
{
    public TrapezoidType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l@0,21600@1,21600,21600,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum width 0 @2");
        this.Formulas.add("mid #0 width");
        this.Formulas.add("mid @1 0");
        this.Formulas.add("prod height width #0");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("sum height 0 @7");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("sum #0 0 @9");
        this.Formulas.add("if @10 @8 0");
        this.Formulas.add("if @10 @7 height");
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "@3,10800;10800,21600;@2,10800;10800,0";
        this.TextboxRectangle = "1800,1800,19800,19800;4500,4500,17100,17100;7200,7200,14400,14400";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,bottomRight";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


