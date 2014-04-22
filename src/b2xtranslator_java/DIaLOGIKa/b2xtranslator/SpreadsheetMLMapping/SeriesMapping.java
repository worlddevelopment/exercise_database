//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:27 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AiSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.BraiId;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.BRAI.DataSource;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SeriesFormatSequence;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.FormulaInfixMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ShapePropertiesMapping;

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
public class SeriesMapping  extends AbstractChartMapping implements IMapping<SeriesFormatSequence>
{
    public SeriesMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    public void apply(SeriesFormatSequence seriesFormatSequence) throws Exception {
        // EG_SerShared
        // c:idx
        // TODO: check the meaning of this element
        writeValueElement(Chart.ElIdx,seriesFormatSequence.SerToCrt.id.toString());
        // c:order
        writeValueElement(Chart.ElOrder,seriesFormatSequence.order.toString());
        for (AiSequence aiSequence : seriesFormatSequence.AiSequences)
        {
            // c:tx
            // find BRAI record for series name
            if (aiSequence.BRAI.braiId == BraiId.SeriesNameOrLegendText)
            {
                BRAI brai = aiSequence.BRAI;
                if (aiSequence.SeriesText != null)
                {
                    switch(brai.rt)
                    {
                        case Literal: 
                            // c:tx
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElTx, Chart.Ns);
                            // c:v
                            _writer.WriteElementString(Chart.Prefix, Chart.ElV, Chart.Ns, aiSequence.SeriesText.stText.getValue());
                            _writer.writeEndElement();
                            break;
                        case Reference: 
                            // c:tx
                            // c:tx
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElTx, Chart.Ns);
                            // c:strRef
                            _writer.WriteStartElement(Chart.Prefix, Chart.ElStrRef, Chart.Ns);
                            {
                                // c:f
                                String formula = FormulaInfixMapping.mapFormula(brai.formula.formula,this.getWorkbookContext());
                                _writer.WriteElementString(Chart.Prefix, Chart.ElF, Chart.Ns, formula);
                            }
                            // c:strCache
                            //convertStringCache(seriesFormatSequence);
                            _writer.writeEndElement();
                            // c:strRef
                            _writer.writeEndElement();
                            break;
                    
                    }
                }
                 
                break;
            }
             
        }
        // c:tx
        if (seriesFormatSequence.SsSequence.size() > 0)
        {
            seriesFormatSequence.SsSequence.get(0).Convert(new ShapePropertiesMapping(this.getWorkbookContext(),this.getChartContext()));
        }
         
    }

}


