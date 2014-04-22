//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextSlantUp  extends ShapeType 
{
    public TextSlantUp() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "12000";
        this.Path = "m0@0l21600,m,21600l21600@1e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 @0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum @2 10800 0");
        this.Formulas.add("prod @1 1 2");
        this.Formulas.add("sum @4 10800 0");
        this.ConnectorLocations = "10800,@2;0,@3;10800,@5;21600,@4";
        this.ConnectorAngles = "270,180,90,0";
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "topLeft,#0";
        h1.yrange = "0,15429";
        this.Handles.Add(h1);
    }

}


