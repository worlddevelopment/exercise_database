//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class Seal4Type  extends ShapeType 
{
    public Seal4Type() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,10800l@2@3,10800,0@3@3,,10800@3@2,10800,21600@2@2xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum 10800 0 #0");
        this.Formulas.add("prod @0 23170 32768");
        this.Formulas.add("sum @1 10800 0");
        this.Formulas.add("sum 10800 0 @1");
        this.AdjustmentValues = "8100";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "@3,@3,@2,@2";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


