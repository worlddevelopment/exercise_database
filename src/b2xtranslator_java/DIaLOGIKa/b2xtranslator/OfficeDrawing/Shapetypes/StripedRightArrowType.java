//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class StripedRightArrowType  extends ShapeType 
{
    public StripedRightArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l@0@1,3375@1,3375@2@0@2@0,21600,21600,10800xem1350@1l1350@2,2700@2,2700@1xem0@1l0@2,675@2,675@1xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum height 0 #1");
        this.Formulas.add("sum 10800 0 #1");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod @4 @3 10800");
        this.Formulas.add("sum width 0 @5");
        this.AdjustmentValues = "16200,5400";
        this.ConnectorLocations = "@0,0;0,10800;@0,21600;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "3375,@1,@6,@2";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        HandleOne.xrange = "3375,21600";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


