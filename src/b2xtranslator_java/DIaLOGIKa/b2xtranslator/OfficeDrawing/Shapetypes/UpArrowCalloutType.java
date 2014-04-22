//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class UpArrowCalloutType  extends ShapeType 
{
    public UpArrowCalloutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m0@0l@3@0@3@2@1@2,10800,0@4@2@5@2@5@0,21600@0,21600,21600,,21600xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("val #3");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("sum 21600 0 #3");
        this.Formulas.add("sum #0 21600 0");
        this.Formulas.add("prod @6 1 2");
        this.AdjustmentValues = "7200,5400,3600,8100";
        this.ConnectorLocations = "10800,0;0,@7;10800,21600;21600,@7";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "0,@0,21600,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.yrange = "@2,21600";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#1,topLeft";
        HandleTwo.xrange = "0,@3";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "#3,#2";
        HandleThree.xrange = "@1,10800";
        HandleThree.yrange = "0,@0";
        this.Handles.Add(HandleThree);
    }

}


