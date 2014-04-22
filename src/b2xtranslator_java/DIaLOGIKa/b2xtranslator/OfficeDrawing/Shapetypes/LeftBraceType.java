//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class LeftBraceType  extends ShapeType 
{
    public LeftBraceType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        //Endcaps: Flat
        this.Path = "m21600,qx10800@0l10800@2qy0@11,10800@3l10800@1qy21600,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum #1 0 #0");
        this.Formulas.add("sum #1 #0 0");
        this.Formulas.add("prod #0 9598 32768");
        this.Formulas.add("sum 21600 0 @4");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("min #1 @6");
        this.Formulas.add("prod @7 1 2");
        this.Formulas.add("prod #0 2 1");
        this.Formulas.add("sum 21600 0 @9");
        this.Formulas.add("val #1");
        this.AdjustmentValues = "1800,10800";
        this.ConnectorLocations = "21600,0;0,10800;21600,21600";
        this.TextboxRectangle = "13963,@4,21600,@5";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        Handle HandleTwo = new Handle();
        HandleOne.position = "center,#0";
        HandleOne.yrange = "0,@8";
        HandleTwo.position = "topLeft,#1";
        HandleTwo.yrange = "@9,@10";
        this.Handles.Add(HandleOne);
        this.Handles.Add(HandleTwo);
    }

}


