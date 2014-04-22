//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class UpDownArrowType  extends ShapeType 
{
    public UpDownArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m10800,l21600@0@3@0@3@2,21600@2,10800,21600,0@2@1@2@1@0,0@0xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #1");
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("prod #1 #0 10800 ");
        this.Formulas.add("sum #1 0 @4");
        this.Formulas.add("sum 21600 0 @5");
        this.AdjustmentValues = "5400,4320";
        this.ConnectorLocations = "10800,0;0,@0;@1,10800;0,@2;10800,21600;21600,@2;@3,10800;21600,@0";
        this.ConnectorAngles = "270,180,180,180,90,0,0,0";
        this.TextboxRectangle = "@1,@5,@3,@6";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        HandleOne.xrange = "0,10800";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


