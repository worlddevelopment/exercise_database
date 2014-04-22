//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:16 AM
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
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.LineSpacingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SectionPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TabDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CharacterPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData.RevisionType;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.SectionPropertiesMapping;
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
public class ParagraphPropertiesMapping  extends PropertiesMapping implements IMapping<ParagraphPropertyExceptions>
{
    private ConversionContext _ctx;
    private XmlElement _pPr;
    private XmlElement _framePr;
    private SectionPropertyExceptions _sepx;
    private CharacterPropertyExceptions _paraEndChpx;
    private int _sectionNr;
    private WordDocument _parentDoc;
    public ParagraphPropertiesMapping(XmlWriter writer, ConversionContext ctx, WordDocument parentDoc, CharacterPropertyExceptions paraEndChpx) throws Exception {
        super(writer);
        _parentDoc = parentDoc;
        _pPr = _nodeFactory.createElement("w", "pPr", OpenXmlNamespaces.WordprocessingML);
        _framePr = _nodeFactory.createElement("w", "framePr", OpenXmlNamespaces.WordprocessingML);
        _paraEndChpx = paraEndChpx;
        _ctx = ctx;
    }

    public ParagraphPropertiesMapping(XmlWriter writer, ConversionContext ctx, WordDocument parentDoc, CharacterPropertyExceptions paraEndChpx, SectionPropertyExceptions sepx, int sectionNr) throws Exception {
        super(writer);
        _parentDoc = parentDoc;
        _pPr = _nodeFactory.createElement("w", "pPr", OpenXmlNamespaces.WordprocessingML);
        _framePr = _nodeFactory.createElement("w", "framePr", OpenXmlNamespaces.WordprocessingML);
        _paraEndChpx = paraEndChpx;
        _sepx = sepx;
        _ctx = ctx;
        _sectionNr = sectionNr;
    }

