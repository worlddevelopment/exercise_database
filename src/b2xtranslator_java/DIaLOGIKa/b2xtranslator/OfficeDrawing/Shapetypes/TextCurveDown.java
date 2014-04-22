//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextCurveDown  extends ShapeType 
{
    public TextCurveDown() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.AdjustmentValues = "9391";
        this.Path = "m,c7200@1,14400@2,21600@0m0@5c7200@6,14400@6,21600@5e";
        this.ConnectorLocations = "10800,@10;0,@8;10800,21600;21600,@9";
        this.ConnectorAngles = "270,180,90,0";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 3 4");
        this.Formulas.add("prod #0 5 4");
        this.Formulas.add("prod #0 3 8");
        this.Formulas.add("prod #0 1 8");
        this.Formulas.add("sum 21600 0 @3");
        this.Formulas.add("sum @4 21600 0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("prod @5 1 2");
        this.Formulas.add("sum @7 @8 0");
        this.Formulas.add("prod #0 7 8");
        this.Formulas.add("prod @5 1 3");
        this.Formulas.add("sum @1 @2 0");
        this.Formulas.add("sum @12 @0 0");
        this.Formulas.add("prod @13 1 4");
        this.Formulas.add("sum @11 14400 @14");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "bottomRight,#0";
        h1.yrange = "0,11368";
        this.Handles.Add(h1);
    }

}


