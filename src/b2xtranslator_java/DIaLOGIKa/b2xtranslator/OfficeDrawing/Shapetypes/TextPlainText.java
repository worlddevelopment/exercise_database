//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextPlainText  extends ShapeType 
{
    public TextPlainText() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "10800";
        this.Path = "m@7,l@8,m@5,21600l@6,21600e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("sum #0 0 10800");
        this.Formulas.add("prod #0 2 1");
        this.Formulas.add("sum 21600 0 @1");
        this.Formulas.add("sum 0 0 @2");
        this.Formulas.add("sum 21600 0 @3");
        this.Formulas.add("if @0 @3 0");
        this.Formulas.add("if @0 21600 @1");
        this.Formulas.add("if @0 0 @2");
        this.Formulas.add("if @0 @4 21600");
        this.Formulas.add("mid @5 @6");
        this.Formulas.add("mid @8 @5");
        this.Formulas.add("mid @7 @8");
        this.Formulas.add("mid @6 @7");
        this.Formulas.add("sum @6 0 @5");
        this.ConnectorLocations = "@9,0;@10,10800;@11,21600;@12,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "#0,bottomRight";
        h1.xrange = "6629,14971";
        this.Handles.Add(h1);
    }

}


