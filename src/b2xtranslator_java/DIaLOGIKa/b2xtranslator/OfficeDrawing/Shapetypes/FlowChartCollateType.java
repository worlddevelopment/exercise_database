//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class FlowChartCollateType  extends ShapeType 
{
    public FlowChartCollateType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m21600,21600l,21600,21600,,,xe";
        this.ConnectorLocations = "10800,0;10800,10800;10800,21600";
        this.TextboxRectangle = "5400,5400,16200,16200";
    }

}


