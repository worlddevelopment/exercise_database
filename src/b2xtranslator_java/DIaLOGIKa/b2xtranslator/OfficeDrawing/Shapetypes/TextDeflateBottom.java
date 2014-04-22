//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextDeflateBottom  extends ShapeType 
{
    public TextDeflateBottom() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "11475";
        this.Path = "m,l21600,m,21600c7200@1,14400@1,21600,21600e";
        this.ConnectorLocations = "10800,0;0,10800;10800,@2;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("prod #0 4 3");
        this.Formulas.add("sum @0 0 7200");
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 2 3");
        this.Formulas.add("sum @3 7200 0");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "center,#0";
        h1.yrange = "1350,21600";
        this.Handles.Add(h1);
    }

}


