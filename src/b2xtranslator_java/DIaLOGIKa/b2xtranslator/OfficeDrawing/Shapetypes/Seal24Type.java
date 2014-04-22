//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class Seal24Type  extends ShapeType 
{
    public Seal24Type() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,10800l@7@14,21232,8005@9@16,20153,5400@11@18,18437,3163@12@17,16200,1447@10@15,13595,368@8@13,10800,0@14@13,8005,368@16@15,5400,1447@18@17,3163,3163@17@18,1447,5400@15@16,368,8005@13@14,,10800@13@8,368,13595@15@10,1447,16200@17@12,3163,18437@18@11,5400,20153@16@9,8005,21232@14@7,10800,21600@8@7,13595,21232@10@9,16200,20153@12@11,18437,18437@11@12,20153,16200@9@10,21232,13595@7@8xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum 10800 0 #0");
        this.Formulas.add("prod @0 32488 32768");
        this.Formulas.add("prod @0 4277 32768");
        this.Formulas.add("prod @0 30274 32768");
        this.Formulas.add("prod @0 12540 32768");
        this.Formulas.add("prod @0 25997 32768");
        this.Formulas.add("prod @0 19948 32768");
        this.Formulas.add("sum @1 10800 0");
        this.Formulas.add("sum @2 10800 0");
        this.Formulas.add("sum @3 10800 0");
        this.Formulas.add("sum @4 10800 0");
        this.Formulas.add("sum @5 10800 0");
        this.Formulas.add("sum @6 10800 0");
        this.Formulas.add("sum 10800 0 @1");
        this.Formulas.add("sum 10800 0 @2");
        this.Formulas.add("sum 10800 0 @3");
        this.Formulas.add("sum 10800 0 @4");
        this.Formulas.add("sum 10800 0 @5");
        this.Formulas.add("sum 10800 0 @6");
        this.Formulas.add("prod @0 23170 32768");
        this.Formulas.add("sum @19 10800 0");
        this.Formulas.add("sum 10800 0 @19");
        this.AdjustmentValues = "2700";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "@21,@21,@20,@20";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


