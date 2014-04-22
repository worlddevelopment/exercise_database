//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextDeflate  extends ShapeType 
{
    public TextDeflate() throws Exception {
        this.TextPath = true;
        this.Path = "m,c7200@0,14400@0,21600,m,21600c7200@1,14400@1,21600,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("prod #0 4 3");
        this.Formulas.add("sum 21600 0 @0");
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 #0");
        this.ConnectorLocations = "10800,@2;0,10800;10800,@3;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "center,#0";
        h1.yrange = "0,8100";
        this.Handles.Add(h1);
    }

}


