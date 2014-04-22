//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:41 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class UpArrowType  extends ShapeType 
{
    public UpArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m0@0l@1@0@1,21600@2,21600@2@0,21600@0,10800,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("prod #0 #1 10800");
        this.Formulas.add("sum #0 0 @3");
        this.AdjustmentValues = "5400,5400";
        this.ConnectorLocations = "10800,0;0,@0;10800,21600;21600,@0";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@1,@4,@2,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#1,#0";
        HandleOne.xrange = "0,10800";
        HandleOne.yrange = "0,21600";
        this.Handles.Add(HandleOne);
    }

}


