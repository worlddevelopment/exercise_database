//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:25 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.ChartPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.BaseTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;

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
public abstract class AbstractChartMapping  extends AbstractOpenXmlMapping 
{
    private ExcelContext _workbookContext;
    private ChartContext _chartContext;
    public AbstractChartMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(chartContext.getChartPart().getXmlWriter());
        this._workbookContext = workbookContext;
        this._chartContext = chartContext;
    }

    public ExcelContext getWorkbookContext() throws Exception {
        return this._workbookContext;
    }

    public ChartContext getChartContext() throws Exception {
        return this._chartContext;
    }

    public ChartPart getChartPart() throws Exception {
        return this.getChartContext().getChartPart();
    }

    public ChartSheetContentSequence getChartSheetContentSequence() throws Exception {
        return this.getChartContext().getChartSheetContentSequence();
    }

    public DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation getLocation() throws Exception {
        return this.getChartContext().getLocation();
    }

    public ChartFormatsSequence getChartFormatsSequence() throws Exception {
        return this.getChartSheetContentSequence().ChartFormatsSequence;
    }

    protected void writeValueElement(String localName, String value) throws Exception {
        _writer.WriteStartElement(Chart.Prefix, localName, Chart.Ns);
        _writer.writeAttributeString(BaseTypes.AttrVal, value);
        _writer.writeEndElement();
    }

    protected void writeValueElement(String prefix, String localName, String ns, String value) throws Exception {
        _writer.WriteStartElement(prefix, localName, ns);
        _writer.writeAttributeString(BaseTypes.AttrVal, value);
        _writer.writeEndElement();
    }

}


