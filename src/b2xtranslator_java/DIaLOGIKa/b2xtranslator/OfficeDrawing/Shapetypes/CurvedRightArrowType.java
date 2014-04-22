//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:35 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CurvedRightArrowType  extends ShapeType 
{
    public CurvedRightArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "ar,0@23@3@22,,0@4,0@15@23@1,0@7@2@13l@2@14@22@8@2@12wa,0@23@3@2@11@26@17,0@15@23@1@26@17@22@15xear,0@23@3,0@4@26@17nfe";
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
        this.Formulas.add("sum height 0 #2");
        this.Formulas.add("ellipse @9 height @4");
        this.Formulas.add("sum @4 @10 0");
        this.Formulas.add("sum @11 #1 width");
        this.Formulas.add("sum @7 @10 0");
        this.Formulas.add("sum @12 width #0");
        this.Formulas.add("sum @5 0 #0");
        this.Formulas.add("prod @15 1 2");
        this.Formulas.add("mid @4 @7");
        this.Formulas.add("sum #0 #1 width");
        this.Formulas.add("prod @18 1 2");
        this.Formulas.add("sum @17 0 @19");
        this.Formulas.add("val width");
        this.Formulas.add("val height");
        this.Formulas.add("prod height 2 1");
        this.Formulas.add("sum @17 0 @4");
        this.Formulas.add("ellipse @24 @4 height");
        this.Formulas.add("sum height 0 @25");
        this.Formulas.add("sum @8 128 0");
        this.Formulas.add("prod @5 1 2");
        this.Formulas.add("sum @5 0 128");
        this.Formulas.add("sum #0 @17 @12");
        this.Formulas.add("ellipse @20 @4 height");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("prod @32 1 2");
        this.Formulas.add("prod height height 1");
        this.Formulas.add("prod @9 @9 1");
        this.Formulas.add("sum @34 0 @35");
        this.Formulas.add("sqrt @36");
        this.Formulas.add("sum @37 height 0");
        this.Formulas.add("prod width height @38");
        this.Formulas.add("sum @39 64 0");
        this.Formulas.add("prod #0 1 2");
        this.Formulas.add("ellipse @33 @41 height");
        this.Formulas.add("sum height 0 @42");
        this.Formulas.add("sum @43 64 0");
        this.Formulas.add("prod @4 1 2");
        this.Formulas.add("sum #1 0 @45");
        this.Formulas.add("prod height 4390 32768");
        this.Formulas.add("prod height 28378 32768");
        this.AdjustmentValues = "12960,19440,14400";
        this.ConnectorLocations = "0,@17;@2,@14;@22,@8;@2,@12;@22,@16";
        this.ConnectorAngles = "180,90,0,0,0";
        this.TextboxRectangle = "@47,@45,@48,@46";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "bottomRight,#0";
        HandleOne.yrange = "@40,@29";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleOne.position = "bottomRight,#1";
        HandleOne.yrange = "@27,@21";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "#2,bottomRight";
        HandleThree.xrange = "@44,@22";
        this.Handles.Add(HandleThree);
    }

}


