//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextRingInside  extends ShapeType 
{
    public TextRingInside() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "13500";
        this.Path = "m0@1qy10800,,21600@1,10800@0,0@1m0@2qy10800@3,21600@2,10800,21600,0@2e";
        this.ConnectorType = "custom";
        this.ConnectorLocations = "10800,0;10800,@0;0,10800;10800,21600;10800,@3;21600,10800";
        this.ConnectorAngles = "270,270,180,90,90,0";
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("sum height 0 @1");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("sum @2 0 @1");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "center,#0";
        h1.yrange = "10800,21600";
        this.Handles.Add(h1);
    }

}


