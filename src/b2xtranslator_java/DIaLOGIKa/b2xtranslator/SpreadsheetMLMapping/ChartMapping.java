//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartFormatsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.LdSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjectLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjectLink.ObjectType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ShtProps;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ShtProps.EmptyCellPlotMode;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.LegendMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.PlotAreaMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ShapePropertiesMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.TitleMapping;

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
public class ChartMapping  extends AbstractChartMapping implements IMapping<ChartSheetContentSequence>
{
    ChartContext _chartContext;
    public ChartMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
        this._chartContext = chartContext;
    }

    // TODO: maybe we only need chartSheetContentSequence.ChartFormatsSequence here
    public void apply(ChartSheetContentSequence chartSheetContentSequence) throws Exception {
        ChartFormatsSequence chartFormatsSequence = chartSheetContentSequence.ChartFormatsSequence;
        // c:chartspace
        _writer.WriteStartElement(Chart.Prefix, Chart.ElChartSpace, Chart.Ns);
        _writer.WriteAttributeString("xmlns", Chart.Prefix, "", Chart.Ns);
        _writer.WriteAttributeString("xmlns", Dml.Prefix, "", Dml.Ns);
        {
            // c:chart
            _writer.WriteStartElement(Chart.Prefix, Chart.ElChart, Chart.Ns);
            {
                for (AttachedLabelSequence attachedLabelSequence : chartFormatsSequence.AttachedLabelSequences)
                {
                    // c:title
                    if (attachedLabelSequence.ObjectLink != null && attachedLabelSequence.ObjectLink.wLinkObj == ObjectType.Chart)
                    {
                        attachedLabelSequence.Convert(new TitleMapping(this.getWorkbookContext(),this.getChartContext()));
                        break;
                    }
                     
                }
                // c:plotArea
                chartFormatsSequence.Convert(new PlotAreaMapping(this.getWorkbookContext(),this.getChartContext()));
                // c:legend
                LdSequence firstLegend = chartFormatsSequence.AxisParentSequences.get(0).CrtSequences.get(0).LdSequence;
                if (firstLegend != null)
                {
                    firstLegend.Convert(new LegendMapping(this.getWorkbookContext(),this.getChartContext()));
                }
                 
                // c:plotVisOnly
                writeValueElement(Chart.ElPlotVisOnly,chartFormatsSequence.ShtProps.fPlotVisOnly ? "1" : "0");
                // c:dispBlanksAs
                String dispBlanksAs = "";
                switch(chartFormatsSequence.ShtProps.mdBlank)
                {
                    case PlotNothing: 
                        dispBlanksAs = "gap";
                        break;
                    case PlotAsZero: 
                        dispBlanksAs = "zero";
                        break;
                    case PlotAsInterpolated: 
                        dispBlanksAs = "span";
                        break;
                
                }
                if (!StringSupport.isNullOrEmpty(dispBlanksAs))
                {
                    writeValueElement(Chart.ElDispBlanksAs,dispBlanksAs);
                }
                 
            }
            // c:showDLblsOverMax
            _writer.writeEndElement();
            // c:chart
            // c:spPr
            if (chartFormatsSequence.FrameSequence != null)
            {
                chartFormatsSequence.FrameSequence.Convert(new ShapePropertiesMapping(this.getWorkbookContext(),this.getChartContext()));
            }
             
        }
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


