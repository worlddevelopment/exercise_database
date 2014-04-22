//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:21 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlNode;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SprmTDefTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheet;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.StyleSheetMapping;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class TablePropertiesMapping  extends PropertiesMapping implements IMapping<TablePropertyExceptions>
{
    private XmlElement _tblPr;
    private XmlElement _tblGrid;
    private XmlElement _tblBorders;
    private StyleSheet _styles;
    private CSList<Short> _grid;
    private BorderCode brcLeft, brcTop, brcBottom, brcRight, brcHorz, brcVert;
    private enum WidthType
    {
        nil,
        auto,
        pct,
        dxa
    }
    public TablePropertiesMapping(XmlWriter writer, StyleSheet styles, CSList<Short> grid) throws Exception {
        super(writer);
        _styles = styles;
        _tblPr = _nodeFactory.createElement("w", "tblPr", OpenXmlNamespaces.WordprocessingML);
        _tblBorders = _nodeFactory.createElement("w", "tblBorders", OpenXmlNamespaces.WordprocessingML);
        _grid = grid;
    }

    public void apply(TablePropertyExceptions tapx) throws Exception {
        XmlElement tblBorders = _nodeFactory.createElement("w", "tblBorders", OpenXmlNamespaces.WordprocessingML);
        XmlElement tblCellMar = _nodeFactory.createElement("w", "tblCellMar", OpenXmlNamespaces.WordprocessingML);
        XmlElement tblLayout = _nodeFactory.createElement("w", "tblLayout", OpenXmlNamespaces.WordprocessingML);
        XmlElement tblpPr = _nodeFactory.createElement("w", "tblpPr", OpenXmlNamespaces.WordprocessingML);
        XmlAttribute layoutType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
        layoutType.setValue("fixed");
        short tblIndent = 0;
        short gabHalf = 0;
        short marginLeft = 0;
        short marginRight = 0;
        for (SinglePropertyModifier sprm : tapx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmTDxaGapHalf: 
                    gabHalf = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    break;
                case sprmTDefTable: 
                    //table definition
                    SprmTDefTable tDef = new SprmTDefTable(sprm.Arguments);
                    //Workaround for retrieving the indent of the table:
                    //In some files there is a indent but no sprmTWidthIndent is set.
                    //For this cases we can calculate the indent of the table by getting the
                    //first boundary of the TDef and adding the padding of the cells
                    tblIndent = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    //add the gabHalf
                    tblIndent += gabHalf;
                    break;
                case sprmTTableWidth: 
                    //If there follows a real sprmTWidthIndent, this value will be overwritten
                    //preferred table width
                    WidthType fts = (WidthType)sprm.Arguments[0];
                    short width = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    XmlElement tblW = _nodeFactory.createElement("w", "tblW", OpenXmlNamespaces.WordprocessingML);
                    XmlAttribute w = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
                    w.setValue(String.valueOf(width));
                    XmlAttribute type = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
                    type.setValue(fts.toString());
                    tblW.getAttributes().add(type);
                    tblW.getAttributes().add(w);
                    _tblPr.appendChild(tblW);
                    break;
                case sprmTJc: 
                case sprmTJcRow: 
                    //justification
                    appendValueElement(_tblPr,"jc",((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.JustificationCode)sprm.Arguments[0]).toString(),true);
                    break;
                case sprmTWidthIndent: 
                    //indent
                    tblIndent = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    break;
                case sprmTIstd: 
                case sprmTIstdPermute: 
                    //style
                    short styleIndex = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    if (_styles.Styles.size() > styleIndex)
                    {
                        String id = StyleSheetMapping.MakeStyleId(_styles.Styles[styleIndex]);
                        if (!StringSupport.equals(id, "TableNormal"))
                        {
                            appendValueElement(_tblPr,"tblStyle",id,true);
                        }
                         
                    }
                     
                    break;
                case sprmTFBiDi: 
                case sprmTFBiDi90: 
                    //bidi
                    appendValueElement(_tblPr,"bidiVisual",System.BitConverter.ToInt16(sprm.Arguments, 0).toString(),true);
                    break;
                case sprmTTlp: 
                    //table look
                    appendValueElement(_tblPr,"tblLook",String.format(StringSupport.CSFmtStrToJFmtStr("{0:x4}"),System.BitConverter.ToInt16(sprm.Arguments, 2)),true);
                    break;
                case sprmTFAutofit: 
                    //autofit
                    if (sprm.Arguments[0] == 1)
                        layoutType.setValue("auto");
                     
                    break;
                case sprmTCellPadding: 
                case sprmTCellPaddingDefault: 
                case sprmTCellPaddingOuter: 
                    //default cell padding (margin)
                    byte grfbrc = sprm.Arguments[2];
                    short wMar = System.BitConverter.ToInt16(sprm.Arguments, 4);
                    if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grfbrc,0x01))
                        appendDxaElement(tblCellMar,"top",String.valueOf(wMar),true);
                     
                    if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grfbrc,0x02))
                        marginLeft = wMar;
                     
                    if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grfbrc,0x04))
                        appendDxaElement(tblCellMar,"bottom",String.valueOf(wMar),true);
                     
                    if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)grfbrc,0x08))
                        marginRight = wMar;
                     
                    break;
                case sprmTCHorzBands: 
                    //row count
                    appendValueElement(_tblPr,"tblStyleRowBandSize",String.valueOf(sprm.Arguments[0]),true);
                    break;
                case sprmTCVertBands: 
                    //col count
                    appendValueElement(_tblPr,"tblStyleColBandSize",String.valueOf(sprm.Arguments[0]),true);
                    break;
                case sprmTFNoAllowOverlap: 
                    //overlap
                    boolean noOverlap = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool(sprm.Arguments[0]);
                    String tblOverlapVal = "overlap";
                    if (noOverlap)
                        tblOverlapVal = "never";
                     
                    appendValueElement(_tblPr,"tblOverlap",tblOverlapVal,true);
                    break;
                case sprmTSetShdTable: 
                    //shading
                    ShadingDescriptor desc = new ShadingDescriptor(sprm.Arguments);
                    appendShading(_tblPr,desc);
                    break;
                case sprmTTableBorders80: 
                    //borders 80 exceptions
                    byte[] brc80 = new byte[4];
                    //top border
                    Array.Copy(sprm.Arguments, 0, brc80, 0, 4);
                    brcTop = new BorderCode(brc80);
                    //left
                    Array.Copy(sprm.Arguments, 4, brc80, 0, 4);
                    brcLeft = new BorderCode(brc80);
                    //bottom
                    Array.Copy(sprm.Arguments, 8, brc80, 0, 4);
                    brcBottom = new BorderCode(brc80);
                    //right
                    Array.Copy(sprm.Arguments, 12, brc80, 0, 4);
                    brcRight = new BorderCode(brc80);
                    //inside H
                    Array.Copy(sprm.Arguments, 16, brc80, 0, 4);
                    brcHorz = new BorderCode(brc80);
                    //inside V
                    Array.Copy(sprm.Arguments, 20, brc80, 0, 4);
                    brcVert = new BorderCode(brc80);
                    break;
                case sprmTTableBorders: 
                    //border exceptions
                    byte[] brc = new byte[8];
                    //top border
                    Array.Copy(sprm.Arguments, 0, brc, 0, 8);
                    brcTop = new BorderCode(brc);
                    //left
                    Array.Copy(sprm.Arguments, 8, brc, 0, 8);
                    brcLeft = new BorderCode(brc);
                    //bottom
                    Array.Copy(sprm.Arguments, 16, brc, 0, 8);
                    brcBottom = new BorderCode(brc);
                    //right
                    Array.Copy(sprm.Arguments, 24, brc, 0, 8);
                    brcRight = new BorderCode(brc);
                    //inside H
                    Array.Copy(sprm.Arguments, 32, brc, 0, 8);
                    brcHorz = new BorderCode(brc);
                    //inside V
                    Array.Copy(sprm.Arguments, 40, brc, 0, 8);
                    brcVert = new BorderCode(brc);
                    break;
                case sprmTPc: 
                    //floating table properties
                    byte flag = sprm.Arguments[0];
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalPositionCode pcVert = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalPositionCode)((flag & 0x30) >> 4);
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HorizontalPositionCode pcHorz = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HorizontalPositionCode)((flag & 0xC0) >> 6);
                    appendValueAttribute(tblpPr,"horzAnchor",pcHorz.toString());
                    appendValueAttribute(tblpPr,"vertAnchor",pcVert.toString());
                    break;
                case sprmTDxaFromText: 
                    appendValueAttribute(tblpPr,"leftFromText",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmTDxaFromTextRight: 
                    appendValueAttribute(tblpPr,"rightFromText",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmTDyaFromText: 
                    appendValueAttribute(tblpPr,"topFromText",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmTDyaFromTextBottom: 
                    appendValueAttribute(tblpPr,"bottomFromText",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmTDxaAbs: 
                    appendValueAttribute(tblpPr,"tblpX",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmTDyaAbs: 
                    appendValueAttribute(tblpPr,"tblpY",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
            
            }
        }
        //indent
        if (tblIndent != 0)
        {
            XmlElement tblInd = _nodeFactory.createElement("w", "tblInd", OpenXmlNamespaces.WordprocessingML);
            XmlAttribute tblIndW = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
            tblIndW.setValue(String.valueOf(tblIndent));
            tblInd.getAttributes().add(tblIndW);
            XmlAttribute tblIndType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
            tblIndType.setValue("dxa");
            tblInd.getAttributes().add(tblIndType);
            _tblPr.appendChild(tblInd);
        }
         
        //append floating props
        if (tblpPr.getAttributes().size() > 0)
        {
            _tblPr.appendChild(tblpPr);
        }
         
        //set borders
        if (brcTop != null)
        {
            XmlNode topBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "top", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcTop,topBorder);
            addOrSetBorder(_tblBorders,topBorder);
        }
         
        if (brcLeft != null)
        {
            XmlNode leftBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "left", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcLeft,leftBorder);
            addOrSetBorder(_tblBorders,leftBorder);
        }
         
        if (brcBottom != null)
        {
            XmlNode bottomBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "bottom", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcBottom,bottomBorder);
            addOrSetBorder(_tblBorders,bottomBorder);
        }
         
        if (brcRight != null)
        {
            XmlNode rightBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "right", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcRight,rightBorder);
            addOrSetBorder(_tblBorders,rightBorder);
        }
         
        if (brcHorz != null)
        {
            XmlNode insideHBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "insideH", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcHorz,insideHBorder);
            addOrSetBorder(_tblBorders,insideHBorder);
        }
         
        if (brcVert != null)
        {
            XmlNode insideVBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "insideV", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(brcVert,insideVBorder);
            addOrSetBorder(_tblBorders,insideVBorder);
        }
         
        if (_tblBorders.getChildNodes().size() > 0)
        {
            _tblPr.appendChild(_tblBorders);
        }
         
        //append layout type
        tblLayout.getAttributes().add(layoutType);
        _tblPr.appendChild(tblLayout);
        //append margins
        if (marginLeft == 0 && gabHalf != 0)
        {
            appendDxaElement(tblCellMar,"left",String.valueOf(gabHalf),true);
        }
        else
        {
            appendDxaElement(tblCellMar,"left",String.valueOf(marginLeft),true);
        } 
        if (marginRight == 0 && gabHalf != 0)
        {
            appendDxaElement(tblCellMar,"right",String.valueOf(gabHalf),true);
        }
        else
        {
            appendDxaElement(tblCellMar,"right",String.valueOf(marginRight),true);
        } 
        _tblPr.appendChild(tblCellMar);
        //write Properties
        if (_tblPr.getChildNodes().size() > 0 || _tblPr.getAttributes().size() > 0)
        {
            _tblPr.WriteTo(_writer);
        }
         
        //append the grid
        _tblGrid = _nodeFactory.createElement("w", "tblGrid", OpenXmlNamespaces.WordprocessingML);
        for (short colW : _grid)
        {
            XmlElement gridCol = _nodeFactory.createElement("w", "gridCol", OpenXmlNamespaces.WordprocessingML);
            XmlAttribute gridColW = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
            gridColW.setValue(String.valueOf(colW));
            gridCol.getAttributes().add(gridColW);
            _tblGrid.appendChild(gridCol);
        }
        _tblGrid.WriteTo(_writer);
    }

}


