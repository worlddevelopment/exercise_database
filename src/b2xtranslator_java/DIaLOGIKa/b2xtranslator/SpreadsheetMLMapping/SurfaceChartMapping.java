//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:28 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Surf;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartGroupMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.CatMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.SeriesMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ValMapping;

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
public class SurfaceChartMapping  extends AbstractChartGroupMapping 
{
    public SurfaceChartMapping(ExcelContext workbookContext, ChartContext chartContext, boolean is3DChart) throws Exception {
        super(workbookContext, chartContext, is3DChart);
    }

    public void apply(CrtSequence crtSequence) throws Exception {
        if (!(crtSequence.ChartType instanceof Surf))
        {
            throw new Exception("Invalid chart type");
        }
         
        Surf surf = crtSequence.ChartType instanceof Surf ? (Surf)crtSequence.ChartType : (Surf)null;
        // c:surfaceChart
        _writer.WriteStartElement(Chart.Prefix, this._is3DChart ? Chart.ElSurface3DChart : Chart.ElSurfaceChart, Chart.Ns);
        {
            for (SeriesFormatSequence seriesFormatSequence : this.getChartFormatsSequence().SeriesFormatSequences)
            {
                // EG_SurfaceChartShared
                // c:wireframe
                // Surface Chart Series (CT_SurfaceSer)
                if (seriesFormatSequence.SerToCrt != null && seriesFormatSequence.SerToCrt.id == crtSequence.ChartFormat.idx)
                {
                    // c:ser
                    _writer.WriteStartElement(Chart.Prefix, Chart.ElSer, Chart.Ns);
                    // EG_SerShared
                    seriesFormatSequence.Convert(new SeriesMapping(this.getWorkbookContext(),this.getChartContext()));
                    // c:cat
                    seriesFormatSequence.Convert(new CatMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElCat));
                    // c:val
                    seriesFormatSequence.Convert(new ValMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElVal));
                    _writer.writeEndElement();
                }
                 
            }
            for (int axisId : crtSequence.ChartFormat.AxisIds)
            {
                // c:ser
                // c:bandFmts
                // c:axId
                writeValueElement(Chart.ElAxId,String.valueOf(axisId));
            }
        }
        _writer.writeEndElement();
    }

}


