//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class RightBracketType  extends ShapeType 
{
    public RightBracketType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.round;
        //Endcaps: Flat
        this.Path = "m,qx21600@0l21600@1qy,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("prod #0 9598 32768");
        this.Formulas.add("sum 21600 0 @2");
        this.AdjustmentValues = "1800";
        this.ConnectorLocations = "0,0;0,21600;21600,10800";
        this.TextboxRectangle = "0,@2,15274,@3";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "bottomRight,#0";
        HandleOne.yrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


