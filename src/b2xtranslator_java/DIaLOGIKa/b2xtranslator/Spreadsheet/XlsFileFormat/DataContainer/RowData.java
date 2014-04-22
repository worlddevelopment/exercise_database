//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:35 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.AbstractCellData;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* This class stores the rowdata from a specific row
*/
public class RowData   
{
    /**
    * The row number
    */
    private int row;
    public int getRow() throws Exception {
        return row;
    }

    public void setRow(int value) throws Exception {
        row = value;
    }

    /**
    * Collection of cellobjects
    */
    private CSList<AbstractCellData> cells;
    public CSList<AbstractCellData> getCells() throws Exception {
        return cells;
    }

    public void setCells(CSList<AbstractCellData> value) throws Exception {
        cells = value;
    }

    public TwipsValue height;
    public boolean hidden;
    public int outlineLevel;
    public boolean collapsed;
    public boolean customFormat;
    public int style;
    public boolean thickBot;
    public boolean thickTop;
    public boolean customHeight;
    public int minSpan;
    public int maxSpan;
    /**
    * Ctor
    */
    public RowData() throws Exception {
        this(0);
        this.outlineLevel = -1;
        this.minSpan = -1;
        this.maxSpan = -1;
    }

    /**
    * Ctor
    * 
    *  @param row Rowid
    */
    public RowData(int row) throws Exception {
        this.row = row;
        this.cells = new CSList<AbstractCellData>();
    }

    /**
    * Add a cellobject to the collection
    * 
    *  @param cell Cellobject
    */
    public void addCell(AbstractCellData cell) throws Exception {
        if (!this.checkCellExists(cell))
            this.cells.add(cell);
         
    }

    /**
    * method checks if a cell exists or not
    * 
    *  @param cell 
    *  @return true if the cell exists and false if not
    */
    public boolean checkCellExists(AbstractCellData cell) throws Exception {
        for (AbstractCellData var : this.cells)
        {
            if (var.getCol() == cell.getCol())
            {
                return true;
            }
             
        }
        return false;
    }

}


