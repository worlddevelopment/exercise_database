//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class EllipseRibbonType  extends ShapeType 
{
    public EllipseRibbonType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "ar@9@38@8@37,0@27@0@26@9@13@8@4@0@25@22@25@9@38@8@37@22@26@3@27l@7@40@3,wa@9@35@8@10@3,0@21@33@9@36@8@1@21@31@20@31@9@35@8@10@20@33,,l@5@40xewr@9@36@8@1@20@31@0@32nfl@20@33ear@9@36@8@1@21@31@22@32nfl@21@33em@0@26nfl@0@32em@22@26nfl@22@32e";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2 ");
        this.Formulas.add("val width ");
        this.Formulas.add("val height ");
        this.Formulas.add("prod width 1 8");
        this.Formulas.add("prod width 1 2 ");
        this.Formulas.add("prod width 7 8 ");
        this.Formulas.add("prod width 3 2 ");
        this.Formulas.add("sum 0 0 @6 ");
        this.Formulas.add("sum height 0 #2");
        this.Formulas.add("prod @10 30573 4096");
        this.Formulas.add("prod @11 2 1 ");
        this.Formulas.add("sum height 0 @12");
        this.Formulas.add("sum @11 #2 0 ");
        this.Formulas.add("sum @11 height #1");
        this.Formulas.add("sum height 0 #1 ");
        this.Formulas.add("prod @16 1 2 ");
        this.Formulas.add("sum @11 @17 0 ");
        this.Formulas.add("sum @14 #1 height");
        this.Formulas.add("sum #0 @5 0 ");
        this.Formulas.add("sum width 0 @20");
        this.Formulas.add("sum width 0 #0");
        this.Formulas.add("sum @6 0 #0");
        this.Formulas.add("ellipse @23 width @11 ");
        this.Formulas.add("sum @24 height @11 ");
        this.Formulas.add("sum @25 @11 @19 ");
        this.Formulas.add("sum #2 @11 @19 ");
        this.Formulas.add("prod @11 2391 32768 ");
        this.Formulas.add("sum @6 0 @20 ");
        this.Formulas.add("ellipse @29 width @11 ");
        this.Formulas.add("sum #1 @30 @11 ");
        this.Formulas.add("sum @25 #1 height ");
        this.Formulas.add("sum height @30 @14 ");
        this.Formulas.add("sum @11 @14 0 ");
        this.Formulas.add("sum height 0 @34 ");
        this.Formulas.add("sum @35 @19 @11 ");
        this.Formulas.add("sum @10 @15 @11 ");
        this.Formulas.add("sum @35 @15 @11 ");
        this.Formulas.add("sum @28 @14 @18 ");
        this.Formulas.add("sum height 0 @39 ");
        this.Formulas.add("sum @19 0 @18 ");
        this.Formulas.add("prod @41 2 3 ");
        this.Formulas.add("sum #1 0 @42 ");
        this.Formulas.add("sum #2 0 @42 ");
        this.Formulas.add("min @44 20925 ");
        this.Formulas.add("prod width 3 8 ");
        this.Formulas.add("sum @46 0 4");
        this.AdjustmentValues = "5400,5400,18900";
        this.ConnectorLocations = "@6,@1;@5,@40;@6,@4;@7,@40";
        this.ConnectorAngles = "270,180,90,0";
        this.TextboxRectangle = "@0,@1,@22,@25";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,bottomRight";
        HandleOne.xrange = "@5,@47";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleTwo.position = "center,#1";
        HandleTwo.yrange = "@10,@43";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "topLeft,#2";
        HandleThree.yrange = "@27,@45";
        this.Handles.Add(HandleThree);
    }

}


