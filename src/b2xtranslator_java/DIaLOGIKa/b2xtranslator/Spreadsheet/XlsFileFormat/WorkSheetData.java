//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:36 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.AbstractCellData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.BlankCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.ColumnInfoData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.FormulaCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.HyperlinkData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.NumberCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.RowData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SharedFormulaData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.StringCell;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ARRAY;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Blank;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ColInfo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.DefaultRowHeight;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Formula;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.HLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LabelSst;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MergeCells;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MulBlank;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MulRk;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Number;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.RK;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Row;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Setup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ShrFmla;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkSheetData;
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
* This class stores the data from every Boundsheet
*/
public class WorkSheetData  extends SheetData 
{
    /**
    * List with the cellrecords from the boundsheet
    */
    public CSList<LabelSst> LABELSSTList;
    public CSList<MulRk> MULRKList;
    public CSList<Number> NUMBERList;
    public CSList<RK> SINGLERKList;
    public CSList<Blank> BLANKList;
    public CSList<MulBlank> MULBLANKList;
    public CSList<Formula> FORMULAList;
    public ObjectsSequence ObjectsSequence;
    // TODO
    public CSList<ARRAY> ARRAYList;
    public CSList<HyperlinkData> HyperLinkList;
    public SortedList<Integer, RowData> rowDataTable = new SortedList<Integer, RowData>();
    public CSList<ColumnInfoData> colInfoDataTable;
    public CSList<SharedFormulaData> sharedFormulaDataTable;
    public FormulaCell latestFormula;
    public MergeCells MERGECELLSData;
    // Default values for the worksheet
    public int defaultColWidth;
    public int defaultRowHeight;
    public boolean zeroHeight;
    public boolean customHeight;
    public boolean thickTop;
    public boolean thickBottom;
    public Double leftMargin;
    // Margins
    public Double rightMargin;
    public Double topMargin;
    public Double bottomMargin;
    public Double headerMargin;
    public Double footerMargin;
    // PageSetup
    private Setup pageSetup;
    public Setup getPageSetup() throws Exception {
        return this.pageSetup;
    }

    /**
    * Ctor
    */
    public WorkSheetData() throws Exception {
        this.LABELSSTList = new CSList<LabelSst>();
        this.MULRKList = new CSList<MulRk>();
        this.NUMBERList = new CSList<Number>();
        this.SINGLERKList = new CSList<RK>();
        this.MULBLANKList = new CSList<MulBlank>();
        this.BLANKList = new CSList<Blank>();
        this.FORMULAList = new CSList<Formula>();
        this.ARRAYList = new CSList<ARRAY>();
        this.rowDataTable = new SortedList<Integer, RowData>();
        this.sharedFormulaDataTable = new CSList<SharedFormulaData>();
        this.colInfoDataTable = new CSList<ColumnInfoData>();
        this.HyperLinkList = new CSList<HyperlinkData>();
        boundsheetRecord = null;
        this.defaultRowHeight = -1;
        this.defaultColWidth = -1;
    }

    /**
    * Adds a labelsst element to the internal list
    * 
    *  @param labelsstdata A LABELSSTData element
    */
    public void addLabelSST(LabelSst labelsst) throws Exception {
        this.LABELSSTList.add(labelsst);
        RowData rowData = this.getSpecificRow(labelsst.rw);
        StringCell cell = new StringCell();
        cell.setValue(labelsst.isst);
        cell.setCol(labelsst.col);
        cell.setRow(labelsst.rw);
        cell.setTemplateID(labelsst.ixfe);
        rowData.addCell(cell);
    }

    /**
    * Adds a mulrk record element to the internal list
    * a mulrk record stores some integer or floatingpoint values
    * 
    *  @param mulrk The MULRK Record
    */
    public void addMULRK(MulRk mulrk) throws Exception {
        this.MULRKList.add(mulrk);
        RowData rowData = this.getSpecificRow(mulrk.rw);
        if (mulrk.ixfe.size() == mulrk.rknumber.size())
        {
            for (int i = 0;i < mulrk.rknumber.size();i++)
            {
                NumberCell cell = new NumberCell();
                cell.setCol(mulrk.colFirst + i);
                cell.setRow(mulrk.rw);
                cell.setValue(mulrk.rknumber.get(i));
                cell.setTemplateID(mulrk.ixfe.get(i));
                rowData.addCell(cell);
            }
        }
         
    }

    /**
    * Adds a NUMBER Biffrecord to the internal list
    * additional the method adds the specific NUMBER Data to a data container
    * 
    *  @param number NUMBER Biffrecord
    */
    public void addNUMBER(Number number) throws Exception {
        this.NUMBERList.add(number);
        RowData rowData = this.getSpecificRow(number.rw);
        NumberCell cell = new NumberCell();
        cell.setValue(number.num);
        cell.setCol(number.col);
        cell.setRow(number.rw);
        cell.setTemplateID(number.ixfe);
        rowData.addCell(cell);
    }

