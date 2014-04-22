//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class IsoscelesTriangleType  extends ShapeType 
{
    public IsoscelesTriangleType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l,21600r21600,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum @1 10800 0");
        this.AdjustmentValues = "10800";
        this.ConnectorLocations = "@0,0;@1,10800;0,21600;10800,21600;21600,21600;@2,10800";
        this.TextboxRectangle = "0,10800,10800,18000;5400,10800,16200,18000;10800,10800,21600,18000;0,7200,7200,21600;7200,7200,14400,21600;14400,7200,21600,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "0,21600";
        this.Handles.Add(HandleOne);
    }

}


