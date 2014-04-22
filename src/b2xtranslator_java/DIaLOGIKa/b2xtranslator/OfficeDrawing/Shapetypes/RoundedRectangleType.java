//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.RectangleType;

/**
* interim solution
* OOX uses an additional attribute: arcsize
*/
public class RoundedRectangleType  extends RectangleType 
{
    public RoundedRectangleType() throws Exception {
        super();
        this.Joins = JoinStyle.round;
        this.AdjustmentValues = "5400";
    }

}


