//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CurvedLeftArrowType  extends ShapeType 
{
    public CurvedLeftArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "wr@22,0@21@3,,0@21@4@22@14@21@1@21@7@2@12l@2@13,0@8@2@11at@22,0@21@3@2@10@24@16@22@14@21@1@24@16,0@14xear@22@14@21@1@21@7@24@16nfe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("sum #0 width #1");
        this.Formulas.add("prod @3 1 2");
        this.Formulas.add("sum #1 #1 width");
        this.Formulas.add("sum @5 #1 #0");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("mid width #0");
        this.Formulas.add("ellipse #2 height @4");
        this.Formulas.add("sum @4 @9 0");
        this.Formulas.add("sum @10 #1 width");
        this.Formulas.add("sum @7 @9 0");
        this.Formulas.add("sum @11 width #0");
        this.Formulas.add("sum @5 0 #0");
        this.Formulas.add("prod @14 1 2");
        this.Formulas.add("mid @4 @7");
        this.Formulas.add("sum #0 #1 width");
        this.Formulas.add("prod @17 1 2");
        this.Formulas.add("sum @16 0 @18");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.Formulas.add("sum 0 0 height");
        this.Formulas.add("sum @16 0 @4");
        this.Formulas.add("ellipse @23 @4 height");
        this.Formulas.add("sum @8 128 0");
        this.Formulas.add("prod @5 1 2");
        this.Formulas.add("sum @5 0 128");
        this.Formulas.add("sum #0 @16 @11");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod @29 1 2");
        this.Formulas.add("prod height height 1");
        this.Formulas.add("prod #2 #2 1");
        this.Formulas.add("sum @31 0 @32");
        this.Formulas.add("sqrt @33");
        this.Formulas.add("sum @34 height 0");
        this.Formulas.add("prod width height @35");
        this.Formulas.add("sum @36 64 0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("ellipse @30 @38 height");
        this.Formulas.add("sum @39 0 64");
        this.Formulas.add("prod @4 1 2");
        this.Formulas.add("sum #1 0 @41");
        this.Formulas.add("prod height 4390 32768");
        this.Formulas.add("prod height 28378 32768");
        this.AdjustmentValues = "12960,19440,7200";
        this.ConnectorLocations = "0,@15;@2,@11;0,@8;@2,@13;@21,@16";
        this.ConnectorAngles = "180,180,180,90,0";
        this.TextboxRectangle = "@43,@41,@44,@42";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "topLeft,#0";
        HandleOne.yrange = "@37,@27";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleOne.position = "topLeft,#1";
        HandleOne.yrange = "@25,@20";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "#2,bottomRight";
        HandleThree.xrange = "0,@40";
        this.Handles.Add(HandleThree);
    }

}


