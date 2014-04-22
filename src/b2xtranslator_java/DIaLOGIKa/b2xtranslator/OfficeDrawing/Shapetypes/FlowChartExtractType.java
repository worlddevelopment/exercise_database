//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class FlowChartExtractType  extends ShapeType 
{
    public FlowChartExtractType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m10800,l21600,21600,,21600xe";
        this.ConnectorLocations = "10800,0;5400,10800;10800,21600;16200,10800";
        this.TextboxRectangle = "5400,10800,16200,21600";
    }

}


