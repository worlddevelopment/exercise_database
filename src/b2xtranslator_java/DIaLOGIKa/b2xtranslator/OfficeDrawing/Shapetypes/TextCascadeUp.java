//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextCascadeUp  extends ShapeType 
{
    public TextCascadeUp() throws Exception {
        this.TextPath = true;
        this.Path = "m0@2l21600,m,21600l21600@0e";
        this.ConnectorLocations = "10800,@4;0,@6;10800,@5;21600,@3";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("prod @1 1 4");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("prod @2 1 2");
        this.Formulas.add("sum @3 10800 0");
        this.Formulas.add("sum @4 10800 0");
        this.Formulas.add("sum @0 21600 @2");
        this.Formulas.add("prod @7 1 2");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "bottomRight,#0";
        h1.yrange = "6171,21600";
        this.Handles.Add(h1);
    }

}


