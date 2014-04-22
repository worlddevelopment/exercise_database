//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:28 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ChartsheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml.Sheet;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.DrawingMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.PageSetupMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.WindowMapping;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
public class ChartsheetMapping  extends AbstractOpenXmlMapping implements IMapping<ChartSheetSequence>
{
    ExcelContext _xlsContext;
    ChartsheetPart _chartsheetPart;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public ChartsheetMapping(ExcelContext xlsContext, ChartsheetPart targetPart) throws Exception {
        super(targetPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._chartsheetPart = targetPart;
    }

    /**
    * The overload apply method
    * Creates the Worksheet xml document
    * 
    *  @param bsd WorkSheetData
    */
    public void apply(ChartSheetSequence chartSheetSequence) throws Exception {
        _writer.WriteStartDocument();
        // chartsheet
        _writer.WriteStartElement(Sheet.ElChartsheet, Sml.Ns);
        _writer.writeAttributeString("xmlns", Sml.Ns);
        _writer.WriteAttributeString("xmlns", "r", "", OpenXmlNamespaces.Relationships);
        ChartSheetContentSequence chartSheetContentSequence = chartSheetSequence.ChartSheetContentSequence;
        // sheetPr
        _writer.WriteStartElement(Sheet.ElSheetPr, Sml.Ns);
        if (chartSheetContentSequence.CodeName != null)
        {
            // code name
            _writer.writeAttributeString(Sheet.AttrCodeName, chartSheetContentSequence.CodeName.codeName.getValue());
        }
         
        // TODO: map SheetExtOptional to published and tab color
        _writer.writeEndElement();
        // sheetViews
        if (chartSheetContentSequence.WindowSequences.size() > 0)
        {
            _writer.WriteStartElement(Sheet.ElSheetViews, Sml.Ns);
            for (int window1Id = 0;window1Id < chartSheetContentSequence.WindowSequences.size();window1Id++)
            {
                // Note: There is a Window2 record for each Window1 record in the beginning of the workbook.
                // The index in the list corresponds to the 0-based workbookViewId attribute.
                //
                chartSheetContentSequence.WindowSequences.get(window1Id).Convert(new WindowMapping(this._xlsContext,this._chartsheetPart,window1Id,true));
            }
            _writer.writeEndElement();
        }
         
        // page setup
        chartSheetContentSequence.PageSetupSequence.Convert(new PageSetupMapping(this._xlsContext,this._chartsheetPart));
        // header and footer
        // TODO: header and footer
        // drawing
        _writer.WriteStartElement(Sheet.ElDrawing, Sml.Ns);
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, this._chartsheetPart.getDrawingsPart().getRelIdToString());
        chartSheetContentSequence.Convert(new DrawingMapping(this._xlsContext,this._chartsheetPart.getDrawingsPart(),true));
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


