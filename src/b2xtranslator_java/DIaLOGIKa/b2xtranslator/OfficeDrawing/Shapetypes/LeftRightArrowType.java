//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class LeftRightArrowType  extends ShapeType 
{
    public LeftRightArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m,10800l@0,21600@0@3@2@3@2,21600,21600,10800@2,0@2@1@0@1@0,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("prod #0 #1 10800");
        this.Formulas.add("sum #0 0 @4");
        this.Formulas.add("sum 21600 0 @5");
        this.AdjustmentValues = "4320,5400";
        this.ConnectorLocations = "@2,0;10800,@1;@0,0;0,10800;@0,21600;10800,@3;@2,21600;21600,10800";
        this.ConnectorAngles = "270,270,270,180,90,90,90,0";
        this.TextboxRectangle = "@5,@1,@6,@3";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        HandleOne.xrange = "0,10800";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