    /**
    * Adds a RK Biffrecord to the internal list
    * additional the method adds the specific RK Data to a data container
    * 
    *  @param number NUMBER Biffrecord
    */
    public void addRK(RK singlerk) throws Exception {
        this.SINGLERKList.add(singlerk);
        RowData rowData = this.getSpecificRow(singlerk.rw);
        NumberCell cell = new NumberCell();
        cell.setValue(singlerk.num);
        cell.setCol(singlerk.col);
        cell.setRow(singlerk.rw);
        cell.setTemplateID(singlerk.ixfe);
        rowData.addCell(cell);
    }

    /**
    * Adds a BLANK Biffrecord to the internal list
    * additional the method adds the specific BLANK Data to a data container
    * 
    *  @param number NUMBER Biffrecord
    */
    public void addBLANK(Blank blank) throws Exception {
        this.BLANKList.add(blank);
        RowData rowData = this.getSpecificRow(blank.rw);
        BlankCell cell = new BlankCell();
        cell.setCol(blank.col);
        cell.setRow(blank.rw);
        cell.setTemplateID(blank.ixfe);
        rowData.addCell(cell);
    }

    /**
    * Adds a mulblank record element to the internal list
    * a mulblank record stores some blank cells and their formating
    * 
    *  @param mulrk The MULRK Record
    */
    public void addMULBLANK(MulBlank mulblank) throws Exception {
        this.MULBLANKList.add(mulblank);
        RowData rowData = this.getSpecificRow(mulblank.rw);
        for (int i = 0;i < mulblank.ixfe.size();i++)
        {
            BlankCell cell = new BlankCell();
            cell.setCol(mulblank.colFirst + i);
            cell.setRow(mulblank.rw);
            cell.setTemplateID(mulblank.ixfe.get(i));
            rowData.addCell(cell);
        }
    }

    /**
    * Adds a formula BIFF RECORD to the formula list and
    * creates a new cell
    * 
    *  @param formula
    */
    public void addFORMULA(Formula formula) throws Exception {
        this.FORMULAList.add(formula);
        RowData rowData = this.getSpecificRow(formula.rw);
        FormulaCell cell = new FormulaCell();
        cell.setValue(formula.ptgStack);
        cell.setCol(formula.col);
        cell.setRow(formula.rw);
        cell.setTemplateID(formula.ixfe);
        cell.alwaysCalculated = formula.fAlwaysCalc;
        if (formula.fShrFmla)
        {
            ((FormulaCell)cell).isSharedFormula = true;
        }
         
        cell.calculatedValue = formula.calculatedValue;
        if (formula.boolValueSet)
        {
            cell.calculatedValue = formula.boolValue;
        }
        else if (formula.errorValue != 0)
        {
            cell.calculatedValue = formula.errorValue;
        }
          
        this.latestFormula = cell;
        rowData.addCell(cell);
    }

    /**
    * Add a stringvalue to the formula
    * 
    *  @param formulaString
    */
    public void addFormulaString(String formulaString) throws Exception {
        this.latestFormula.calculatedValue = formulaString;
    }

    /**
    * Adds an ARRAY BIFF Record to the arraylist
    * 
    *  @param array
    */
    //TODO
    public void addARRAY(ARRAY array) throws Exception {
        this.ARRAYList.add(array);
    }

    /**
    * Looks for a specific row number and if it doesn't exist the method will create the one.
    * 
    *  @param rownumber the specific rownumber as integer
    *  @return
    */
    public RowData getSpecificRow(int rownumber) throws Exception {
        RowData rowData;
        if (this.rowDataTable.ContainsKey(rownumber))
        {
            rowData = (RowData)this.rowDataTable[rownumber];
        }
        else
        {
            rowData = new RowData(rownumber);
            this.rowDataTable.Add(rownumber, rowData);
        } 
        return rowData;
    }

    /**
    * Returns the stack at the position of the given values
    * 
    *  @param rw 
    *  @param col 
    *  @return
    */
    public Stack<AbstractPtg> getArrayData(UInt16 rw, UInt16 col) throws Exception {
        Stack<AbstractPtg> stack = new Stack<AbstractPtg>();
        for (ARRAY array : this.ARRAYList)
        {
            if (array.colFirst == col && array.rwFirst == rw)
            {
                stack = array.ptgStack;
            }
             
        }
        return stack;
    }

    /**
    * Searches a cell at the specific Position
    * 
    *  @param rw 
    *  @param col 
    *  @return
    */
    public AbstractCellData getCellAtPosition(UInt16 rw, UInt16 col) throws Exception {
        RowData rd = this.getSpecificRow((int)rw);
        AbstractCellData scell = null;
        for (AbstractCellData cell : rd.getCells())
        {
            if (cell.getRow() == rw && cell.getCol() == col)
                scell = cell;
             
        }
        return scell;
    }

