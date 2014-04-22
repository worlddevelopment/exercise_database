//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class ChevronType  extends ShapeType 
{
    public ChevronType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l,0@1,10800,,21600@0,21600,21600,10800xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 @0");
        this.Formulas.add("prod #0 1 2");
        this.AdjustmentValues = "16200";
        this.ConnectorLocations = "@2,0;@1,10800;@2,21600;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "0,0,10800,21600;0,0,16200,21600;0,0,21600,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "0,21600";
        this.Handles.Add(HandleOne);
    }

}


