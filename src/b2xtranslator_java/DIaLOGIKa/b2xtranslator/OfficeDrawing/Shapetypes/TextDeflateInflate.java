//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:40 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class TextDeflateInflate  extends ShapeType 
{
    public TextDeflateInflate() throws Exception {
        this.TextPath = true;
        this.Joins = JoinStyle.none;
        this.AdjustmentValues = "6054";
        this.Path = "m,l21600,m,10125c7200@1,14400@1,21600,10125m,11475c7200@2,14400@2,21600,11475m,21600r21600,e";
        this.ConnectorType = "rect";
        this.ExtrusionOk = true;
        this.Lock = new ProtectionBooleans();
        this.Lock.fUsefLockText = true;
        this.Lock.fLockText = true;
        this.LockShapeType = true;
        this.Formulas = new CSList<String>();
        this.Formulas.add("prod #0 4 3");
        this.Formulas.add("sum @0 0 4275");
        this.Formulas.add("sum @0 0 2925");
        this.Handles = new CSList<Handle>();
        Handle h1 = new Handle();
        h1.position = "center,#0";
        h1.yrange = "1308,20292";
        this.Handles.Add(h1);
    }

}


