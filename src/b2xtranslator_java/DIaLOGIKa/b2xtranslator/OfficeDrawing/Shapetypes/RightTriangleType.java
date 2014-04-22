//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class RightTriangleType  extends ShapeType 
{
    public RightTriangleType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m,l,21600r21600,xe";
        this.ConnectorLocations = "0,0;0,10800;0,21600;10800,21600;21600,21600;10800,10800";
        this.TextboxRectangle = "1800,12600,12600,19800";
    }

}


