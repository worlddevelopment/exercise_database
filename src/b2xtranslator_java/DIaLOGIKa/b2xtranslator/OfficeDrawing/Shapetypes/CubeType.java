//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CubeType  extends ShapeType 
{
    public CubeType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l0@0,,21600@1,21600,21600@2,21600,xem0@0nfl@1@0,21600,em@1@0nfl@1,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("mid height #0");
        this.Formulas.add("prod @1 1 2");
        this.Formulas.add("prod @2 1 2");
        this.Formulas.add("mid width #0");
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "@6,0;@4,@0;0,@3;@4,21600;@1,@3;21600,@5";
        this.ConnectorAngles = "270,270,180,90,0,0";
        this.TextboxRectangle = "0,@0,@1,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.switchHandle = "true";
        HandleOne.yrange = "0,21600";
        this.Handles.Add(HandleOne);
        this.Limo = "10800,10800";
    }

}


