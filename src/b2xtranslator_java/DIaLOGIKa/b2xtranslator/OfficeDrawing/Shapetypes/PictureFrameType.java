//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:38 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class PictureFrameType  extends ShapeType 
{
    public PictureFrameType() throws Exception {
        this.Path = "m@4@5l@4@11@9@11@9@5xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("if lineDrawn pixelLineWidth 0");
        this.Formulas.add("sum @0 1 0");
        this.Formulas.add("sum 0 0 @1");
        this.Formulas.add("prod @2 1 2");
        this.Formulas.add("prod @3 21600 pixelWidth");
        this.Formulas.add("prod @3 21600 pixelHeight");
        this.Formulas.add("sum @0 0 1");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("prod @7 21600 pixelWidth");
        this.Formulas.add("sum @8 21600 0");
        this.Formulas.add("prod @7 21600 pixelHeight");
        this.Formulas.add("sum @10 21600 0");
        //pictures are not stroked or fileld by default
        this.Filled = false;
        this.Stroked = false;
        //pictures have a lock on the aspect ratio by default
        this.Lock = new ProtectionBooleans(0);
        this.Lock.fUsefLockAspectRatio = true;
        this.Lock.fLockAspectRatio = true;
        this.ShapeConcentricFill = true;
        this.ConnectorType = "rect";
    }

}


