//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:37 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class LeftRightArrowCalloutType  extends ShapeType 
{
    public LeftRightArrowCalloutType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0,l@0@3@2@3@2@1,,10800@2@4@2@5@0@5@0,21600@8,21600@8@5@9@5@9@4,21600,10800@9@1@9@3@8@3@8,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("val #3");
        this.Formulas.add("sum 21600 0 #1");
        this.Formulas.add("sum 21600 0 #3");
        this.Formulas.add("sum #0 21600 0");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("sum 21600 0 #0");
        this.Formulas.add("sum 21600 0 #2");
        this.AdjustmentValues = "5400,5400,2700,8100";
        this.ConnectorLocations = "10800,0;0,10800;10800,21600;21600,10800";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@0,0,@8,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "@2,10800";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "topLeft,#1";
        HandleTwo.yrange = "0,@3";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "#2,#3";
        HandleThree.xrange = "0,@0";
        HandleThree.yrange = "@1,10800";
        this.Handles.Add(HandleThree);
    }

}


