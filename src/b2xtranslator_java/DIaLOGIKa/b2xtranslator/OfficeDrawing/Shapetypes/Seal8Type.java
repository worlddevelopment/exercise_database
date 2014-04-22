//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class Seal8Type  extends ShapeType 
{
    public Seal8Type() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,10800l@3@6,18436,3163@4@5,10800,0@6@5,3163,3163@5@6,,10800@5@4,3163,18436@6@3,10800,21600@4@3,18436,18436@3@4xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum 10800 0 #0");
        this.Formulas.add("prod @0 30274 32768");
        this.Formulas.add("prod @0 12540 32768");
        this.Formulas.add("sum @1 10800 0");
        this.Formulas.add("sum @2 10800 0");
        this.Formulas.add("sum 10800 0 @1");
        this.Formulas.add("sum 10800 0 @2");
        this.Formulas.add("prod @0 23170 32768");
        this.Formulas.add("sum @7 10800 0");
        this.Formulas.add("sum 10800 0 @7");
        this.AdjustmentValues = "2538";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "@9,@9,@8,@8";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


