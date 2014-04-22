//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class QuadArrowType  extends ShapeType 
{
    public QuadArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "m10800,l@0@2@1@2@1@1@2@1@2@0,,10800@2@3@2@4@1@4@1@5@0@5,10800,21600@3@5@4@5@4@4@5@4@5@3,21600,10800@5@0@5@1@4@1@4@2@3@2xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0 ");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2 ");
        this.Formulas.add("sum 21600 0 #0 ");
        this.Formulas.add("sum 21600 0 #1 ");
        this.Formulas.add("sum 21600 0 #2 ");
        this.Formulas.add("sum #0 0 10800 ");
        this.Formulas.add("sum #1 0 10800 ");
        this.Formulas.add("prod @7 #2 @6 ");
        this.Formulas.add("sum 21600 0 @8");
        this.AdjustmentValues = "6480,8640,4320";
        this.ConnectorLocations = "Rectangle";
        this.TextboxRectangle = "@8,@1,@9,@4;@1,@8,@4,@9";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "@2,@1";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#1,#2";
        HandleTwo.xrange = "@0,10800";
        HandleTwo.yrange = "0,@0";
        this.Handles.Add(HandleTwo);
    }

}


