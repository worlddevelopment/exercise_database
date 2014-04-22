//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class DownArrowType  extends ShapeType 
{
    public DownArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m0@0l@1@0@1,0@2,0@2@0,21600@0,10800,21600xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum height 0 #1");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod @4 @3 10800");
        this.Formulas.add("sum width 0 @5");
        this.AdjustmentValues = "16200,5400";
        this.ConnectorLocations = "10800,0;0,@0;10800,21600;21600,@0";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@1,0,@2,@6";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#1,#0";
        HandleOne.xrange = "0,10800";
        HandleOne.yrange = "0,21600";
        this.Handles.Add(HandleOne);
    }

}


