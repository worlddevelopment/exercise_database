//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:32 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.ChartsheetPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.Sml.Sheet;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WindowSequence;
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
public class WindowMapping  extends AbstractOpenXmlMapping implements IMapping<WindowSequence>
{
    ExcelContext _xlsContext;
    ChartsheetPart _chartsheetPart;
    int _window1Id = 0;
    boolean _isChartsheetSubstream;
    /**
    * Ctor
    * 
    *  @param xlsContext The excel context object
    */
    public WindowMapping(ExcelContext xlsContext, ChartsheetPart chartsheetPart, int window1Id, boolean isChartsheetSubstream) throws Exception {
        super(chartsheetPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._chartsheetPart = chartsheetPart;
        this._window1Id = window1Id;
        this._isChartsheetSubstream = isChartsheetSubstream;
    }

    public void apply(WindowSequence windowSequence) throws Exception {
        _writer.WriteStartElement(Sheet.ElSheetView, Sml.Ns);
        _writer.writeAttributeString(Sheet.AttrTabSelected, windowSequence.Window2.fSelected ? "1" : "0");
        // zoomScale
        if (windowSequence.Scl != null)
        {
            // zoomScale is a percentage in the range 10-400
            int zoomScale = Math.Min(400, Math.max(10,windowSequence.Scl.nscl * 100 / windowSequence.Scl.dscl));
            _writer.writeAttributeString(Sheet.AttrZoomScale, String.valueOf(zoomScale));
        }
         
        _writer.writeAttributeString(Sheet.AttrWorkbookViewId, String.valueOf(this._window1Id));
        // TODO: complete mapping, certain fields must be ignored when in chart sheet substream
        _writer.writeEndElement();
    }

}


