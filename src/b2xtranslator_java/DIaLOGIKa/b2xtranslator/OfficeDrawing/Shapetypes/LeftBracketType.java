//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class LeftBracketType  extends ShapeType 
{
    public LeftBracketType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.round;
        //Endcaps: Flat
        this.Path = "m21600,qx0@0l0@1qy21600,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("prod #0 9598 32768");
        this.Formulas.add("sum 21600 0 @2");
        this.AdjustmentValues = "1800";
        this.ConnectorLocations = "21600,0;0,10800;21600,21600";
        this.TextboxRectangle = "6326,@2,21600,@3";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


