//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class MoonType  extends ShapeType 
{
    public MoonType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,qx,10800,21600,21600wa@0@10@6@11,21600,21600,21600,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0 ");
        this.Formulas.add("sum 21600 0 #0 ");
        this.Formulas.add("prod #0 #0 @1 ");
        this.Formulas.add("prod 21600 21600 @1 ");
        this.Formulas.add("prod @3 2 1 ");
        this.Formulas.add("sum @4 0 @2");
        this.Formulas.add("sum @5 0 #0 ");
        this.Formulas.add("prod @5 1 2 ");
        this.Formulas.add("sum @7 0 #0 ");
        this.Formulas.add("prod @8 1 2 ");
        this.Formulas.add("sum 10800 0 @9 ");
        this.Formulas.add("sum @9 10800 0 ");
        this.Formulas.add("prod #0 9598 32768 ");
        this.Formulas.add(" sum 21600 0 @12 ");
        this.Formulas.add("ellipse @13 21600 10800 ");
        this.Formulas.add("sum 10800 0 @14 ");
        this.Formulas.add("sum @14 10800 0");
        this.AdjustmentValues = "10800";
        this.ConnectorAngles = "270,180,90,0";
        this.ConnectorLocations = "21600,0;0,10800;21600,21600;@0,10800";
        this.TextboxRectangle = "@12,@15,@0,@16";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,18900";
        this.Handles.Add(HandleOne);
    }

}


