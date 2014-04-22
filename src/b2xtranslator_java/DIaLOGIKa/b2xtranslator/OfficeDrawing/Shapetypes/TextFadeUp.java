//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextFadeUp  extends ShapeType 
{
    public TextFadeUp() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "7200";
        this.Path = "m@0,l@1,m,21600r21600,e";
        this.ConnectorLocations = "10800,0;@2,10800;10800,21600;@3,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 @0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum 21600 0 @2");
        this.Formulas.add("sum @1 21600 @0");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "#0,topLeft";
        h1.xrange = "0,10792";
        this.Handles.Add(h1);
    }

}


