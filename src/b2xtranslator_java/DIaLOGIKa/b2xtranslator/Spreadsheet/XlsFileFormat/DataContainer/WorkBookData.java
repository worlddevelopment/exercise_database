//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:36 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.ExternSheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SSTData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SupBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.WorkBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.XTIData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BOF;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CodeName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CRN;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ExternName;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ExternSheet;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Lbl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SupBook;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.XCT;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData.StyleData;

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
* This class is a container for the extracted data
* Implements the IVisitable Interface
*/
public class WorkBookData   implements IVisitable
{
    /**
    * This attribute stores the SharedStringTable Data
    */
    private SSTData sstData;
    public SSTData getSstData() throws Exception {
        return sstData;
    }

    public void setSstData(SSTData value) throws Exception {
        sstData = value;
    }

    private boolean __Template;
    public boolean getTemplate() {
        return __Template;
    }

    public void setTemplate(boolean value) {
        __Template = value;
    }

    public CSList<SheetData> boundSheetDataList;
    public CSList<ExternSheetData> externSheetDataList;
    public LinkedList<SupBookData> supBookDataList = new LinkedList<SupBookData>();
    public LinkedList<XTIData> xtiDataList = new LinkedList<XTIData>();
    public CSList<Lbl> definedNameList;
    public int refWorkBookNumber;
    public StyleData styleData;
    public BOF BOF;
    public CodeName CodeName;
    /**
    * Ctor
    */
    public WorkBookData() throws Exception {
        this.boundSheetDataList = new CSList<SheetData>();
        this.externSheetDataList = new CSList<ExternSheetData>();
        this.supBookDataList = new LinkedList<SupBookData>();
        this.xtiDataList = new LinkedList<XTIData>();
        this.definedNameList = new CSList<Lbl>();
        refWorkBookNumber = 0;
        this.styleData = new StyleData();
    }

    /**
    * Adds a WorkSheetData Element to the internal list
    * 
    *  @param bsd The Boundsheetdata element
    */
    public void addBoundSheetData(SheetData bsd) throws Exception {
        this.boundSheetDataList.Add(bsd);
    }

    /**
    * Add the ExternSheetData extracted from an EXTERNSHEET BIFF Record
    * 
    *  @param ext BIFF Record
    */
    public void addExternSheetData(ExternSheet ext) throws Exception {
        for (int i = 0;i < ext.cXTI;i++)
        {
            ExternSheetData extdata = new ExternSheetData(ext.iSUPBOOK[i],ext.itabFirst[i],ext.itabLast[i]);
            this.externSheetDataList.add(extdata);
        }
    }

    /**
    * Add a SUPBOOK BIFF Record to the list
    * 
    *  @param sup
    */
    public void addSupBookData(SupBook sup) throws Exception {
        SupBookData supbook = new SupBookData(sup);
        if (!supbook.getSelfRef())
        {
            this.refWorkBookNumber++;
            supbook.Number = this.refWorkBookNumber;
        }
         
        this.supBookDataList.AddLast(supbook);
    }

    /**
    * Returns the string from an external workbook
    * 
    *  @param index index from the workbook 
    *  @return Filename and sheetname
    */
    public String getIXTIString(UInt16 index) throws Exception {
        ExternSheetData extSheet = this.externSheetDataList[index];
        SupBookData supData = null;
        LinkedList<SupBookData>.Enumerator listenum = this.supBookDataList.GetEnumerator();
        int count = 0;
        listenum.MoveNext();
        do
        {
            if (count == extSheet.iSUPBOOK)
            {
                supData = listenum.Current;
            }
             
            count++;
        }
        while (listenum.MoveNext());
        String back = "";
        if (supData != null && supData.getSelfRef())
        {
            String first = this.boundSheetDataList[extSheet.itabFirst].boundsheetRecord.stName.Value;
            String last = this.boundSheetDataList[extSheet.itabLast].boundsheetRecord.stName.Value;
            if (first.equals(last))
            {
                back = first;
            }
            else
            {
                back = first + ":" + last;
            } 
        }
        else
        {
            String first = supData.getRgstString(extSheet.itabFirst);
            String last = supData.getRgstString(extSheet.itabLast);
            if (first.equals(last))
            {
                back = first;
            }
            else
            {
                back = first + ":" + last;
            } 
            // add one to the index
            back = "[" + String.valueOf(supData.Number) + "]" + back;
        } 
        return back;
    }

    /**
    * Add a XCT Data structure to the internal stack
    * 
    *  @param xct
    */
    public void addXCT(XCT xct) throws Exception {
        XTIData xti = new XTIData(this.xtiDataList.Count - 1, this.supBookDataList.Count - 1, xct.itab);
        this.xtiDataList.AddLast(xti);
        this.supBookDataList.Last.Value.addXCT(xct);
    }

    /**
    * Add a CRN Data structure to the internal list
    * 
    *  @param xct
    */
    public void addCRN(CRN crn) throws Exception {
        this.supBookDataList.Last.Value.addCRN(crn);
    }

    /**
    * Add a EXTERNNAME Data structure to the internal list
    * 
    *  @param xct
    */
    public void addEXTERNNAME(ExternName extname) throws Exception {
        this.supBookDataList.Last.Value.addEXTERNNAME(extname);
    }

    /**
    * add a definedName data object
    * 
    *  @param name
    */
    public void addDefinedName(Lbl name) throws Exception {
        this.definedNameList.add(name);
    }

    /**
    * Get the definedname string from an ID
    * 
    *  @param id 
    *  @return
    */
    public String getDefinedNameByRef(int id) throws Exception {
        if (this.definedNameList.get(id - 1).Name.getValue().length() > 1)
        {
            return this.definedNameList.get(id - 1).Name.getValue();
        }
        else
        {
            String internName = "_xlnm." + ExcelHelperClass.getNameStringfromBuiltInFunctionID(this.definedNameList.get(id - 1).Name.getValue());
            return internName;
        } 
    }

    /**
    * returns the extern name if an valid ID is given!
    * 
    *  @param supIndex 
    *  @param nameIndex 
    *  @return
    */
    public String getExternNameByRef(UInt16 supIndex, long nameIndex) throws Exception {
        ExternSheetData extSheet = this.externSheetDataList[supIndex];
        SupBookData supData = null;
        LinkedList<SupBookData>.Enumerator listenum = this.supBookDataList.GetEnumerator();
        String back = "";
        int count = 0;
        int counttwo = 0;
        listenum.MoveNext();
        do
        {
            if (count == extSheet.iSUPBOOK)
            {
                supData = listenum.Current;
            }
             
            count++;
        }
        while (listenum.MoveNext());
        LinkedList<String>.Enumerator nameEnum = supData.getExternNames().GetEnumerator();
        do
        {
            if (counttwo == nameIndex)
            {
                back = nameEnum.Current;
            }
             
            counttwo++;
        }
        while (nameEnum.MoveNext());
        return "[" + (supData.Number)+"]!" + back;
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<WorkBookData>)mapping).apply(this);
    }

}