    public void apply(ParagraphPropertyExceptions papx) throws Exception {
        XmlElement ind = _nodeFactory.createElement("w", "ind", OpenXmlNamespaces.WordprocessingML);
        XmlElement numPr = _nodeFactory.createElement("w", "numPr", OpenXmlNamespaces.WordprocessingML);
        XmlElement pBdr = _nodeFactory.createElement("w", "pBdr", OpenXmlNamespaces.WordprocessingML);
        XmlElement spacing = _nodeFactory.createElement("w", "spacing", OpenXmlNamespaces.WordprocessingML);
        XmlElement jc = null;
        //append style id , do not append "Normal" style (istd 0)
        XmlElement pStyle = _nodeFactory.createElement("w", "pStyle", OpenXmlNamespaces.WordprocessingML);
        XmlAttribute styleId = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
        styleId.setValue(StyleSheetMapping.MakeStyleId(_parentDoc.Styles.Styles[papx.istd]));
        pStyle.getAttributes().add(styleId);
        _pPr.appendChild(pStyle);
        //append formatting of paragraph end mark
        if (_paraEndChpx != null)
        {
            XmlElement rPr = _nodeFactory.createElement("w", "rPr", OpenXmlNamespaces.WordprocessingML);
            //append properties
            _paraEndChpx.Convert(new CharacterPropertiesMapping(rPr,_ctx.getDoc(),new RevisionData(_paraEndChpx),papx,false));
            RevisionData rev = new RevisionData(_paraEndChpx);
            //append delete infos
            if (rev.Type == RevisionType.Deleted)
            {
                XmlElement del = _nodeFactory.createElement("w", "del", OpenXmlNamespaces.WordprocessingML);
                rPr.appendChild(del);
            }
             
            if (rPr.getChildNodes().size() > 0)
            {
                _pPr.appendChild(rPr);
            }
             
        }
         
        for (SinglePropertyModifier sprm : papx.grpprl)
        {
            switch(sprm.OpCode)
            {
                case sprmPRsid: 
                    //rsid for paragraph property enditing (write to parent element)
                    String rsid = String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),System.BitConverter.ToInt32(sprm.Arguments, 0));
                    _ctx.addRsid(rsid);
                    _writer.WriteAttributeString("w", "rsidP", OpenXmlNamespaces.WordprocessingML, rsid);
                    break;
                case sprmPIpgp: 
                    //attributes
                    XmlAttribute divId = _nodeFactory.createAttribute("w","divId",OpenXmlNamespaces.WordprocessingML);
                    divId.setValue(System.BitConverter.ToUInt32(sprm.Arguments, 0).toString());
                    _pPr.getAttributes().add(divId);
                    break;
                case sprmPFAutoSpaceDE: 
                    appendFlagAttribute(_pPr,sprm,"autoSpaceDE");
                    break;
                case sprmPFAutoSpaceDN: 
                    appendFlagAttribute(_pPr,sprm,"autoSpaceDN");
                    break;
                case sprmPFBiDi: 
                    appendFlagAttribute(_pPr,sprm,"bidi");
                    break;
                case sprmPFContextualSpacing: 
                    appendFlagAttribute(_pPr,sprm,"contextualSpacing");
                    break;
                case sprmPFKeep: 
                    //element flags
                    appendFlagElement(_pPr,sprm,"keepLines",true);
                    break;
                case sprmPFKeepFollow: 
                    appendFlagElement(_pPr,sprm,"keepNext",true);
                    break;
                case sprmPFKinsoku: 
                    appendFlagElement(_pPr,sprm,"kinsoku",true);
                    break;
                case sprmPFOverflowPunct: 
                    appendFlagElement(_pPr,sprm,"overflowPunct",true);
                    break;
                case sprmPFPageBreakBefore: 
                    appendFlagElement(_pPr,sprm,"pageBreakBefore",true);
                    break;
                case sprmPFNoAutoHyph: 
                    appendFlagElement(_pPr,sprm,"su_pPressAutoHyphens",true);
                    break;
                case sprmPFNoLineNumb: 
                    appendFlagElement(_pPr,sprm,"su_pPressLineNumbers",true);
                    break;
                case sprmPFNoAllowOverlap: 
                    appendFlagElement(_pPr,sprm,"su_pPressOverlap",true);
                    break;
                case sprmPFTopLinePunct: 
                    appendFlagElement(_pPr,sprm,"topLinePunct",true);
                    break;
                case sprmPFWidowControl: 
                    appendFlagElement(_pPr,sprm,"widowControl",true);
                    break;
                case sprmPFWordWrap: 
                    appendFlagElement(_pPr,sprm,"wordWrap",true);
                    break;
                case sprmPDxaLeft: 
                case sprmPDxaLeft80: 
                case sprmPNest: 
                case sprmPNest80: 
                    //indentation
                    appendValueAttribute(ind,"left",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmPDxcLeft: 
                    appendValueAttribute(ind,"leftChars",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmPDxaLeft1: 
                case sprmPDxaLeft180: 
                    short flValue = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    String flName;
                    if (flValue >= 0)
                    {
                        flName = "firstLine";
                    }
                    else
                    {
                        flName = "hanging";
                        flValue *= -1;
                    } 
                    appendValueAttribute(ind,flName,String.valueOf(flValue));
                    break;
                case sprmPDxcLeft1: 
                    appendValueAttribute(ind,"firstLineChars",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmPDxaRight: 
                case sprmPDxaRight80: 
                    appendValueAttribute(ind,"right",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmPDxcRight: 
                    appendValueAttribute(ind,"rightChars",System.BitConverter.ToInt16(sprm.Arguments, 0).toString());
                    break;
                case sprmPDyaBefore: 
                    //spacing
                    XmlAttribute before = _nodeFactory.createAttribute("w","before",OpenXmlNamespaces.WordprocessingML);
                    before.setValue(System.BitConverter.ToUInt16(sprm.Arguments, 0).toString());
                    spacing.getAttributes().add(before);
                    break;
                case sprmPDyaAfter: 
                    XmlAttribute after = _nodeFactory.createAttribute("w","after",OpenXmlNamespaces.WordprocessingML);
                    after.setValue(System.BitConverter.ToUInt16(sprm.Arguments, 0).toString());
                    spacing.getAttributes().add(after);
                    break;
                case sprmPFDyaAfterAuto: 
                    XmlAttribute afterAutospacing = _nodeFactory.createAttribute("w","afterAutospacing",OpenXmlNamespaces.WordprocessingML);
                    afterAutospacing.setValue(String.valueOf(sprm.Arguments[0]));
                    spacing.getAttributes().add(afterAutospacing);
                    break;
                case sprmPFDyaBeforeAuto: 
                    XmlAttribute beforeAutospacing = _nodeFactory.createAttribute("w","beforeAutospacing",OpenXmlNamespaces.WordprocessingML);
                    beforeAutospacing.setValue(String.valueOf(sprm.Arguments[0]));
                    spacing.getAttributes().add(beforeAutospacing);
                    break;
                case sprmPDyaLine: 
                    LineSpacingDescriptor lspd = new LineSpacingDescriptor(sprm.Arguments);
                    XmlAttribute line = _nodeFactory.createAttribute("w","line",OpenXmlNamespaces.WordprocessingML);
                    line.setValue(Math.Abs(lspd.dyaLine).toString());
                    spacing.getAttributes().add(line);
                    XmlAttribute lineRule = _nodeFactory.createAttribute("w","lineRule",OpenXmlNamespaces.WordprocessingML);
                    if (!lspd.fMultLinespace && lspd.dyaLine < 0)
                        lineRule.setValue("exact");
                    else if (!lspd.fMultLinespace && lspd.dyaLine > 0)
                        lineRule.setValue("atLeast");
                      
                    //no line rule means auto
                    spacing.getAttributes().add(lineRule);
                    break;
                case sprmPJc: 
                case sprmPJc80: 
                    //justification code
                    jc = _nodeFactory.createElement("w", "jc", OpenXmlNamespaces.WordprocessingML);
                    XmlAttribute jcVal = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                    jcVal.setValue(((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.JustificationCode)sprm.Arguments[0]).toString());
                    jc.getAttributes().add(jcVal);
                    break;
                case sprmPBrcTop: 
                case sprmPBrcTop80: 
                    //borders
                    //case 0x461C:
                    //case 0x4424:
                    XmlNode topBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "top", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),topBorder);
                    addOrSetBorder(pBdr,topBorder);
                    break;
                case sprmPBrcLeft: 
                case sprmPBrcLeft80: 
                    //case 0x461D:
                    //case 0x4425:
                    XmlNode leftBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "left", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),leftBorder);
                    addOrSetBorder(pBdr,leftBorder);
                    break;
                case sprmPBrcBottom: 
                case sprmPBrcBottom80: 
                    //case 0x461E:
                    //case 0x4426:
                    XmlNode bottomBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "bottom", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),bottomBorder);
                    addOrSetBorder(pBdr,bottomBorder);
                    break;
                case sprmPBrcRight: 
                case sprmPBrcRight80: 
                    //case 0x461F:
                    //case 0x4427:
                    XmlNode rightBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "right", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),rightBorder);
                    addOrSetBorder(pBdr,rightBorder);
                    break;
                case sprmPBrcBetween: 
                case sprmPBrcBetween80: 
                    //case 0x4620:
                    //case 0x4428:
                    XmlNode betweenBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "between", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),betweenBorder);
                    addOrSetBorder(pBdr,betweenBorder);
                    break;
                case sprmPBrcBar: 
                case sprmPBrcBar80: 
                    //case 0x4621:
                    //case 0x4629:
                    XmlNode barBorder = _nodeFactory.CreateNode(org.w3c.dom.Node.ELEMENT_NODE, "w", "bar", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),barBorder);
                    addOrSetBorder(pBdr,barBorder);
                    break;
                case sprmPShd80: 
                case sprmPShd: 
                    //shading
                    ShadingDescriptor desc = new ShadingDescriptor(sprm.Arguments);
                    appendShading(_pPr,desc);
                    break;
                case sprmPIlvl: 
                    //numbering
                    appendValueElement(numPr,"ilvl",String.valueOf(sprm.Arguments[0]),true);
                    break;
                case sprmPIlfo: 
                    UInt16 val = System.BitConverter.ToUInt16(sprm.Arguments, 0);
                    appendValueElement(numPr,"numId",val.toString(),true);
                    break;
                case sprmPChgTabsPapx: 
                case sprmPChgTabs: 
                    /**
                    * /check if there is a ilvl reference, if not, check the count of LVLs.
                    * /if only one LVL exists in the referenced list, create a hard reference to that LVL
                    */
                    //if (containsLvlReference(papx.grpprl) == false)
                    //{
                    //    ListFormatOverride lfo = _ctx.Doc.ListFormatOverrideTable[val];
                    //    int index = NumberingMapping.FindIndexbyId(_ctx.Doc.ListTable, lfo.lsid);
                    //    ListData lst = _ctx.Doc.ListTable[index];
                    //    if (lst.rglvl.Length == 1)
                    //    {
                    //        appendValueElement(numPr, "ilvl", "0", true);
                    //    }
                    //}
                    //tabs
                    XmlElement tabs = _nodeFactory.createElement("w", "tabs", OpenXmlNamespaces.WordprocessingML);
                    int pos = 0;
                    //read the removed tabs
                    byte itbdDelMax = sprm.Arguments[pos];
                    pos++;
                    for (int i = 0;i < itbdDelMax;i++)
                    {
                        XmlElement tab = _nodeFactory.createElement("w", "tab", OpenXmlNamespaces.WordprocessingML);
                        //clear
                        XmlAttribute tabsVal = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                        tabsVal.setValue("clear");
                        tab.getAttributes().add(tabsVal);
                        //position
                        XmlAttribute tabsPos = _nodeFactory.createAttribute("w","pos",OpenXmlNamespaces.WordprocessingML);
                        tabsPos.setValue(System.BitConverter.ToInt16(sprm.Arguments, pos).toString());
                        tab.getAttributes().add(tabsPos);
                        tabs.appendChild(tab);
                        //skip the tolerence array in sprm 0xC615
                        if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmPChgTabs)
                            pos += 4;
                        else
                            pos += 2; 
                    }
                    //read the added tabs
                    byte itbdAddMax = sprm.Arguments[pos];
                    pos++;
                    for (int i = 0;i < itbdAddMax;i++)
                    {
                        TabDescriptor tbd = new TabDescriptor(sprm.Arguments[pos + (itbdAddMax * 2) + i]);
                        XmlElement tab = _nodeFactory.createElement("w", "tab", OpenXmlNamespaces.WordprocessingML);
                        //justification
                        XmlAttribute tabsVal = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                        tabsVal.setValue(((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.JustificationCode)tbd.jc).toString());
                        tab.getAttributes().add(tabsVal);
                        //tab leader type
                        XmlAttribute leader = _nodeFactory.createAttribute("w","leader",OpenXmlNamespaces.WordprocessingML);
                        leader.setValue(((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TabLeader)tbd.tlc).toString());
                        tab.getAttributes().add(leader);
                        //position
                        XmlAttribute tabsPos = _nodeFactory.createAttribute("w","pos",OpenXmlNamespaces.WordprocessingML);
                        tabsPos.setValue(System.BitConverter.ToInt16(sprm.Arguments, pos + (i * 2)).toString());
                        tab.getAttributes().add(tabsPos);
                        tabs.appendChild(tab);
                    }
                    _pPr.appendChild(tabs);
                    break;
                case sprmPPc: 
                    //frame properties
                    //position code
                    byte flag = sprm.Arguments[0];
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalPositionCode pcVert = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.VerticalPositionCode)((flag & 0x30) >> 4);
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HorizontalPositionCode pcHorz = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.HorizontalPositionCode)((flag & 0xC0) >> 6);
                    appendValueAttribute(_framePr,"hAnchor",pcHorz.toString());
                    appendValueAttribute(_framePr,"vAnchor",pcVert.toString());
                    break;
                case sprmPWr: 
                    DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextFrameWrapping wrapping = (DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextFrameWrapping)sprm.Arguments[0];
                    appendValueAttribute(_framePr,"wrap",wrapping.toString());
                    break;
                case sprmPDxaAbs: 
                    short frameX = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"x",String.valueOf(frameX));
                    break;
                case sprmPDyaAbs: 
                    short frameY = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"y",String.valueOf(frameY));
                    break;
                case sprmPWHeightAbs: 
                    short frameHeight = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"h",String.valueOf(frameHeight));
                    break;
                case sprmPDxaWidth: 
                    short frameWidth = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"w",String.valueOf(frameWidth));
                    break;
                case sprmPDxaFromText: 
                    short frameSpaceH = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"hSpace",String.valueOf(frameSpaceH));
                    break;
                case sprmPDyaFromText: 
                    short frameSpaceV = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    appendValueAttribute(_framePr,"vSpace",String.valueOf(frameSpaceV));
                    break;
                case sprmPOutLvl: 
                    //outline level
                    appendValueElement(_pPr,"outlineLvl",String.valueOf(sprm.Arguments[0]),false);
                    break;
                default: 
                    break;
            
            }
        }
        //append frame properties
        if (_framePr.getAttributes().size() > 0)
        {
            _pPr.appendChild(_framePr);
        }
         
        //append section properties
        if (_sepx != null)
        {
            XmlElement sectPr = _nodeFactory.createElement("w", "sectPr", OpenXmlNamespaces.WordprocessingML);
            _sepx.Convert(new SectionPropertiesMapping(sectPr,_ctx,_sectionNr));
            _pPr.appendChild(sectPr);
        }
         
        //append indent
        if (ind.getAttributes().size() > 0)
            _pPr.appendChild(ind);
         
        //append spacing
        if (spacing.getAttributes().size() > 0)
            _pPr.appendChild(spacing);
         
        //append justification
        if (jc != null)
            _pPr.appendChild(jc);
         
        //append numPr
        if (numPr.getChildNodes().size() > 0)
            _pPr.appendChild(numPr);
         
        //append borders
        if (pBdr.getChildNodes().size() > 0)
            _pPr.appendChild(pBdr);
         
        //write Properties
        if (_pPr.getChildNodes().size() > 0 || _pPr.getAttributes().size() > 0)
        {
            _pPr.WriteTo(_writer);
        }
         
    }

    private boolean containsLvlReference(CSList<SinglePropertyModifier> sprms) throws Exception {
        boolean ret = false;
        for (SinglePropertyModifier sprm : sprms)
        {
            if (sprm.OpCode == DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier.OperationCode.sprmPIlvl)
            {
                ret = true;
                break;
            }
             
        }
        return ret;
    }

}


