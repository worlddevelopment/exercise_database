//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextStop  extends ShapeType 
{
    public TextStop() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.AdjustmentValues = "4800";
        this.Path = "m0@0l7200,r7200,l21600@0m0@1l7200,21600r7200,l21600@1e";
        this.ConnectorType = "rect";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("sum 21600 0 @0");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "topLeft,#0";
        h1.yrange = "3086,10800";
        this.Handles.Add(h1);
    }

}


