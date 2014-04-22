//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:29 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ClientAnchor;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.ChartPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.BaseTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.Chart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.DocumentProperties;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.GraphicalObject;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.Dml.SpreadsheetDrawing;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.DrawingML.DrawingsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ChartSheetContentSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DrawingsGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectGroup;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ObjectsSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Chart;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.MsoDrawing;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.OfficeArtClientAnchorSheet;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartMapping;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.Tools.PtValue;

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
public class DrawingMapping  extends AbstractOpenXmlMapping implements IMapping<ChartSheetContentSequence>, IMapping<ObjectsSequence>
{
    ExcelContext _xlsContext;
    DrawingsPart _drawingsPart;
    boolean _isChartsheet;
    public DrawingMapping(ExcelContext xlsContext, DrawingsPart targetPart, boolean isChartsheet) throws Exception {
        super(targetPart.getXmlWriter());
        this._xlsContext = xlsContext;
        this._drawingsPart = targetPart;
        this._isChartsheet = isChartsheet;
    }

    public void apply(ChartSheetContentSequence chartSheetContentSequence) throws Exception {
        _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElWsDr, SpreadsheetDrawing.Ns);
        _writer.WriteAttributeString("xmlns", SpreadsheetDrawing.Prefix, "", SpreadsheetDrawing.Ns);
        _writer.WriteAttributeString("xmlns", Dml.Prefix, "", Dml.Ns);
        if (this._isChartsheet)
        {
            _writer.WriteStartElement(SpreadsheetDrawing.ElAbsoluteAnchor, SpreadsheetDrawing.Ns);
            {
                Chart chart = chartSheetContentSequence.ChartFormatsSequence.Chart;
                // NOTE: Excel seems to somehow round the pos and ext values. The exact calculation is not documented.
                //   Besides, Excel might write negative values which are corrected to 0 by Excel on load time.
                //
                // xdr:pos
                _writer.WriteStartElement(SpreadsheetDrawing.ElPos, SpreadsheetDrawing.Ns);
                _writer.writeAttributeString(BaseTypes.AttrX, String.valueOf(Math.max(0,new PtValue(chart.x.getValue()).toEmu())));
                _writer.writeAttributeString(BaseTypes.AttrY, String.valueOf(Math.max(0,new PtValue(chart.y.getValue()).toEmu())));
                _writer.writeEndElement();
                // xdr:ext
                _writer.WriteStartElement(SpreadsheetDrawing.ElExt, SpreadsheetDrawing.Ns);
                _writer.writeAttributeString(BaseTypes.AttrCx, String.valueOf(Math.max(0,new PtValue(chart.dx.getValue()).toEmu())));
                _writer.writeAttributeString(BaseTypes.AttrCy, String.valueOf(Math.max(0,new PtValue(chart.dy.getValue()).toEmu())));
                _writer.writeEndElement();
                // insert EG_ObjectChoices type
                insertObjectChoices(chartSheetContentSequence);
            }
            _writer.writeEndElement();
        }
        else
        {
        } 
        // absoluteAnchor
        // embedded drawing
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    /**
    * Mapping definition for embedded objects
    * 
    *  @param objectsSequence
    */
    public void apply(ObjectsSequence objectsSequence) throws Exception {
        _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElWsDr, SpreadsheetDrawing.Ns);
        _writer.WriteAttributeString("xmlns", SpreadsheetDrawing.Prefix, "", SpreadsheetDrawing.Ns);
        _writer.WriteAttributeString("xmlns", Dml.Prefix, "", Dml.Ns);
        for (DrawingsGroup drawingsGroup : objectsSequence.DrawingsGroup)
        {
            // TODO: currently only embedded charts are mapped. Shapes and images are not yet implemented.
            //    The check on the type of object would have to be removed here once shapes and images are supported.
            //
            ObjectGroup objGroup = drawingsGroup.Objects.Find(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(p) => {
                return p.ChartSheetSequence != null;
            }" */);
            if (objGroup != null)
            {
                MsoDrawing msoDrawing = drawingsGroup.MsoDrawing;
                // find OfficeArtClientAnchorSheet with drawing
                RegularContainer container = msoDrawing.rgChildRec instanceof RegularContainer ? (RegularContainer)msoDrawing.rgChildRec : (RegularContainer)null;
                if (container != null)
                {
                    ClientAnchor clientAnchor = container.firstDescendantWithType();
                    if (clientAnchor != null)
                    {
                        OfficeArtClientAnchorSheet oartClientAnchor = new OfficeArtClientAnchorSheet(clientAnchor.RawData);
                        // xdr:twoCellAnchor
                        _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElTwoCellAnchor, SpreadsheetDrawing.Ns);
                        String editAs = "absolute";
                        if (oartClientAnchor.fSize && oartClientAnchor.fMove)
                        {
                            // Move and resize with anchor cells
                            editAs = "twoCell";
                        }
                        else if (!oartClientAnchor.fSize && oartClientAnchor.fMove)
                        {
                            // Move with cells but do not resize
                            editAs = "oneCell";
                        }
                          
                        _writer.writeAttributeString(SpreadsheetDrawing.AttrEditAs, editAs);
                        {
                            // xdr:from
                            _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElFrom, SpreadsheetDrawing.Ns);
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElCol, SpreadsheetDrawing.Ns, oartClientAnchor.colL.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElColOff, SpreadsheetDrawing.Ns, oartClientAnchor.dxL.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElRow, SpreadsheetDrawing.Ns, oartClientAnchor.rwT.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElRowOff, SpreadsheetDrawing.Ns, oartClientAnchor.dyT.toString());
                            _writer.writeEndElement();
                            // xdr:from
                            // xdr:to
                            _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElTo, SpreadsheetDrawing.Ns);
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElCol, SpreadsheetDrawing.Ns, oartClientAnchor.colR.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElColOff, SpreadsheetDrawing.Ns, oartClientAnchor.dxR.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElRow, SpreadsheetDrawing.Ns, oartClientAnchor.rwB.toString());
                            _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElRowOff, SpreadsheetDrawing.Ns, oartClientAnchor.dyB.toString());
                            _writer.writeEndElement();
                            // xdr:to
                            ObjectGroup objectGroup = drawingsGroup.Objects.Find(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(p) => {
                                return p.ChartSheetSequence != null;
                            }" */);
                            if (objectGroup != null)
                            {
                                ChartSheetContentSequence chartSheetContentSequence = objectGroup.ChartSheetSequence.ChartSheetContentSequence;
                                insertObjectChoices(chartSheetContentSequence);
                            }
                             
                        }
                        _writer.writeEndElement();
                    }
                     
                }
                 
            }
             
        }
        // xdr:twoCellAnchor
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void insertObjectChoices(ChartSheetContentSequence chartSheetContentSequence) throws Exception {
        _writer.WriteStartElement(SpreadsheetDrawing.ElGraphicFrame, SpreadsheetDrawing.Ns);
        {
            // TODO: add graphic properties
            _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElNvGraphicFramePr, SpreadsheetDrawing.Ns);
            {
                _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElCNvPr, SpreadsheetDrawing.Ns);
                _writer.writeAttributeString(DocumentProperties.AttrId, String.valueOf(this._drawingsPart.getRelId()));
                _writer.writeAttributeString(DocumentProperties.AttrName, "Shape");
                _writer.writeEndElement();
                // xdr:cNvPr
                _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElCNvGraphicFramePr, SpreadsheetDrawing.Ns);
                _writer.WriteStartElement(Dml.Prefix, DocumentProperties.ElGraphicFrameLocks, Dml.Ns);
                _writer.writeAttributeString(DocumentProperties.AttrNoGrp, "1");
                _writer.writeEndElement();
                // a:graphicFrameLocks
                _writer.writeEndElement();
            }
            // xdr:cNvGraphicFramePr
            _writer.writeEndElement();
            // xdr:nvGraphicFramePr
            // xdr:xfrm
            _writer.WriteStartElement(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElXfrm, SpreadsheetDrawing.Ns);
            {
                _writer.WriteStartElement(Dml.Prefix, BaseTypes.ElOff, Dml.Ns);
                _writer.writeAttributeString(BaseTypes.AttrX, "0");
                _writer.writeAttributeString(BaseTypes.AttrY, "0");
                _writer.writeEndElement();
                // a:off
                _writer.WriteStartElement(Dml.Prefix, BaseTypes.ElExt, Dml.Ns);
                _writer.writeAttributeString(BaseTypes.AttrCx, "0");
                _writer.writeAttributeString(BaseTypes.AttrCy, "0");
                _writer.writeEndElement();
            }
            // a:ext
            _writer.writeEndElement();
            // xdr:xfrm
            _writer.WriteStartElement(GraphicalObject.ElGraphic, Dml.Ns);
            {
                _writer.WriteStartElement(GraphicalObject.ElGraphicData, Dml.Ns);
                _writer.writeAttributeString(GraphicalObject.AttrUri, Chart.Ns);
                // create and convert chart part
                ChartPart chartPart = this._drawingsPart.addChartPart();
                ChartContext chartContext = new ChartContext(chartPart,chartSheetContentSequence,this._isChartsheet ? DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation.Chartsheet : DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ChartContext.ChartLocation.Embedded);
                chartSheetContentSequence.Convert(new ChartMapping(this._xlsContext,chartContext));
                _writer.WriteStartElement(Chart.Prefix, Chart.ElChart, Chart.Ns);
                _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, chartPart.getRelIdToString());
                _writer.writeEndElement();
                // c:chart
                _writer.writeEndElement();
            }
            // a:graphicData
            _writer.writeEndElement();
        }
        // a:graphic
        _writer.writeEndElement();
        // a:graphicFrame
        _writer.WriteElementString(SpreadsheetDrawing.Prefix, SpreadsheetDrawing.ElClientData, SpreadsheetDrawing.Ns, "");
    }

}


