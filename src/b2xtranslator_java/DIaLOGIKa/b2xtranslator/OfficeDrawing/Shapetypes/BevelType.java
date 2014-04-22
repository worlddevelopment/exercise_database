//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BevelType  extends ShapeType 
{
    public BevelType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l,21600r21600,l21600,xem@0@0nfl@0@2@1@2@1@0xem,nfl@0@0em,21600nfl@0@2em21600,21600nfl@1@2em21600,nfl@1@0e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("prod height 1 2");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("prod #0 3 2");
        this.Formulas.add("sum @1 @5 0");
        this.Formulas.add("sum @2 @5 0");
        this.AdjustmentValues = "2700";
        this.ConnectorLocations = "0,@4;@0,@4;@3,21600;@3,@2;21600,@4;@1,@4;@3,0;@3,@0";
        this.TextboxRectangle = "@0,@0,@1,@2";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.switchHandle = "true";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
        this.Limo = "10800,10800";
    }

}


