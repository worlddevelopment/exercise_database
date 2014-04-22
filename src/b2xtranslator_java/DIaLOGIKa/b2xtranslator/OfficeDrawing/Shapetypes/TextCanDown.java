//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextCanDown  extends ShapeType 
{
    public TextCanDown() throws Exception {
        this.TextPath = true;
        this.Path = "m,qy10800@0,21600,m0@1qy10800,21600,21600@1e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("prod @1 1 2");
        this.Formulas.add("sum @2 10800 0");
        this.ConnectorLocations = "10800,@0;0,@2;10800,21600;21600,@2";
        this.ConnectorAngles = "270,180,90,0";
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "center,#0";
        h1.yrange = "0,7200";
        this.Handles.Add(h1);
    }

}


