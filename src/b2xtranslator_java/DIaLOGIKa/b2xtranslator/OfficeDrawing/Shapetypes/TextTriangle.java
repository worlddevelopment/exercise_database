//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextTriangle  extends ShapeType 
{
    public TextTriangle() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "10800";
        this.Path = "m0@0l10800,,21600@0m,21600r10800,l21600,21600e";
        this.ConnectorLocations = "10800,0;5400,@1;10800,21600;16200,@1";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum @1 10800 0");
        this.Formulas.add("sum 21600 0 @1");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "topLeft,#0";
        h1.yrange = "0,21600";
        this.Handles.Add(h1);
    }

}


