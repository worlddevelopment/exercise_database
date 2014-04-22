//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class DownArrowCalloutType  extends ShapeType 
{
    public DownArrowCalloutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l21600,,21600@0@5@0@5@2@4@2,10800,21600@1@2@3@2@3@0,0@0xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("val #3");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("sum 21600 0 #3");
        this.Formulas.add("prod #0 1 2");
        this.AdjustmentValues = "14400,5400,18000,8100";
        this.ConnectorLocations = "10800,0;0,@6;10800,21600;21600,@6";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "0,0,21600,@0";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.yrange = "0,@2";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#1,bottomRight";
        HandleTwo.xrange = "0,@3";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "#3,#2";
        HandleThree.xrange = "@1,10800";
        HandleThree.yrange = "@0,21600";
        this.Handles.Add(HandleThree);
    }

}


