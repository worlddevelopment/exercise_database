//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:32 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ChartsheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml.Workbook;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorkbookPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorksheetPart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.SupBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.WorkBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BoundSheet8;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BoundSheet8.SheetType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Lbl;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SheetData;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartsheetMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.FormulaInfixMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.WorksheetMapping;

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
public class WorkbookMapping  extends AbstractOpenXmlMapping implements IMapping<WorkBookData>
{
    ExcelContext _xlsContext;
    WorkbookPart _workbookPart;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public WorkbookMapping(ExcelContext xlsContext, WorkbookPart workbookPart) throws Exception {
        super(workbookPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._workbookPart = workbookPart;
    }

    /**
    * The overload apply method
    * Creates the Workbook xml document
    * 
    *  @param bsd WorkSheetData
    */
    public void apply(WorkBookData workbookData) throws Exception {
        _writer.WriteStartDocument();
        _writer.WriteStartElement("workbook", OpenXmlNamespaces.SpreadsheetML);
        _writer.writeAttributeString("xmlns", Sml.Ns);
        _writer.WriteAttributeString("xmlns", "r", "", OpenXmlNamespaces.Relationships);
        // fileVersion
        _writer.WriteStartElement(Workbook.ElFileVersion, Sml.Ns);
        _writer.writeAttributeString(Workbook.AttrAppName, "xl");
        _writer.writeEndElement();
        // workbookPr
        _writer.WriteStartElement(Workbook.ElWorkbookPr, Sml.Ns);
        if (workbookData.CodeName != null)
        {
            _writer.writeAttributeString(Workbook.AttrCodeName, workbookData.CodeName.codeName.getValue());
        }
         
        _writer.writeEndElement();
        _writer.writeStartElement("sheets");
        for (Object __dummyForeachVar0 : workbookData.boundSheetDataList)
        {
            // create the sheets
            SheetData sheet = (SheetData)__dummyForeachVar0;
            OpenXmlPart sheetPart;
            switch(sheet.boundsheetRecord.dt)
            {
                case Worksheet: 
                    {
                        WorksheetPart worksheetPart = this._workbookPart.addWorksheetPart();
                        sheet.Convert(new WorksheetMapping(this._xlsContext,worksheetPart));
                        sheetPart = worksheetPart;
                    }
                    break;
                case Chartsheet: 
                    {
                        ChartsheetPart chartsheetPart = this._workbookPart.addChartsheetPart();
                        sheet.Convert(new ChartsheetMapping(this._xlsContext,chartsheetPart));
                        sheetPart = chartsheetPart;
                    }
                    break;
                default: 
                    {
                        sheet.emtpyWorksheet = true;
                        WorksheetPart worksheetPart = this._workbookPart.addWorksheetPart();
                        sheet.Convert(new WorksheetMapping(this._xlsContext,worksheetPart));
                        sheetPart = worksheetPart;
                    }
                    break;
            
            }
            _writer.writeStartElement("sheet");
            _writer.writeAttributeString("name", sheet.boundsheetRecord.stName.getValue());
            _writer.writeAttributeString("sheetId", String.valueOf(sheetPart.getRelId()));
            _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, sheetPart.getRelIdToString());
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        // close sheetData
        boolean ParentTagWritten = false;
        if (workbookData.supBookDataList.Count != 0)
        {
            for (Object __dummyForeachVar1 : workbookData.supBookDataList)
            {
                /*
                                    <externalReferences>
                                        <externalReference r:id="rId4" /> 
                                        <externalReference r:id="rId5" /> 
                                    </externalReferences>
                                 */
                SupBookData var = (SupBookData)__dummyForeachVar1;
                if (!var.getSelfRef())
                {
                    if (!ParentTagWritten)
                    {
                        _writer.writeStartElement("externalReferences");
                        ParentTagWritten = true;
                    }
                     
                    _writer.writeStartElement("externalReference");
                    _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, var.ExternalLinkRef);
                    _writer.writeEndElement();
                }
                 
            }
            if (ParentTagWritten)
            {
                _writer.writeEndElement();
            }
             
        }
         
        // write definedNames
        if (workbookData.definedNameList.size() > 0)
        {
            //<definedNames>
            //<definedName name="abc" comment="test" localSheetId="1">Sheet1!$B$3</definedName>
            //</definedNames>
            _writer.writeStartElement("definedNames");
            for (Lbl item : workbookData.definedNameList)
            {
                if (item.rgce.Count > 0)
                {
                    _writer.writeStartElement("definedName");
                    if (item.Name.getValue().length() > 1)
                    {
                        _writer.writeAttributeString("name", item.Name.getValue());
                    }
                    else
                    {
                        String internName = "_xlnm." + ExcelHelperClass.getNameStringfromBuiltInFunctionID(item.Name.getValue());
                        _writer.writeAttributeString("name", internName);
                    } 
                    if (item.itab > 0)
                    {
                        _writer.writeAttributeString("localSheetId", (item.itab - 1).toString());
                    }
                     
                    _writer.WriteValue(FormulaInfixMapping.mapFormula(item.rgce,_xlsContext));
                    _writer.writeEndElement();
                }
                 
            }
            _writer.writeEndElement();
        }
         
        // close tags
        _writer.writeEndElement();
        // close worksheet
        _writer.WriteEndDocument();
        // flush writer
        _writer.Flush();
    }

}


