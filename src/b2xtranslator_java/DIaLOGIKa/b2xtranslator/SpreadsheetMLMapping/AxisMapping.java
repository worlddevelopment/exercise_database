//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:26 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.JavaSupport.util.LocaleSupport;
import CS2JNet.System.DoubleSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AttachedLabelSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.AxesSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.IvAxisSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjectLink;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ObjectLink.ObjectType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.ValueRange;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.AbstractChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.TitleMapping;
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
public class AxisMapping  extends AbstractChartMapping implements IMapping<AxesSequence>
{
    public AxisMapping(ExcelContext workbookContext, ChartContext chartContext) throws Exception {
        super(workbookContext, chartContext);
    }

    public void apply(AxesSequence axesSequence) throws Exception {
        if (axesSequence != null)
        {
            if (axesSequence.IvAxisSequence != null)
            {
                // c:catAx
                _writer.WriteStartElement(Chart.Prefix, Chart.ElCatAx, Chart.Ns);
                {
                    mapIvAxis(axesSequence.IvAxisSequence,axesSequence);
                }
                _writer.writeEndElement();
            }
             
            // c:catAx
            // c:valAx
            _writer.WriteStartElement(Chart.Prefix, Chart.ElValAx, Chart.Ns);
            {
                // c:axId
                writeValueElement(Chart.ElAxId,String.valueOf(axesSequence.DvAxisSequence.Axis.AxisId));
                // c:scaling
                mapScaling(axesSequence.DvAxisSequence.ValueRange);
                // c:delete
                // c:axPos
                // TODO: find mapping
                writeValueElement(Chart.ElAxPos,"l");
                for (AttachedLabelSequence attachedLabelSequence : axesSequence.AttachedLabelSequences)
                {
                    // c:majorGridlines
                    // c:minorGridlines
                    // c:title
                    if (attachedLabelSequence.ObjectLink != null && attachedLabelSequence.ObjectLink.wLinkObj == ObjectType.DVAxis)
                    {
                        attachedLabelSequence.Convert(new TitleMapping(this.getWorkbookContext(),this.getChartContext()));
                        break;
                    }
                     
                }
                // c:numFmt
                // c:majorTickMark
                // c:minorTickMark
                // c:tickLblPos
                // c:spPr
                // c:txPr
                // c:crossAx
                if (axesSequence.IvAxisSequence != null)
                {
                    writeValueElement(Chart.ElCrossAx,String.valueOf(axesSequence.IvAxisSequence.Axis.AxisId));
                }
                else if (axesSequence.DvAxisSequence2 != null)
                {
                    writeValueElement(Chart.ElCrossAx,String.valueOf(axesSequence.DvAxisSequence2.Axis.AxisId));
                }
                  
                // c:crosses or c:crossesAt
                // c:crossBetween
                // c:majorUnit
                mapMajorUnit(axesSequence.DvAxisSequence.ValueRange);
                // c:minorUnit
                mapMinorUnit(axesSequence.DvAxisSequence.ValueRange);
            }
            // c:dispUnits
            _writer.writeEndElement();
            // c:valAx
            if (axesSequence.DvAxisSequence2 != null)
            {
                _writer.WriteStartElement(Chart.Prefix, Chart.ElValAx, Chart.Ns);
                {
                    // c:axId
                    writeValueElement(Chart.ElAxId,String.valueOf(axesSequence.DvAxisSequence2.Axis.AxisId));
                    // c:scaling
                    mapScaling(axesSequence.DvAxisSequence2.ValueRange);
                    // c:delete
                    // c:axPos
                    // TODO: find mapping
                    writeValueElement(Chart.ElAxPos,"b");
                    for (AttachedLabelSequence attachedLabelSequence : axesSequence.AttachedLabelSequences)
                    {
                        // c:majorGridlines
                        // c:minorGridlines
                        // c:title
                        if (attachedLabelSequence.ObjectLink != null && attachedLabelSequence.ObjectLink.wLinkObj == ObjectType.DVAxis)
                        {
                            attachedLabelSequence.Convert(new TitleMapping(this.getWorkbookContext(),this.getChartContext()));
                            break;
                        }
                         
                    }
                    // c:numFmt
                    // c:majorTickMark
                    // c:minorTickMark
                    // c:tickLblPos
                    // c:spPr
                    // c:txPr
                    // c:crossAx
                    if (axesSequence.DvAxisSequence != null)
                    {
                        writeValueElement(Chart.ElCrossAx,String.valueOf(axesSequence.DvAxisSequence.Axis.AxisId));
                    }
                     
                    // c:crosses or c:crossesAt
                    // c:crossBetween
                    // c:majorUnit
                    mapMajorUnit(axesSequence.DvAxisSequence2.ValueRange);
                    // c:minorUnit
                    mapMinorUnit(axesSequence.DvAxisSequence2.ValueRange);
                }
                // c:dispUnits
                _writer.writeEndElement();
            }
             
            // c:valAx
            if (axesSequence.SeriesAxisSequence != null)
            {
                // c:serAx
                _writer.WriteStartElement(Chart.Prefix, Chart.ElSerAx, Chart.Ns);
                {
                    mapIvAxis(axesSequence.SeriesAxisSequence,axesSequence);
                }
                _writer.writeEndElement();
            }
             
        }
         
    }

