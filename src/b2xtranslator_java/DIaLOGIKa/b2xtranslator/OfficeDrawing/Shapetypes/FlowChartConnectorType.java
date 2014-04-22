//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class FlowChartConnectorType  extends ShapeType 
{
    public FlowChartConnectorType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.round;
        this.Path = "m10800,qx,10800,10800,21600,21600,10800,10800,xe";
        this.ConnectorLocations = "10800,0;3163,3163;0,10800;3163,18437;10800,21600;18437,18437;21600,10800;18437,3163";
        this.TextboxRectangle = "3163,3163,18437,18437";
    }

}


