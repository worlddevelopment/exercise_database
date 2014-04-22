//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class NotchedRightArrowType  extends ShapeType 
{
    public NotchedRightArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l@0@1,0@1@5,10800,0@2@0@2@0,21600,21600,10800xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum height 0 #1");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod @4 @3 10800");
        this.Formulas.add("sum width 0 @5");
        this.AdjustmentValues = "16200,5400";
        this.ConnectorLocations = "@0,0;@5,10800;@0,21600;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@5,@1,@6,@2";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        HandleOne.xrange = "0,21600";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


