//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class ArcType  extends ShapeType 
{
    public ArcType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.round;
        this.Path = "wr-21600,,21600,43200,,,21600,21600nfewr-21600,,21600,43200,,,21600,21600l,21600nsxe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #2");
        this.Formulas.add("val #3");
        this.Formulas.add("val #4");
        this.AdjustmentValues = "-5898240,,,21600,21600";
        this.ConnectorLocations = "0,0;21600,21600;0,21600";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "@2,#0";
        HandleOne.polar = "@0,@1";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleOne.position = "@2,#1";
        HandleOne.polar = "@0,@1";
        this.Handles.Add(HandleTwo);
    }

}


