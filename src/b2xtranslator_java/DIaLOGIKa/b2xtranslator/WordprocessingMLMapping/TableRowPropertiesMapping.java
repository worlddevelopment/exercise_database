//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:21 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TablePropertyExceptions;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData.RevisionType;

public class TableRowPropertiesMapping  extends PropertiesMapping implements IMapping<TablePropertyExceptions>
{
    private XmlElement _trPr;
    private XmlElement _tblPrEx;
    //private XmlElement _tblBorders;
    //private BorderCode brcLeft, brcTop, brcBottom, brcRight, brcHorz, brcVert;
    private CharacterPropertyExceptions _rowEndChpx;
    public TableRowPropertiesMapping(XmlWriter writer, CharacterPropertyExceptions rowEndChpx) throws Exception {
        super(writer);
        _trPr = _nodeFactory.createElement("w", "trPr", OpenXmlNamespaces.WordprocessingML);
        _tblPrEx = _nodeFactory.createElement("w", "tblPrEx", OpenXmlNamespaces.WordprocessingML);
        //_tblBorders = _nodeFactory.CreateElement("w", "tblBorders", OpenXmlNamespaces.WordprocessingML);
        _rowEndChpx = rowEndChpx;
    }

    public void apply(TablePropertyExceptions tapx) throws Exception {
        //delete infos
        RevisionData rev = new RevisionData(_rowEndChpx);
        if (_rowEndChpx != null && rev.Type == RevisionType.Deleted)
        {
            XmlElement del = _nodeFactory.createElement("w", "del", OpenXmlNamespaces.WordprocessingML);
            _trPr.appendChild(del);
        }
         
        for (SinglePropertyModifier sprm : tapx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmTDefTable: 
                    break;
                case sprmTTableHeader: 
                    //SprmTDefTable tdef = new SprmTDefTable(sprm.Arguments);
                    //header row
                    boolean fHeader = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool(sprm.Arguments[0]);
                    if (fHeader)
                    {
                        XmlElement header = _nodeFactory.createElement("w", "tblHeader", OpenXmlNamespaces.WordprocessingML);
                        _trPr.appendChild(header);
                    }
                     
                    break;
                case sprmTWidthAfter: 
                    //width after
                    XmlElement wAfter = _nodeFactory.createElement("w", "wAfter", OpenXmlNamespaces.WordprocessingML);
                    XmlAttribute wAfterValue = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
                    wAfterValue.setValue(System.BitConverter.ToInt16(sprm.Arguments, 1).toString());
                    wAfter.getAttributes().add(wAfterValue);
                    XmlAttribute wAfterType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
                    wAfterType.setValue("dxa");
                    wAfter.getAttributes().add(wAfterType);
                    _trPr.appendChild(wAfter);
                    break;
                case sprmTWidthBefore: 
                    //width before
                    short before = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    if (before != 0)
                    {
                        XmlElement wBefore = _nodeFactory.createElement("w", "wBefore", OpenXmlNamespaces.WordprocessingML);
                        XmlAttribute wBeforeValue = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
                        wBeforeValue.setValue(String.valueOf(before));
                        wBefore.getAttributes().add(wBeforeValue);
                        XmlAttribute wBeforeType = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
                        wBeforeType.setValue("dxa");
                        wBefore.getAttributes().add(wBeforeType);
                        _trPr.appendChild(wBefore);
                    }
                     
                    break;
                case sprmTDyaRowHeight: 
                    //row height
                    XmlElement rowHeight = _nodeFactory.createElement("w", "trHeight", OpenXmlNamespaces.WordprocessingML);
                    XmlAttribute rowHeightVal = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                    XmlAttribute rowHeightRule = _nodeFactory.createAttribute("w","hRule",OpenXmlNamespaces.WordprocessingML);
                    short rH = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    if (rH > 0)
                    {
                        rowHeightRule.setValue("atLeast");
                    }
                    else
                    {
                        rowHeightRule.setValue("exact");
                        rH *= -1;
                    } 
                    rowHeightVal.setValue(String.valueOf(rH));
                    rowHeight.getAttributes().add(rowHeightVal);
                    rowHeight.getAttributes().add(rowHeightRule);
                    _trPr.appendChild(rowHeight);
                    break;
                case sprmTFCantSplit: 
                case sprmTFCantSplit90: 
                    //can't split
                    appendFlagElement(_trPr,sprm,"cantSplit",true);
                    break;
                case sprmTIpgp: 
                    //div id
                    appendValueElement(_trPr,"divId",System.BitConverter.ToInt32(sprm.Arguments, 0).toString(),true);
                    break;
            
            }
        }
        /**
        * /borders 80 exceptions
        */
        //case SinglePropertyModifier.OperationCode.sprmTTableBorders80:
        //    byte[] brc80 = new byte[4];
        //    //top border
        //    Array.Copy(sprm.Arguments, 0, brc80, 0, 4);
        //    brcTop = new BorderCode(brc80);
        //    //left
        //    Array.Copy(sprm.Arguments, 4, brc80, 0, 4);
        //    brcLeft = new BorderCode(brc80);
        //    //bottom
        //    Array.Copy(sprm.Arguments, 8, brc80, 0, 4);
        //    brcBottom = new BorderCode(brc80);
        //    //right
        //    Array.Copy(sprm.Arguments, 12, brc80, 0, 4);
        //    brcRight = new BorderCode(brc80);
        //    //inside H
        //    Array.Copy(sprm.Arguments, 16, brc80, 0, 4);
        //    brcHorz = new BorderCode(brc80);
        //    //inside V
        //    Array.Copy(sprm.Arguments, 20, brc80, 0, 4);
        //    brcVert = new BorderCode(brc80);
        //    break;
        /**
        * /border exceptions
        */
        //case SinglePropertyModifier.OperationCode.sprmTTableBorders:
        //    byte[] brc = new byte[8];
        //    //top border
        //    Array.Copy(sprm.Arguments, 0, brc, 0, 8);
        //    brcTop = new BorderCode(brc);
        //    //left
        //    Array.Copy(sprm.Arguments, 8, brc, 0, 8);
        //    brcLeft = new BorderCode(brc);
        //    //bottom
        //    Array.Copy(sprm.Arguments, 16, brc, 0, 8);
        //    brcBottom = new BorderCode(brc);
        //    //right
        //    Array.Copy(sprm.Arguments, 24, brc, 0, 8);
        //    brcRight = new BorderCode(brc);
        //    //inside H
        //    Array.Copy(sprm.Arguments, 32, brc, 0, 8);
        //    brcHorz = new BorderCode(brc);
        //    //inside V
        //    Array.Copy(sprm.Arguments, 40, brc, 0, 8);
        //    brcVert = new BorderCode(brc);
        //    break;
        /**
        * /set borders
        */
        //if (brcTop != null)
        //{
        //    XmlNode topBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "top", OpenXmlNamespaces.WordprocessingML);
        //    appendBorderAttributes(brcTop, topBorder);
        //    addOrSetBorder(_tblBorders, topBorder);
        //}
        //if (brcLeft != null)
        //{
        //    XmlNode leftBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "left", OpenXmlNamespaces.WordprocessingML);
        //    appendBorderAttributes(brcLeft, leftBorder);
        //    addOrSetBorder(_tblBorders, leftBorder);
        //}
        //if (brcBottom != null)
        //{
        //    XmlNode bottomBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "bottom", OpenXmlNamespaces.WordprocessingML);
        //    appendBorderAttributes(brcBottom, bottomBorder);
        //    addOrSetBorder(_tblBorders, bottomBorder);
        //}
        //if (brcRight != null)
        //{
        //    XmlNode rightBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "right", OpenXmlNamespaces.WordprocessingML);
        //    appendBorderAttributes(brcRight, rightBorder);
        //    addOrSetBorder(_tblBorders, rightBorder);
        //}
        //if (brcHorz != null)
        //{
        //     XmlNode insideHBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "insideH", OpenXmlNamespaces.WordprocessingML);
        //     appendBorderAttributes(brcHorz, insideHBorder);
        //     addOrSetBorder(_tblBorders, insideHBorder);
        //}
        //if (brcVert != null)
        //{
        //    XmlNode insideVBorder = _nodeFactory.CreateNode(XmlNodeType.Element, "w", "insideV", OpenXmlNamespaces.WordprocessingML);
        //    appendBorderAttributes(brcVert, insideVBorder);
        //    addOrSetBorder(_tblBorders, insideVBorder);
        //}
        //if (_tblBorders.ChildNodes.Count > 0)
        //{
        //    _tblPrEx.AppendChild(_tblBorders);
        //}
        //set exceptions
        if (_tblPrEx.getChildNodes().size() > 0)
        {
            _trPr.appendChild(_tblPrEx);
        }
         
        //write Properties
        if (_trPr.getChildNodes().size() > 0 || _trPr.getAttributes().size() > 0)
        {
            _trPr.WriteTo(_writer);
        }
         
    }

}


