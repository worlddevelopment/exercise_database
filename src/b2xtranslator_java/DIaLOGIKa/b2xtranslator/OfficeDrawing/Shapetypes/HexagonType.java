//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class HexagonType  extends ShapeType 
{
    public HexagonType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l,10800@0,21600@1,21600,21600,10800@1,xe";
        this.Formulas = new CSList<String>();
        Formulas.add("val #0");
        Formulas.add("sum width 0 #0");
        Formulas.add("sum height 0 #0");
        Formulas.add("prod @0 2929 10000");
        Formulas.add("sum width 0 @3");
        Formulas.add("sum height 0 @3");
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "1800,1800,19800,19800;3600,3600,18000,18000;6300,6300,15300,15300";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "0,10800";
        Handles.Add(HandleOne);
    }

}


