//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class SmileyFaceType  extends ShapeType 
{
    public SmileyFaceType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.round;
        this.Path = "m10800,qx,10800,10800,21600,21600,10800,10800,xem7340,6445qx6215,7570,7340,8695,8465,7570,7340,6445xnfem14260,6445qx13135,7570,14260,8695,15385,7570,14260,6445xnfem4960@0c8853@3,12747@3,16640@0nfe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum 33030 0 #0");
        this.Formulas.add("prod #0 4 3");
        this.Formulas.add("prod @0 1 3");
        this.Formulas.add("sum @1 0 @2");
        this.AdjustmentValues = "17520";
        this.ConnectorLocations = "10800,0;3163,3163;0,10800;3163,18437;10800,21600;18437,18437;21600,10800;18437,3163";
        this.TextboxRectangle = "3163,3163,18437,18437";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "center,#0";
        HandleOne.yrange = "15510,17520";
        this.Handles.Add(HandleOne);
    }

}


