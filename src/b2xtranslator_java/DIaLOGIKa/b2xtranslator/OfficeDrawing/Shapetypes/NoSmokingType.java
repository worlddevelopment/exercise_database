//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class NoSmokingType  extends ShapeType 
{
    public NoSmokingType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m,10800qy10800,,21600,10800,10800,21600,,10800xar@0@0@16@16@12@14@15@13xar@0@0@16@16@13@15@14@12xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod @0 2 1");
        this.Formulas.add("sum 21600 0 @1");
        this.Formulas.add("prod @2 @2 1 ");
        this.Formulas.add("prod @0 @0 1");
        this.Formulas.add("sum @3 0 @4");
        this.Formulas.add("prod @5 1 8 ");
        this.Formulas.add("sqrt @6 ");
        this.Formulas.add("prod @4 1 8 ");
        this.Formulas.add("sqrt @8 ");
        this.Formulas.add("sum @7 @9 0");
        this.Formulas.add("sum @7 0 @9");
        this.Formulas.add("sum @10 10800 0");
        this.Formulas.add("sum 10800 0 @10");
        this.Formulas.add("sum @11 10800 0 ");
        this.Formulas.add("sum 10800 0 @11 ");
        this.Formulas.add("sum 21600 0 @0");
        this.AdjustmentValues = "2700";
        this.ConnectorLocations = "10800,0;3163,3163;0,10800;3163,18437;10800,21600;18437,18437;21600,10800;18437,3163";
        this.TextboxRectangle = "3163,3163,18437,18437";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,7200";
        this.Handles.Add(HandleOne);
    }

}


