//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:28 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DoubleSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AiSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AbstractCellContent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.BraiId;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.DataSource;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SIIndex;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.SIIndex.SeriesDataType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesDataSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesGroup;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.FormulaInfixMapping;
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
public class ValMapping  extends AbstractChartMapping implements IMapping<SeriesFormatSequence>
{
    String _parentElement;
    public ValMapping(ExcelContext workbookContext, ChartContext chartContext, String parentElement) throws Exception {
        super(workbookContext, chartContext);
        this._parentElement = parentElement;
    }

    /**
    * 
    */
    public void apply(SeriesFormatSequence seriesFormatSequence) throws Exception {
        for (AiSequence aiSequence : seriesFormatSequence.AiSequences)
        {
            // find BRAI record for values
            if (aiSequence.BRAI.braiId == BraiId.SeriesValues)
            {
                // c:val
                _writer.WriteStartElement(Chart.Prefix, this._parentElement, Chart.Ns);
                {
                    BRAI brai = aiSequence.BRAI;
                    switch(brai.rt)
                    {
                        case Literal: 
                            // c:numLit
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElNumLit, Chart.Ns);
                            convertNumData(seriesFormatSequence);
                            _writer.writeEndElement();
                            break;
                        case Reference: 
                            // c:numLit
                            // c:numRef
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElNumRef, Chart.Ns);
                            // c:f
                            String formula = FormulaInfixMapping.mapFormula(brai.formula.formula,this.getWorkbookContext());
                            _writer.WriteElementString(Chart.Prefix, Chart.ElF, Chart.Ns, formula);
                            // TODO: optional data cache
                            // c:numCache
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElNumCache, Chart.Ns);
                            convertNumData(seriesFormatSequence);
                            _writer.writeEndElement();
                            // c:numCache
                            _writer.writeEndElement();
                            break;
                    
                    }
                }
                // c:numRef
                _writer.writeEndElement();
                break;
            }
             
        }
    }

    // c:val
    private void convertNumData(SeriesFormatSequence seriesFormatSequence) throws Exception {
        // find series data
        SeriesDataSequence seriesDataSequence = this.getChartContext().getChartSheetContentSequence().SeriesDataSequence;
        for (SeriesGroup seriesGroup : seriesDataSequence.SeriesGroups)
        {
            if (seriesGroup.SIIndex.numIndex == SeriesDataType.SeriesValues)
            {
                AbstractCellContent[][] dataMatrix = seriesDataSequence.DataMatrix[(UInt16)seriesGroup.SIIndex.numIndex - 1];
                // TODO: c:formatCode
                long ptCount = 0;
                for (long i = 0;i < dataMatrix.GetLength(1);i++)
                {
                    if (dataMatrix[seriesFormatSequence.order, i] != null)
                    {
                        ptCount++;
                    }
                     
                }
                // c:ptCount
                writeValueElement(Chart.ElPtCount,String.valueOf(ptCount));
                long idx = 0;
                for (long i = 0;i < dataMatrix.GetLength(1);i++)
                {
                    Number cellContent = dataMatrix[seriesFormatSequence.order, i] instanceof Number ? (Number)dataMatrix[seriesFormatSequence.order, i] : (Number)null;
                    if (cellContent != null && cellContent.num != null)
                    {
                        // c:pt
                        _writer.WriteStartElement(Chart.Prefix, Chart.ElPt, Chart.Ns);
                        _writer.writeAttributeString(Chart.AttrIdx, String.valueOf(idx));
                        // c:v
                        double num = cellContent.num != null ? cellContent.num : 0.0;
                        _writer.WriteElementString(Chart.Prefix, Chart.ElV, Chart.Ns, DoubleSupport.ToString(num, LocaleSupport.INVARIANT));
                        _writer.writeEndElement();
                    }
                     
                    // c:pt
                    idx++;
                }
                break;
            }
             
        }
    }

}


