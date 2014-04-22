//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:07 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;

public class TC80   
{
    public TC80() {
    }

    /**
    * A value from the following table that specifies how this cell merges horizontally with the neighboring cells in the same row. 
    * MUST be one of the following values:
    * 0        The cell is not merged with the cells on either side of it.
    * 1        The cell is one of a set of horizontally merged cells. It contributes its layout region to the set and its own contents are not rendered.
    * 2, 3     The cell is the first cell in a set of horizontally merged cells. The contents and formatting of this cell extend into any consecutive cells following it that are designated as part of the merged set.
    */
    public byte horzMerge;
    /**
    * A value from the TextFlow enumeration that specifies rotation settings for the text in the cell.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextFlow textFlow = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextFlow.lrTb;
    /**
    * A value from the VerticalMergeFlag enumeration that specifies how this cell merges vertically with the cells above or below it.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag vertMerge = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag.fvmClear;
    /**
    * A value from the VerticalAlign enumeration that specifies how contents inside this cell are aligned.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalAlign vertAlign = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalAlign.top;
    /**
    * An Fts that specifies the unit of measurement for the wWidth field in the TC80 structure.
    */
    public DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType ftsWidth = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType.nil;
    /**
    * Specifies whether the contents of the cell are to be stretched out such that the full cell width is used.
    */
    public boolean fFitText;
    /**
    * When set, specifies that the preferred layout of the contents of this cell are as a single line,
    * and cell widths can be adjusted to accommodate long lines. 
    * This preference is ignored when the preferred width of this cell is set to ftsDxa.
    */
    public boolean fNoWrap;
    /**
    * When set, specifies that this cell is rendered with no height if all cells in the row are empty.
    */
    public boolean fHideMark;
    /**
    * An integer that specifies the preferred width of the cell.
    * The width includes cell margins, but does not include cell spacing. MUST be non-negative.
    * The unit of measurement depends on ftsWidth.
    * If ftsWidth is set to ftsPercent, the value is a fraction of the width of the entire table.
    */
    public short wWidth;
    public BorderCode brcTop;
    public BorderCode brcLeft;
    public BorderCode brcBottom;
    public BorderCode brcRight;
}


