//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DoubleSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.CrtLayout12.CrtLayout12Mode;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import java.util.Locale;

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
public class LayoutMapping  extends AbstractChartMapping implements IMapping<CrtLayout12>
{
    public LayoutMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    public void apply(CrtLayout12 crtLayout12) throws Exception {
        // c:layout
        _writer.WriteStartElement(Chart.Prefix, Chart.ElLayout, Chart.Ns);
        {
            if (crtLayout12.wHeightMode != CrtLayout12Mode.L12MAUTO || crtLayout12.wWidthMode != CrtLayout12Mode.L12MAUTO || crtLayout12.wYMode != CrtLayout12Mode.L12MAUTO || crtLayout12.wXMode != CrtLayout12Mode.L12MAUTO)
            {
                // c:manualLayout
                _writer.WriteStartElement(Chart.Prefix, Chart.ElManualLayout, Chart.Ns);
                {
                    // c:layoutTarget
                    writeValueElement(Chart.ElLayoutTarget,crtLayout12.fLayoutTargetInner ? "inner" : "outer");
                    if (crtLayout12.wXMode != CrtLayout12Mode.L12MAUTO)
                    {
                        // c:xMode
                        writeValueElement(Chart.ElXMode,crtLayout12.wXMode == CrtLayout12Mode.L12MEDGE ? "edge" : "factor");
                    }
                     
                    if (crtLayout12.wYMode != CrtLayout12Mode.L12MAUTO)
                    {
                        // c:yMode
                        writeValueElement(Chart.ElYMode,crtLayout12.wYMode == CrtLayout12Mode.L12MEDGE ? "edge" : "factor");
                    }
                     
                    if (crtLayout12.wWidthMode != CrtLayout12Mode.L12MAUTO)
                    {
                        // c:wMode
                        writeValueElement(Chart.ElWMode,crtLayout12.wWidthMode == CrtLayout12Mode.L12MEDGE ? "edge" : "factor");
                    }
                     
                    if (crtLayout12.wHeightMode != CrtLayout12Mode.L12MAUTO)
                    {
                        // c:hMode
                        writeValueElement(Chart.ElHMode,crtLayout12.wHeightMode == CrtLayout12Mode.L12MEDGE ? "edge" : "factor");
                    }
                     
                    // c:x
                    writeValueElement(Chart.ElX,DoubleSupport.ToString(crtLayout12.x, LocaleSupport.INVARIANT));
                    // c:y
                    writeValueElement(Chart.ElY,DoubleSupport.ToString(crtLayout12.y, LocaleSupport.INVARIANT));
                    // c:w
                    writeValueElement(Chart.ElW,DoubleSupport.ToString(crtLayout12.dx, LocaleSupport.INVARIANT));
                    // c:h
                    writeValueElement(Chart.ElH,DoubleSupport.ToString(crtLayout12.dy, LocaleSupport.INVARIANT));
                }
                _writer.writeEndElement();
            }
             
        }
        // c:manualLayout
        _writer.writeEndElement();
    }

}


// c:layout