    /**
    * Sets the value from a Formula Cell
    * 
    *  @param rw 
    *  @param col
    */
    public void setFormulaUsesArray(UInt16 rw, UInt16 col) throws Exception {
        AbstractCellData cell = this.getCellAtPosition(rw,col);
        if (cell instanceof FormulaCell)
        {
            ((FormulaCell)cell).usesArrayRecord = true;
        }
         
    }

    /**
    * Add a shared formula to the internal list
    * 
    *  @param shrfmla
    */
    public void addSharedFormula(ShrFmla shrfmla) throws Exception {
        SharedFormulaData sfd = new SharedFormulaData();
        sfd.colFirst = shrfmla.colFirst;
        sfd.colLast = shrfmla.colLast;
        sfd.rwFirst = shrfmla.rwFirst;
        sfd.rwLast = shrfmla.rwLast;
        sfd.setValue(shrfmla.ptgStack);
        int ID = this.sharedFormulaDataTable.size();
        sfd.ID = ID;
        this.sharedFormulaDataTable.add(sfd);
    }

    /**
    * Checks if the formula cell with this coordinates is in the shared formula range
    * 
    *  @param rw 
    *  @param col 
    *  @return Null if the cell isn't in a SharedFormula range
    * The SharedFormulaData Objekt if the cell is in the range
    */
    public SharedFormulaData checkFormulaIsInShared(int rw, int col) throws Exception {
        for (SharedFormulaData var : this.sharedFormulaDataTable)
        {
            if (var.checkFormulaIsInShared(rw,col))
                return var;
             
        }
        return null;
    }

    /**
    * Add the rowdata to the rowdataobject
    * 
    *  @param row ROW Biff Record
    */
    public void addRowData(Row row) throws Exception {
        RowData rowData = this.getSpecificRow(row.rw);
        rowData.height = new TwipsValue(row.miyRw);
        rowData.hidden = row.fDyZero;
        rowData.outlineLevel = row.iOutLevel;
        rowData.collapsed = row.fCollapsed;
        rowData.customFormat = row.fGhostDirty;
        rowData.style = row.ixfe_val;
        rowData.thickBot = row.fExDes;
        rowData.thickTop = row.fExAsc;
        rowData.maxSpan = row.colMac;
        rowData.minSpan = row.colMic;
        rowData.customHeight = row.fUnsynced;
    }

    /**
    * Add the colinfo to the data object model
    * 
    *  @param colinfo ColInfo BIFF Record
    */
    public void addColData(ColInfo colinfo) throws Exception {
        ColumnInfoData colinfoData = new ColumnInfoData();
        colinfoData.min = colinfo.colFirst;
        colinfoData.max = colinfo.colLast;
        colinfoData.widht = colinfo.coldx;
        colinfoData.customWidth = colinfo.fUserSet;
        colinfoData.hidden = colinfo.fHidden;
        colinfoData.bestFit = colinfo.fBestFit;
        colinfoData.phonetic = colinfo.fPhonetic;
        colinfoData.outlineLevel = colinfo.iOutLevel;
        colinfoData.collapsed = colinfo.fCollapsed;
        colinfoData.style = colinfo.ixfe;
        this.colInfoDataTable.add(colinfoData);
    }

    /**
    * Add the default column width
    * 
    *  @param width
    */
    public void addDefaultColWidth(int width) throws Exception {
        this.defaultColWidth = width;
    }

    /**
    * add the default row data to the boundsheet data object
    * 
    *  @param defaultRowData
    */
    public void addDefaultRowData(DefaultRowHeight defaultRowData) throws Exception {
        if (!defaultRowData.fDyZero)
        {
            this.defaultRowHeight = defaultRowData.miyRW;
        }
        else
        {
            this.defaultRowHeight = defaultRowData.miyRwHidden;
        } 
        this.zeroHeight = defaultRowData.fDyZero;
        this.customHeight = defaultRowData.fUnsynced;
        this.thickTop = defaultRowData.fExAsc;
        this.thickBottom = defaultRowData.fExDsc;
    }

    public void addSetupData(Setup setup) throws Exception {
        this.footerMargin = setup.numFtr;
        this.headerMargin = setup.numHdr;
        this.pageSetup = setup;
    }

    public void addHyperLinkData(HLink hlink) throws Exception {
        HyperlinkData hld = new HyperlinkData();
        hld.colFirst = hlink.colFirst;
        hld.colLast = hlink.colLast;
        hld.rwFirst = hlink.rwFirst;
        hld.rwLast = hlink.rwLast;
        hld.absolute = hlink.hlstmfIsAbsolute;
        hld.url = hlink.monikerString;
        hld.location = hlink.location;
        hld.display = hlink.displayName;
        this.HyperLinkList.add(hld);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<WorkSheetData>)mapping).apply(this);
    }

}