    // c:serAx
    private void mapIvAxis(IvAxisSequence ivAxisSequence, AxesSequence axesSequence) throws Exception {
        // EG_AxShared
        // c:axId
        writeValueElement(Chart.ElAxId,String.valueOf(ivAxisSequence.Axis.AxisId));
        // c:scaling
        _writer.WriteStartElement(Chart.Prefix, Chart.ElScaling, Chart.Ns);
        {
            // c:logBase
            // c:orientation
            writeValueElement(Chart.ElOrientation,ivAxisSequence.CatSerRange.fReverse ? "maxMin" : "minMax");
        }
        // c:max
        // c:min
        _writer.writeEndElement();
        // c:scaling
        // c:delete
        // c:axPos
        // TODO: find mapping
        writeValueElement(Chart.ElAxPos,"b");
        for (AttachedLabelSequence attachedLabelSequence : axesSequence.AttachedLabelSequences)
        {
            // c:majorGridlines
            // c:minorGridlines
            // c:title
            if (attachedLabelSequence.ObjectLink != null && attachedLabelSequence.ObjectLink.wLinkObj == ObjectType.IVAxis)
            {
                attachedLabelSequence.Convert(new TitleMapping(this.getWorkbookContext(),this.getChartContext()));
                break;
            }
             
        }
        // c:numFmt
        // c:majorTickMark
        // c:minorTickMark
        // c:tickLblPos
        // c:spPr
        // c:txPr
        // c:crossAx
        writeValueElement(Chart.ElCrossAx,String.valueOf(axesSequence.DvAxisSequence.Axis.AxisId));
    }

    // c:crosses or c:crossesAt
    private void mapScaling(ValueRange valueRange) throws Exception {
        // c:scaling
        _writer.WriteStartElement(Chart.Prefix, Chart.ElScaling, Chart.Ns);
        {
            // c:logBase
            // TODO: support for custom logarithmic base (The default base of the logarithmic scale is 10,
            //  unless a CrtMlFrt record follows this record, specifying the base in a XmlTkLogBaseFrt)
            // c:orientation
            writeValueElement(Chart.ElOrientation,(valueRange == null || valueRange.fReversed) ? "maxMin" : "minMax");
            // c:max
            if (valueRange != null && !valueRange.fAutoMax)
            {
                writeValueElement(Chart.ElMax,DoubleSupport.ToString(valueRange.numMax, LocaleSupport.INVARIANT));
            }
             
            // c:min
            if (valueRange != null && !valueRange.fAutoMin)
            {
                writeValueElement(Chart.ElMin,DoubleSupport.ToString(valueRange.numMin, LocaleSupport.INVARIANT));
            }
             
        }
        _writer.writeEndElement();
    }

    // c:scaling
    private void mapMajorUnit(ValueRange valueRange) throws Exception {
        if (valueRange != null && !valueRange.fAutoMajor)
        {
            writeValueElement(Chart.ElMajorUnit,DoubleSupport.ToString(valueRange.numMajor, LocaleSupport.INVARIANT));
        }
         
    }

    private void mapMinorUnit(ValueRange valueRange) throws Exception {
        if (valueRange != null && !valueRange.fAutoMinor)
        {
            writeValueElement(Chart.ElMinorUnit,DoubleSupport.ToString(valueRange.numMinor, LocaleSupport.INVARIANT));
        }
         
    }

}


