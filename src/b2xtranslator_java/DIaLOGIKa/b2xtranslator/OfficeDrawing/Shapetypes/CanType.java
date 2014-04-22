//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CanType  extends ShapeType 
{
    public CanType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.round;
        this.Path = "m10800,qx0@1l0@2qy10800,21600,21600@2l21600@1qy10800,xem0@1qy10800@0,21600@1nfe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum height 0 @1");
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "10800,@0;10800,0;0,10800;10800,21600;21600,10800";
        this.ConnectorAngles = "270,270,180,90,0";
        this.TextboxRectangle = "0,@0,21600,@2";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "center,#0";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


