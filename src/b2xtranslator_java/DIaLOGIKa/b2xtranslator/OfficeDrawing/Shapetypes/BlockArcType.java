//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:34 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class BlockArcType  extends ShapeType 
{
    public BlockArcType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "al10800,10800@0@0@2@14,10800,10800,10800,10800@3@15xe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #1 ");
        this.Formulas.add("val #0 ");
        this.Formulas.add("sum 0 0 #0 ");
        this.Formulas.add("sumangle #0 0 180 ");
        this.Formulas.add("sumangle #0 0 90 ");
        this.Formulas.add("prod @4 2 1 ");
        this.Formulas.add("sumangle #0 90 0 ");
        this.Formulas.add("prod @6 2 1 ");
        this.Formulas.add("abs #0 ");
        this.Formulas.add("sumangle @8 0 90 ");
        this.Formulas.add(" if @9 @7 @5 ");
        this.Formulas.add("sumangle @10 0 360 ");
        this.Formulas.add("if @10 @11 @10 ");
        this.Formulas.add("sumangle @12 0 360 ");
        this.Formulas.add("if @12 @13 @12 ");
        this.Formulas.add("sum 0 0 @14 ");
        this.Formulas.add("val 10800 ");
        this.Formulas.add("sum 10800 0 #1 ");
        this.Formulas.add("prod #1 1 2 ");
        this.Formulas.add("sum @18 5400 0 ");
        this.Formulas.add("cos @19 #0 ");
        this.Formulas.add("sin @19 #0 ");
        this.Formulas.add("sum @20 10800 0 ");
        this.Formulas.add("sum @21 10800 0 ");
        this.Formulas.add("sum 10800 0 @20 ");
        this.Formulas.add("sum #1 10800 0 ");
        this.Formulas.add("if @9 @17 @25 ");
        this.Formulas.add("if @9 0 21600 ");
        this.Formulas.add("cos 10800 #0 ");
        this.Formulas.add("sin 10800 #0 ");
        this.Formulas.add("sin #1 #0 ");
        this.Formulas.add("sum @28 10800 0 ");
        this.Formulas.add("sum @29 10800 0 ");
        this.Formulas.add("sum @30 10800 0 ");
        this.Formulas.add("if @4 0 @31 ");
        this.Formulas.add("if #0 @34 0 ");
        this.Formulas.add("if @6 @35 @31 ");
        this.Formulas.add("sum 21600 0 @36 ");
        this.Formulas.add("if @4 0 @33 ");
        this.Formulas.add("if #0 @38 @32 ");
        this.Formulas.add("if @6 @39 0 ");
        this.Formulas.add("if @4 @32 21600");
        this.Formulas.add("if @6 @41 @33");
        this.AdjustmentValues = "11796480,5400";
        this.ConnectorLocations = "10800,@27;@22,@23;10800,@26;@24,@23";
        this.TextboxRectangle = "@36,@40,@37,@42";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#1,#0";
        HandleOne.polar = "10800,10800";
        HandleOne.radiusrange = "0,10800";
        HandleOne.switchHandle = "true";
        HandleOne.xrange = "0,10800";
        this.Handles.Add(HandleOne);
    }

}


