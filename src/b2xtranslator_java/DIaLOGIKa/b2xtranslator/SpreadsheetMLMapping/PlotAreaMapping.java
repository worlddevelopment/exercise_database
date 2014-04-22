//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:27 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxesSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxisParentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.CrtSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Area;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Bar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BopPop;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Line;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Pie;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Radar;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Scatter;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Surf;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AreaChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AxisMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.BarChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.BubbleChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.LayoutMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.LineChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.OfPieChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.PieChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.RadarChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ScatterChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ShapePropertiesMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.SurfaceChartMapping;

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
public class PlotAreaMapping  extends AbstractChartMapping implements IMapping<ChartFormatsSequence>
{
    public PlotAreaMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    /**
    * 
    */
    public void apply(ChartFormatsSequence chartFormatsSequence) throws Exception {
        // c:plotArea
        _writer.WriteStartElement(Chart.Prefix, Chart.ElPlotArea, Chart.Ns);
        {
            // c:layout
            if (chartFormatsSequence.ShtProps.fManPlotArea && chartFormatsSequence.CrtLayout12A != null)
            {
                chartFormatsSequence.CrtLayout12A.Convert(new LayoutMapping(this.getWorkbookContext(),this.getChartContext()));
            }
             
            for (AxisParentSequence axisParentSequence : chartFormatsSequence.AxisParentSequences)
            {
                for (CrtSequence crtSequence : axisParentSequence.CrtSequences)
                {
                    // chart groups
                    // The Chart3d record specifies that the plot area, axis group, and chart group are rendered
                    // in a 3-D scene, rather than a 2-D scene, and specifies properties of the 3-D scene. If this
                    // record exists in the chart sheet substream, the chart sheet substream MUST have exactly one
                    // chart group. This record MUST NOT exist in a bar of pie, bubble, doughnut,
                    // filled radar, pie of pie, radar, or scatter chart group.
                    //
                    boolean is3DChart = (crtSequence.Chart3d != null);
                    // area chart
                    if (crtSequence.ChartType instanceof Area)
                    {
                        crtSequence.Convert(new AreaChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // bar and column chart
                    if (crtSequence.ChartType instanceof Bar)
                    {
                        crtSequence.Convert(new BarChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // OfPieChart (Bar of pie / Pie of Pie)
                    if (crtSequence.ChartType instanceof BopPop)
                    {
                        crtSequence.Convert(new OfPieChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // bubbleChart
                    if (crtSequence.ChartType instanceof Scatter && ((Scatter)crtSequence.ChartType).fBubbles)
                    {
                        crtSequence.Convert(new BubbleChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // scatterChart
                    if (crtSequence.ChartType instanceof Scatter && !((Scatter)crtSequence.ChartType).fBubbles)
                    {
                        crtSequence.Convert(new ScatterChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // lineChart and stockChart
                    if (crtSequence.ChartType instanceof Line)
                    {
                        crtSequence.Convert(new LineChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // doughnutChart and pieChart (they differ by ((Pie)crtSequence.ChartType).pcDonut
                    if (crtSequence.ChartType instanceof Pie)
                    {
                        crtSequence.Convert(new PieChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // radarChart
                    if (crtSequence.ChartType instanceof Radar)
                    {
                        // RadarArea (or "Filled Radar") has the radarStyle set to "filled")
                        crtSequence.Convert(new RadarChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                    else // surfaceChart
                    if (crtSequence.ChartType instanceof Surf)
                    {
                        crtSequence.Convert(new SurfaceChartMapping(this.getWorkbookContext(),this.getChartContext(),is3DChart));
                    }
                             
                }
            }
            for (AxisParentSequence axisParentSequence : chartFormatsSequence.AxisParentSequences)
            {
                // axis groups
                // NOTE: AxisParent.iax must be 0 for the primary axis group
                AxesSequence axesSequence = axisParentSequence.AxesSequence;
                if (axesSequence != null)
                {
                    axesSequence.Convert(new AxisMapping(this.getWorkbookContext(),this.getChartContext()));
                }
                 
            }
            // c:spPr
            if (chartFormatsSequence.AxisParentSequences.size() > 0 && chartFormatsSequence.AxisParentSequences.get(0).AxesSequence != null && chartFormatsSequence.AxisParentSequences.get(0).AxesSequence.PlotArea != null)
            {
                chartFormatsSequence.AxisParentSequences.get(0).AxesSequence.Frame.Convert(new ShapePropertiesMapping(this.getWorkbookContext(),this.getChartContext()));
            }
             
        }
        _writer.writeEndElement();
    }

}


// c:plotArea