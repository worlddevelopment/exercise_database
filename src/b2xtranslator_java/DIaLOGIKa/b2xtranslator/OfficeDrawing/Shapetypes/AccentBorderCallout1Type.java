//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class AccentBorderCallout1Type  extends ShapeType 
{
    public AccentBorderCallout1Type() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m@0@1l@2@3nfem@2,l@2,21600nfem,l21600,r,21600l,21600xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("val #3");
        this.AdjustmentValues = "-8280,24300,-1800,4050";
        this.ConnectorLocations = "@0,@1;10800,0;10800,21600;0,10800;21600,10800";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,#1";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "#2,#3";
        this.Handles.Add(HandleTwo);
    }

}


