//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class PentagonType  extends ShapeType 
{
    public PentagonType() throws Exception {
        this.ShapeConcentricFill = true;
        this.Joins = JoinStyle.miter;
        this.Path = "m10800,l,8259,4200,21600r13200,l21600,8259xe";
        this.ConnectorLocations = "10800,0;0,8259;4200,21600;10800,21600;17400,21600;21600,8259";
        this.ConnectorAngles = "270,180,90,90,90,0";
        this.TextboxRectangle = "4200,5077,17400,21600";
    }

}


