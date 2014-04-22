//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:27 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat.LineStyle;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MarkerFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MarkerFormat.MarkerType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Scatter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartGroupMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.CatMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.DataLabelMapping;
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
public class ScatterChartMapping  extends AbstractChartGroupMapping 
{
    public ScatterChartMapping(ExcelContext workbookContext, ChartContext chartContext, boolean is3DChart) throws Exception {
        super(workbookContext, chartContext, is3DChart);
    }

    public void apply(CrtSequence crtSequence) throws Exception {
        if (!(crtSequence.ChartType instanceof Scatter))
        {
            throw new Exception("Invalid chart type");
        }
         
        Scatter scatter = crtSequence.ChartType instanceof Scatter ? (Scatter)crtSequence.ChartType : (Scatter)null;
        // c:scatterChart
        _writer.WriteStartElement(Chart.Prefix, Chart.ElScatterChart, Chart.Ns);
        {
            // c:scatterStyle
            writeValueElement(Chart.Prefix,Chart.ElScatterStyle,Chart.Ns,mapScatterStyle(crtSequence.SsSequence));
            for (SeriesFormatSequence seriesFormatSequence : this.getChartFormatsSequence().SeriesFormatSequences)
            {
                // c:varyColors
                //writeValueElement(Dml.Chart.ElVaryColors, crtSequence.ChartFormat.fVaried ? "1" : "0");
                if (seriesFormatSequence.SerToCrt != null && seriesFormatSequence.SerToCrt.id == crtSequence.ChartFormat.idx)
                {
                    // c:ser (CT_ScatterSer)
                    // c:ser
                    _writer.WriteStartElement(Chart.Prefix, Chart.ElSer, Chart.Ns);
                    // EG_SerShared
                    seriesFormatSequence.Convert(new SeriesMapping(this.getWorkbookContext(),this.getChartContext()));
                    // c:marker
                    // c:dPt
                    // c:dLbls (CT_DLbls)
                    this.getChartFormatsSequence().Convert(new DataLabelMapping(this.getWorkbookContext(),this.getChartContext(),seriesFormatSequence));
                    // c:trendline
                    // c:errBars
                    // c:xVal
                    seriesFormatSequence.Convert(new CatMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElXVal));
                    // c:yVal
                    seriesFormatSequence.Convert(new ValMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElYVal));
                    // c:smooth
                    writeValueElement(Chart.Prefix,Chart.ElSmooth,Chart.Ns,isSmoothed(crtSequence.SsSequence) ? "1" : "0");
                    _writer.writeEndElement();
                }
                 
            }
            for (int axisId : crtSequence.ChartFormat.AxisIds)
            {
                // c:ser
                // Data Labels
                // Axis Ids
                writeValueElement(Chart.ElAxId,String.valueOf(axisId));
            }
        }
        _writer.writeEndElement();
    }

    private String mapScatterStyle(SsSequence ssSequence) throws Exception {
        // CT_ScatterStyle
        // The following scatter styles exist: line, lineMarker, marker, none, smooth, smoothMarker
        //
        boolean smoothed = isSmoothed(ssSequence);
        boolean hasMarker = (ssSequence == null) || (ssSequence.MarkerFormat != null && ssSequence.MarkerFormat.imk != MarkerType.NoMarker);
        boolean hasLine = (ssSequence == null) || (ssSequence.LineFormat != null && ssSequence.LineFormat.lns != LineStyle.None);
        String scatterStyle = "none";
        if (smoothed && hasMarker)
        {
            scatterStyle = "smoothMarker";
        }
        else if (smoothed)
        {
            scatterStyle = "smooth";
        }
        else if (hasMarker && hasLine)
        {
            scatterStyle = "lineMarker";
        }
        else if (hasLine)
        {
            scatterStyle = "line";
        }
        else if (hasMarker)
        {
            scatterStyle = "marker";
        }
             
        return scatterStyle;
    }

    private boolean isSmoothed(SsSequence ssSequence) throws Exception {
        return (ssSequence != null && ssSequence.SerFmt != null && ssSequence.SerFmt.fSmoothedLine);
    }

}


