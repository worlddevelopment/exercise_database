//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class FoldedCornerType  extends ShapeType 
{
    public FoldedCornerType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l,21600@0,21600,21600@0,21600,xem@0,21600nfl@3@5c@7@9@11@13,21600@0e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 @0");
        this.Formulas.add("prod @1 8481 32768");
        this.Formulas.add("sum @2 @0 0");
        this.Formulas.add("prod @1 1117 32768");
        this.Formulas.add("sum @4 @0 0");
        this.Formulas.add("prod @1 11764 32768");
        this.Formulas.add("sum @6 @0 0");
        this.Formulas.add("prod @1 6144 32768");
        this.Formulas.add("sum @8 @0 0");
        this.Formulas.add("prod @1 20480 32768");
        this.Formulas.add("sum @10 @0 0");
        this.Formulas.add("prod @1 6144 32768");
        this.Formulas.add("sum @12 @0 0");
        this.AdjustmentValues = "18900";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "0,0,21600,@13";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,bottomRight";
        HandleOne.xrange = "10800,21600";
        this.Handles.Add(HandleOne);
    }

}


