//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BentArrowType  extends ShapeType 
{
    public BentArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,6079l@0,0@0@1,12427@1qx,12158l,21600@4,21600@4,12158qy12427@2l@0@2@0,12158xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0 ");
        this.Formulas.add("val #1 ");
        this.Formulas.add("sum 12158 0 #1 ");
        this.Formulas.add("sum @2 0 #1 ");
        this.Formulas.add("prod @3 32768 32059 ");
        this.Formulas.add("prod @4 1 2 ");
        this.Formulas.add("sum 21600 0 #0 ");
        this.Formulas.add("prod @6 #1 6079 ");
        this.Formulas.add("sum @7 #0 0");
        this.AdjustmentValues = "Connector Angles";
        this.ConnectorLocations = "@0,0;@0,12158;@5,21600;21600,6079";
        this.ConnectorAngles = "270,90,90,0";
        this.TextboxRectangle = "12427,@1,@8,@2;0,12158,@4,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        HandleOne.xrange = "12427,21600";
        HandleOne.yrange = "0,6079";
        this.Handles.Add(HandleOne);
    }

}


