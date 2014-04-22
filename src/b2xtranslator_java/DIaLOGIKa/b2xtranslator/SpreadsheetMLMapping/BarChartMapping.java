//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Bar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart3DBarShape;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart3DBarShape.RiserType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart3DBarShape.TaperType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SsSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartGroupMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.CatMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.DataLabelMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.DataPointMapping;
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
public class BarChartMapping  extends AbstractChartGroupMapping 
{
    public BarChartMapping(ExcelContext workbookContext, ChartContext chartContext, boolean is3DChart) throws Exception {
        super(workbookContext, chartContext, is3DChart);
    }

    public void apply(CrtSequence crtSequence) throws Exception {
        if (!(crtSequence.ChartType instanceof Bar))
        {
            throw new Exception("Invalid chart type");
        }
         
        Bar bar = crtSequence.ChartType instanceof Bar ? (Bar)crtSequence.ChartType : (Bar)null;
        // c:barChart / c:bar3DChart
        _writer.WriteStartElement(Chart.Prefix, this._is3DChart ? Chart.ElBar3DChart : Chart.ElBarChart, Chart.Ns);
        {
            // EG_BarChartShared
            // c:barDir
            writeValueElement(Chart.ElBarDir,bar.fTranspose ? "bar" : "col");
            // c:grouping
            String grouping = bar.fStacked ? "stacked" : (bar.f100 ? "percentStacked" : (this.getIs3DChart() && !crtSequence.Chart3d.fCluster ? "standard" : "clustered"));
            writeValueElement(Chart.ElGrouping,grouping);
            for (SeriesFormatSequence seriesFormatSequence : this.getChartFormatsSequence().SeriesFormatSequences)
            {
                // c:varyColors: This setting needs to be ignored if the chart has
                //writeValueElement(Dml.Chart.ElVaryColors, crtSequence.ChartFormat.fVaried ? "1" : "0");
                // Bar Chart Series
                if (seriesFormatSequence.SerToCrt != null && seriesFormatSequence.SerToCrt.id == crtSequence.ChartFormat.idx)
                {
                    // c:ser
                    _writer.WriteStartElement(Chart.Prefix, Chart.ElSer, Chart.Ns);
                    // EG_SerShared
                    seriesFormatSequence.Convert(new SeriesMapping(this.getWorkbookContext(),this.getChartContext()));
                    for (int i = 1;i < seriesFormatSequence.SsSequence.size();i++)
                    {
                        // c:invertIfNegative (stored in AreaFormat)
                        // c:pictureOptions
                        // c:dPt (Data Points)
                        // write a dPt for each SsSequence
                        SsSequence ssSequence = seriesFormatSequence.SsSequence.get(i);
                        ssSequence.Convert(new DataPointMapping(this.getWorkbookContext(),this.getChartContext(),i - 1));
                    }
                    // c:dLbls (Data Labels)
                    this.getChartFormatsSequence().Convert(new DataLabelMapping(this.getWorkbookContext(),this.getChartContext(),seriesFormatSequence));
                    // c:trendline
                    // c:errBars
                    // c:cat (Category Axis Data)
                    seriesFormatSequence.Convert(new CatMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElCat));
                    // c:val
                    seriesFormatSequence.Convert(new ValMapping(this.getWorkbookContext(),this.getChartContext(),Chart.ElVal));
                    // c:shape (we only condider the first Chart3DBarShape found)
                    SsSequence ssSeq = seriesFormatSequence.SsSequence.Find(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(s) => {
                        return s.Chart3DBarShape != null;
                    }" */);
                    if (ssSeq != null)
                    {
                        insertShape(ssSeq.Chart3DBarShape);
                    }
                     
                    _writer.writeEndElement();
                }
                 
            }
            // c:ser
            // Data Labels
            if (this._is3DChart)
            {
                // c:gapWidth
                writeValueElement(Chart.ElGapWidth,crtSequence.Chart3d.pcGap.toString());
                // c:gapDepth
                writeValueElement(Chart.ElGapDepth,String.valueOf(crtSequence.Chart3d.pcDepth));
                // c:shape
                if (crtSequence.SsSequence != null && crtSequence.SsSequence.Chart3DBarShape != null)
                {
                    insertShape(crtSequence.SsSequence.Chart3DBarShape);
                }
                 
            }
            else
            {
                // c:gapWidth
                writeValueElement(Chart.ElGapWidth,bar.pcGap.toString());
                // c:overlap
                writeValueElement(Chart.ElOverlap,String.valueOf((-bar.pcOverlap)));
            } 
            for (int axisId : crtSequence.ChartFormat.AxisIds)
            {
                // Series Lines
                // Axis Ids
                writeValueElement(Chart.ElAxId,String.valueOf(axisId));
            }
        }
        _writer.writeEndElement();
    }

    private void insertShape(Chart3DBarShape chart3DBarShape) throws Exception {
        String shape = "";
        switch(chart3DBarShape.taper)
        {
            case None: 
                shape = chart3DBarShape.riser == RiserType.Rectangle ? "box" : "cylinder";
                break;
            case TopEach: 
                shape = chart3DBarShape.riser == RiserType.Rectangle ? "pyramid" : "cone";
                break;
            case TopMax: 
                shape = chart3DBarShape.riser == RiserType.Rectangle ? "pyramidToMax" : "coneToMax";
                break;
        
        }
        if (!StringSupport.isNullOrEmpty(shape))
        {
            writeValueElement(Chart.ElShape,shape);
        }
         
    }

}


