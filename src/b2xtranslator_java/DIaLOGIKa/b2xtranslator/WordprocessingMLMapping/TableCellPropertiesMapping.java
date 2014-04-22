//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:20 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
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
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TC80;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class TableCellPropertiesMapping  extends PropertiesMapping implements IMapping<TablePropertyExceptions>
{
    private int _gridIndex;
    private int _cellIndex;
    private XmlElement _tcPr;
    private XmlElement _tcMar;
    private XmlElement _tcBorders;
    private CSList<Short> _grid;
    private short[] _tGrid;
    private short _width;
    private DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType _ftsWidth = DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType.nil;
    private TC80 _tcDef = new TC80();
    private BorderCode _brcTop, _brcLeft, _brcRight, _brcBottom;
    /**
    * The grind span of this cell
    */
    private int _gridSpan;
    public int getGridSpan() throws Exception {
        return _gridSpan;
    }

    private enum VerticalCellAlignment
    {
        top,
        center,
        bottom
    }
    public TableCellPropertiesMapping(XmlWriter writer, CSList<Short> tableGrid, int gridIndex, int cellIndex) throws Exception {
        super(writer);
        _tcPr = _nodeFactory.createElement("w", "tcPr", OpenXmlNamespaces.WordprocessingML);
        _tcMar = _nodeFactory.createElement("w", "tcMar", OpenXmlNamespaces.WordprocessingML);
        _tcBorders = _nodeFactory.createElement("w", "tcBorders", OpenXmlNamespaces.WordprocessingML);
        _gridIndex = gridIndex;
        _grid = tableGrid;
        _cellIndex = cellIndex;
    }

    public void apply(TablePropertyExceptions tapx) throws Exception {
        for (int i = 0;i < tapx.grpprl.size();i++)
        {
            //int lastBdr = getLastTabelBorderOccurrence(tapx.grpprl);
            SinglePropertyModifier sprm = tapx.grpprl.get(i);
            switch(sprm.OpCode)
            {
                case sprmTDefTable: 
                    //Table definition SPRM
                    SprmTDefTable tdef = new SprmTDefTable(sprm.Arguments);
                    _tGrid = tdef.rgdxaCenter;
                    _tcDef = tdef.rgTc80[_cellIndex];
                    appendValueElement(_tcPr,"textDirection",_tcDef.textFlow.toString(),false);
                    if (_tcDef.vertMerge == DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag.fvmMerge)
                        appendValueElement(_tcPr,"vMerge","continue",false);
                    else if (_tcDef.vertMerge == DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag.fvmRestart)
                        appendValueElement(_tcPr,"vMerge","restart",false);
                    else if (_tcDef.vertMerge == DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalMergeFlag.fvmRestart)
                        appendValueElement(_tcPr,"vMerge","restart",false);
                       
                    appendValueElement(_tcPr,"vAlign",_tcDef.vertAlign.toString(),false);
                    if (_tcDef.fFitText)
                        appendValueElement(_tcPr,"tcFitText","",false);
                     
                    if (_tcDef.fNoWrap)
                        appendValueElement(_tcPr,"noWrap","",true);
                     
                    //_width = _tcDef.wWidth;
                    _width = (short)(tdef.rgdxaCenter[_cellIndex + 1] - tdef.rgdxaCenter[_cellIndex]);
                    _ftsWidth = _tcDef.ftsWidth;
                    break;
                case sprmTCellPadding: 
                    //borders
                    // if the sprm has a higher priority than the last sprmTTableBorder sprm in the list
                    //if (i > lastBdr)
                    //{
                    //    _brcTop = _tcDef.brcTop;
                    //    _brcLeft = _tcDef.brcLeft;
                    //    _brcRight = _tcDef.brcRight;
                    //    _brcBottom = _tcDef.brcBottom;
                    //}
                    //margins
                    byte first = sprm.Arguments[0];
                    byte lim = sprm.Arguments[1];
                    byte ftsMargin = sprm.Arguments[3];
                    short wMargin = System.BitConverter.ToInt16(sprm.Arguments, 4);
                    if (_cellIndex >= first && _cellIndex < lim)
                    {
                        BitArray borderBits = new BitArray(new byte[]{ sprm.Arguments[2] });
                        if (borderBits[0] == true)
                            appendDxaElement(_tcMar,"top",String.valueOf(wMargin),true);
                         
                        if (borderBits[1] == true)
                            appendDxaElement(_tcMar,"left",String.valueOf(wMargin),true);
                         
                        if (borderBits[2] == true)
                            appendDxaElement(_tcMar,"bottom",String.valueOf(wMargin),true);
                         
                        if (borderBits[3] == true)
                            appendDxaElement(_tcMar,"right",String.valueOf(wMargin),true);
                         
                    }
                     
                    break;
                case sprmTDefTableShd: 
                    //shading
                    //cell shading for cells 0-20
                    apppendCellShading(sprm.Arguments,_cellIndex);
                    break;
                case sprmTDefTableShd2nd: 
                    //cell shading for cells 21-42
                    apppendCellShading(sprm.Arguments,_cellIndex - 21);
                    break;
                case sprmTDefTableShd3rd: 
                    //cell shading for cells 43-62
                    apppendCellShading(sprm.Arguments,_cellIndex - 43);
                    break;
                case sprmTCellWidth: 
                    //width
                    first = sprm.Arguments[0];
                    lim = sprm.Arguments[1];
                    if (_cellIndex >= first && _cellIndex < lim)
                    {
                        _ftsWidth = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.CellWidthType)sprm.Arguments[2];
                        _width = System.BitConverter.ToInt16(sprm.Arguments, 3);
                    }
                     
                    break;
                case sprmTVertAlign: 
                    //vertical alignment
                    first = sprm.Arguments[0];
                    lim = sprm.Arguments[1];
                    if (_cellIndex >= first && _cellIndex < lim)
                        appendValueElement(_tcPr,"vAlign",((VerticalCellAlignment)sprm.Arguments[2]).toString(),true);
                     
                    break;
                case sprmTFitText: 
                    //Autofit
                    first = sprm.Arguments[0];
                    lim = sprm.Arguments[1];
                    if (_cellIndex >= first && _cellIndex < lim)
                        appendValueElement(_tcPr,"tcFitText",String.valueOf(sprm.Arguments[2]),true);
                     
                    break;
                case sprmTSetBrc: 
                    //borders (cell definition)
                    byte min = sprm.Arguments[0];
                    byte max = sprm.Arguments[1];
                    int bordersToApply = (int)sprm.Arguments[2];
                    if (_cellIndex >= min && _cellIndex < max)
                    {
                        byte[] brcBytes = new byte[8];
                        Array.Copy(sprm.Arguments, 3, brcBytes, 0, 8);
                        BorderCode border = new BorderCode(brcBytes);
                        if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bordersToApply,0x01))
                        {
                            _brcTop = border;
                        }
                         
                        if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bordersToApply,0x02))
                        {
                            _brcLeft = border;
                        }
                         
                        if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bordersToApply,0x04))
                        {
                            _brcBottom = border;
                        }
                         
                        if (DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(bordersToApply,0x08))
                        {
                            _brcRight = border;
                        }
                         
                    }
                     
                    break;
            
            }
        }
        //width
        XmlElement tcW = _nodeFactory.createElement("w", "tcW", OpenXmlNamespaces.WordprocessingML);
        XmlAttribute tcWType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
        XmlAttribute tcWVal = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
        tcWType.setValue(_ftsWidth.toString());
        tcWVal.setValue(String.valueOf(_width));
        tcW.getAttributes().add(tcWType);
        tcW.getAttributes().add(tcWVal);
        _tcPr.appendChild(tcW);
        //grid span
        _gridSpan = 1;
        if (_width > _grid.get(_gridIndex))
        {
            //check the number of merged cells
            int w = _grid.get(_gridIndex);
            for (int i = _gridIndex + 1;i < _grid.size();i++)
            {
                _gridSpan++;
                w += _grid.get(i);
                if (w >= _width)
                    break;
                 
            }
            appendValueElement(_tcPr,"gridSpan",String.valueOf(_gridSpan),true);
        }
         
        //append margins
        if (_tcMar.getChildNodes().size() > 0)
        {
            _tcPr.appendChild(_tcMar);
        }
         
        //append borders
        if (_brcTop != null)
        {
            XmlNode topBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "top", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(_brcTop,topBorder);
            addOrSetBorder(_tcBorders,topBorder);
        }
         
        if (_brcLeft != null)
        {
            XmlNode leftBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "left", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(_brcLeft,leftBorder);
            addOrSetBorder(_tcBorders,leftBorder);
        }
         
        if (_brcBottom != null)
        {
            XmlNode bottomBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "bottom", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(_brcBottom,bottomBorder);
            addOrSetBorder(_tcBorders,bottomBorder);
        }
         
        if (_brcRight != null)
        {
            XmlNode rightBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "right", OpenXmlNamespaces.WordprocessingML);
            appendBorderAttributes(_brcRight,rightBorder);
            addOrSetBorder(_tcBorders,rightBorder);
        }
         
        if (_tcBorders.getChildNodes().size() > 0)
        {
            _tcPr.appendChild(_tcBorders);
        }
         
        //write Properties
        if (_tcPr.getChildNodes().size() > 0 || _tcPr.getAttributes().size() > 0)
        {
            _tcPr.WriteTo(_writer);
        }
         
    }

    private void apppendCellShading(byte[] sprmArg, int cellIndex) throws Exception {
        //shading descriptor can have 10 bytes (Word 2000) or 2 bytes (Word 97)
        int shdLength = 2;
        if (sprmArg.length % 10 == 0)
            shdLength = 10;
         
        byte[] shdBytes = new byte[shdLength];
        //multiple cell can be formatted with the same SHD.
        //in this case there is only 1 SHD for all cells in the row.
        if ((cellIndex * shdLength) >= sprmArg.length)
        {
            //use the first SHD
            cellIndex = 0;
        }
         
        Array.Copy(sprmArg, cellIndex * shdBytes.length, shdBytes, 0, shdBytes.length);
        ShadingDescriptor shd = new ShadingDescriptor(shdBytes);
        appendShading(_tcPr,shd);
    }

    /**
    * Returns the index of the last occurence of an sprmTTableBorders or sprmTTableBorders80 sprm.
    * 
    *  @param grpprl The grpprl of sprms
    *  @return The index or -1 if no sprm is in the list
    */
    private int getLastTabelBorderOccurrence(CSList<SinglePropertyModifier> grpprl) throws Exception {
        int index = -1;
        for (int i = 0;i < grpprl.size();i++)
        {
            if (grpprl.get(i).OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmTTableBorders || grpprl.get(i).OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmTTableBorders80)
            {
                index = i;
            }
             
        }
        return index;
    }

}


