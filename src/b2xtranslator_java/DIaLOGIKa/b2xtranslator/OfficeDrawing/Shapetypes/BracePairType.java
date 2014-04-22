//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BracePairType  extends ShapeType 
{
    public BracePairType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        //Encaps: Flat
        this.Path = "m@9,nfqx@0@0l@0@7qy0@4@0@8l@0@6qy@9,21600em@10,nfqx@5@0l@5@7qy21600@4@5@8l@5@6qy@10,21600em@9,nsqx@0@0l@0@7qy0@4@0@8l@0@6qy@9,21600l@10,21600qx@5@6l@5@8qy21600@4@5@7l@5@0qy@10,xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.Formulas.add("prod width 1 2");
        this.Formulas.add("prod height 1 2");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum height 0 #0");
        this.Formulas.add("sum @4 0 #0");
        this.Formulas.add("sum @4 #0 0");
        this.Formulas.add("prod #0 2 1");
        this.Formulas.add("sum width 0 @9");
        this.Formulas.add("prod #0 9598 32768");
        this.Formulas.add("sum height 0 @11");
        this.Formulas.add("sum @11 #0 0");
        this.Formulas.add("sum width 0 @13");
        this.AdjustmentValues = "1800";
        this.ConnectorLocations = "@3,0;0,@4;@3,@2;@1,@4";
        this.TextboxRectangle = "@13,@11,@14,@12";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.switchHandle = "true";
        HandleOne.yrange = "0,5400";
        this.Handles.Add(HandleOne);
        this.Limo = "10800,10800";
    }

}


