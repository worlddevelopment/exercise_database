//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class DonutType  extends ShapeType 
{
    public DonutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.round;
        this.Path = "m,10800qy10800,,21600,10800,10800,21600,,10800xm@0,10800qy10800@2@1,10800,10800@0@0,10800xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("prod @0 2929 10000");
        this.Formulas.add("sum width 0 @3");
        this.Formulas.add("sum height 0 @3");
        this.AdjustmentValues = "5400";
        this.ConnectorLocations = "10800,0;3163,3163;0,10800;3163,18437;10800,21600;18437,18437;21600,10800;18437,3163";
        this.TextboxRectangle = "3163,3163,18437,18437";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,center";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


