//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:10 AM
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
import DIaLOGIKa.b2xtranslator.DocFileFormat.FontFamilyName;
import DIaLOGIKa.b2xtranslator.DocFileFormat.Global;
import DIaLOGIKa.b2xtranslator.DocFileFormat.LanguageId;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ParagraphPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheet;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheetDescription;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.DateMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.LanguageIdMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.LanguageIdMapping.LanguageType;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData.RevisionType;
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
public class CharacterPropertiesMapping  extends PropertiesMapping implements IMapping<CharacterPropertyExceptions>
{
    private WordDocument _doc;
    private XmlElement _rPr;
    private UInt16 _currentIstd = new UInt16();
    private RevisionData _revisionData;
    private boolean _styleChpx;
    private ParagraphPropertyExceptions _currentPapx;
    CSList<CharacterPropertyExceptions> _hierarchy;
    private enum SuperscriptIndex
    {
        baseline,
        superscript,
        subscript
    }
    public CharacterPropertiesMapping(XmlWriter writer, WordDocument doc, RevisionData rev, ParagraphPropertyExceptions currentPapx, boolean styleChpx) throws Exception {
        super(writer);
        _doc = doc;
        _rPr = _nodeFactory.createElement("w", "rPr", OpenXmlNamespaces.WordprocessingML);
        _revisionData = rev;
        _currentPapx = currentPapx;
        _styleChpx = styleChpx;
        _currentIstd = UInt16.MaxValue;
    }

    public CharacterPropertiesMapping(XmlElement rPr, WordDocument doc, RevisionData rev, ParagraphPropertyExceptions currentPapx, boolean styleChpx) throws Exception {
        super(null);
        _doc = doc;
        _nodeFactory = rPr.getOwnerDocument();
        _rPr = rPr;
        _revisionData = rev;
        _currentPapx = currentPapx;
        _styleChpx = styleChpx;
        _currentIstd = UInt16.MaxValue;
    }

    public void apply(CharacterPropertyExceptions chpx) throws Exception {
        //convert the normal SPRMS
        convertSprms(chpx.grpprl,_rPr);
        //apend revision changes
        if (_revisionData.Type == RevisionType.Changed)
        {
            XmlElement rPrChange = _nodeFactory.createElement("w", "rPrChange", OpenXmlNamespaces.WordprocessingML);
            //date
            _revisionData.Dttm.Convert(new DateMapping(rPrChange));
            //author
            XmlAttribute author = _nodeFactory.createAttribute("w","author",OpenXmlNamespaces.WordprocessingML);
            author.setValue(_doc.RevisionAuthorTable.Strings[_revisionData.Isbt]);
            rPrChange.getAttributes().add(author);
            //convert revision stack
            convertSprms(_revisionData.Changes,rPrChange);
            _rPr.appendChild(rPrChange);
        }
         
        //write properties
        if (_writer != null && (_rPr.getChildNodes().size() > 0 || _rPr.getAttributes().size() > 0))
        {
            _rPr.WriteTo(_writer);
        }
         
    }

    private void convertSprms(CSList<SinglePropertyModifier> sprms, XmlElement parent) throws Exception {
        XmlElement shd = _nodeFactory.createElement("w", "shd", OpenXmlNamespaces.WordprocessingML);
        XmlElement rFonts = _nodeFactory.createElement("w", "rFonts", OpenXmlNamespaces.WordprocessingML);
        XmlElement color = _nodeFactory.createElement("w", "color", OpenXmlNamespaces.WordprocessingML);
        XmlAttribute colorVal = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
        XmlElement lang = _nodeFactory.createElement("w", "lang", OpenXmlNamespaces.WordprocessingML);
        for (SinglePropertyModifier sprm : sprms)
        {
            switch(((Enum)sprm.OpCode).ordinal())
            {
                case 0x4A30: 
                    //style id
                    _currentIstd = System.BitConverter.ToUInt16(sprm.Arguments, 0);
                    appendValueElement(parent, "rStyle", StyleSheetMapping.MakeStyleId(_doc.Styles.Styles[_currentIstd]), true);
                    break;
                case 0x085A: 
                    //Element flags
                    appendFlagElement(parent,sprm,"rtl",true);
                    break;
                case 0x0835: 
                    appendFlagElement(parent,sprm,"b",true);
                    break;
                case 0x085C: 
                    appendFlagElement(parent,sprm,"bCs",true);
                    break;
                case 0x083B: 
                    appendFlagElement(parent,sprm,"caps",true);
                        ;
                    break;
                case 0x0882: 
                    appendFlagElement(parent,sprm,"cs",true);
                    break;
                case 0x2A53: 
                    appendFlagElement(parent,sprm,"dstrike",true);
                    break;
                case 0x0858: 
                    appendFlagElement(parent,sprm,"emboss",true);
                    break;
                case 0x0854: 
                    appendFlagElement(parent,sprm,"imprint",true);
                    break;
                case 0x0836: 
                    appendFlagElement(parent,sprm,"i",true);
                    break;
                case 0x085D: 
                    appendFlagElement(parent,sprm,"iCs",true);
                    break;
                case 0x0875: 
                    appendFlagElement(parent,sprm,"noProof",true);
                    break;
                case 0x0838: 
                    appendFlagElement(parent,sprm,"outline",true);
                    break;
                case 0x0839: 
                    appendFlagElement(parent,sprm,"shadow",true);
                    break;
                case 0x083A: 
                    appendFlagElement(parent,sprm,"smallCaps",true);
                    break;
                case 0x0818: 
                    appendFlagElement(parent,sprm,"specVanish",true);
                    break;
                case 0x0837: 
                    appendFlagElement(parent,sprm,"strike",true);
                    break;
                case 0x083C: 
                    appendFlagElement(parent,sprm,"vanish",true);
                    break;
                case 0x0811: 
                    appendFlagElement(parent,sprm,"webHidden",true);
                    break;
                case 0x2A48: 
                    SuperscriptIndex iss = (SuperscriptIndex)sprm.Arguments[0];
                    appendValueElement(parent,"vertAlign",iss.toString(),true);
                    break;
                case 0x486D: 
                case 0x4873: 
                    //language
                    //latin
                    LanguageId langid = new LanguageId(System.BitConverter.ToInt16(sprm.Arguments, 0));
                    langid.Convert(new LanguageIdMapping(lang,LanguageType.Default));
                    break;
                case 0x486E: 
                case 0x4874: 
                    //east asia
                    langid = new LanguageId(System.BitConverter.ToInt16(sprm.Arguments, 0));
                    langid.Convert(new LanguageIdMapping(lang,LanguageType.EastAsian));
                    break;
                case 0x485F: 
                    //bidi
                    langid = new LanguageId(System.BitConverter.ToInt16(sprm.Arguments, 0));
                    langid.Convert(new LanguageIdMapping(lang,LanguageType.Complex));
                    break;
                case 0x6865: 
                case 0xCA72: 
                    //borders
                    XmlNode bdr = _nodeFactory.createElement("w", "bdr", OpenXmlNamespaces.WordprocessingML);
                    appendBorderAttributes(new BorderCode(sprm.Arguments),bdr);
                    parent.appendChild(bdr);
                    break;
                case 0x4866: 
                case 0xCA71: 
                    //shading
                    ShadingDescriptor desc = new ShadingDescriptor(sprm.Arguments);
                    appendShading(parent,desc);
                    break;
                case 0x2A42: 
                case 0x4A60: 
                    //color
                    colorVal.setValue(((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier)(sprm.Arguments[0])).toString());
                    break;
                case 0x6870: 
                    //R
                    colorVal.setValue(String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),sprm.Arguments[0]));
                    //G
                    colorVal.setValue(colorVal.getValue() + String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),sprm.Arguments[1]));
                    //B
                    colorVal.setValue(colorVal.getValue() + String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),sprm.Arguments[2]));
                    break;
                case 0x2A0C: 
                    //highlightning
                    appendValueElement(parent,"highlight",((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.ColorIdentifier)sprm.Arguments[0]).toString(),true);
                    break;
                case 0x8840: 
                    //spacing
                    appendValueElement(parent,"spacing",System.BitConverter.ToInt16(sprm.Arguments, 0).toString(),true);
                    break;
                case 0x4A43: 
                    //font size
                    appendValueElement(parent,"sz",String.valueOf(sprm.Arguments[0]),true);
                    break;
                case 0x484B: 
                    appendValueElement(parent,"kern",System.BitConverter.ToInt16(sprm.Arguments, 0).toString(),true);
                    break;
                case 0x4A61: 
                    appendValueElement(parent,"szCs",System.BitConverter.ToInt16(sprm.Arguments, 0).toString(),true);
                    break;
                case 0x4A4F: 
                    //font family
                    XmlAttribute ascii = _nodeFactory.createAttribute("w","ascii",OpenXmlNamespaces.WordprocessingML);
                    FontFamilyName ffn = (FontFamilyName)_doc.FontTable.Data[System.BitConverter.ToUInt16(sprm.Arguments, 0)];
                    ascii.setValue(ffn.xszFtn);
                    rFonts.getAttributes().add(ascii);
                    break;
                case 0x4A50: 
                    XmlAttribute eastAsia = _nodeFactory.createAttribute("w","eastAsia",OpenXmlNamespaces.WordprocessingML);
                    FontFamilyName ffnAsia = (FontFamilyName)_doc.FontTable.Data[System.BitConverter.ToUInt16(sprm.Arguments, 0)];
                    eastAsia.setValue(ffnAsia.xszFtn);
                    rFonts.getAttributes().add(eastAsia);
                    break;
                case 0x4A51: 
                    XmlAttribute ansi = _nodeFactory.createAttribute("w","hAnsi",OpenXmlNamespaces.WordprocessingML);
                    FontFamilyName ffnAnsi = (FontFamilyName)_doc.FontTable.Data[System.BitConverter.ToUInt16(sprm.Arguments, 0)];
                    ansi.setValue(ffnAnsi.xszFtn);
                    rFonts.getAttributes().add(ansi);
                    break;
                case 0x2A3E: 
                    //Underlining
                    appendValueElement(parent,"u",lowerFirstChar(((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.UnderlineCode)sprm.Arguments[0]).toString()),true);
                    break;
                case 0x4852: 
                    //char width
                    appendValueElement(parent,"w",System.BitConverter.ToInt16(sprm.Arguments, 0).toString(),true);
                    break;
                case 0x2859: 
                    //animation
                    appendValueElement(parent,"effect",((DIaLOGIKa.b2xtranslator.DocFileFormat.Global.TextAnimation)sprm.Arguments[0]).toString(),true);
                    break;
                default: 
                    break;
            
            }
        }
        //apend lang
        if (lang.getAttributes().size() > 0)
        {
            parent.appendChild(lang);
        }
         
        //append fonts
        if (rFonts.getAttributes().size() > 0)
        {
            parent.appendChild(rFonts);
        }
         
        //append color
        if (!StringSupport.equals(colorVal.getValue(), ""))
        {
            color.getAttributes().add(colorVal);
            parent.appendChild(color);
        }
         
    }

    /**
    * CHPX flags are special flags because the can be 0,1,128 and 129,
    * so this method overrides the appendFlagElement method.
    */
    protected void appendFlagElement(XmlElement node, SinglePropertyModifier sprm, String elementName, boolean unique) throws Exception {
        byte flag = sprm.Arguments[0];
        if (flag != 128)
        {
            XmlElement ele = _nodeFactory.createElement("w", elementName, OpenXmlNamespaces.WordprocessingML);
            XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
            if (unique)
            {
                for (Object __dummyForeachVar1 : node.getChildNodes())
                {
                    XmlElement exEle = (XmlElement)__dummyForeachVar1;
                    if (StringSupport.equals(exEle.getName(), ele.getName()))
                    {
                        node.removeChild(exEle);
                        break;
                    }
                     
                }
            }
             
            if (flag == 0)
            {
                val.setValue("false");
                ele.getAttributes().add(val);
                node.appendChild(ele);
            }
            else if (flag == 1)
            {
                //dont append attribute val
                //no attribute means true
                node.appendChild(ele);
            }
            else if (flag == 129)
            {
                //Invert the value of the style
                //determine the style id of the current style
                UInt16 styleId = 0;
                if (_currentIstd != UInt16.MaxValue)
                {
                    styleId = _currentIstd;
                }
                else if (_currentPapx != null)
                {
                    styleId = _currentPapx.istd;
                }
                  
                //this chpx is the chpx of a style,
                //don't use the id of the chpx or the papx, use the baseOn style
                if (_styleChpx)
                {
                    StyleSheetDescription thisStyle = _doc.Styles.Styles[styleId];
                    styleId = (UInt16)thisStyle.istdBase;
                }
                 
                //build the style hierarchy
                if (_hierarchy == null)
                {
                    _hierarchy = buildHierarchy(_doc.Styles,styleId);
                }
                 
                //apply the toggle values to get the real value of the style
                boolean stylesVal = applyToggleHierachy(sprm);
                //invert it
                if (stylesVal)
                {
                    val.setValue("0");
                    ele.getAttributes().add(val);
                }
                 
                node.appendChild(ele);
            }
               
        }
         
    }

    private CSList<CharacterPropertyExceptions> buildHierarchy(StyleSheet styleSheet, UInt16 istdStart) throws Exception {
        CSList<CharacterPropertyExceptions> hierarchy = new CSList<CharacterPropertyExceptions>();
        int istd = (int)istdStart;
        boolean goOn = true;
        while (goOn)
        {
            try
            {
                CharacterPropertyExceptions baseChpx = styleSheet.Styles.get(istd).chpx;
                if (baseChpx != null)
                {
                    hierarchy.add(baseChpx);
                    istd = (int)styleSheet.Styles.get(istd).istdBase;
                }
                else
                {
                    goOn = false;
                } 
            }
            catch (Exception __dummyCatchVar0)
            {
                goOn = false;
            }
        
        }
        return hierarchy;
    }

    private boolean applyToggleHierachy(SinglePropertyModifier sprm) throws Exception {
        boolean ret = false;
        for (CharacterPropertyExceptions ancientChpx : _hierarchy)
        {
            for (SinglePropertyModifier ancientSprm : ancientChpx.grpprl)
            {
                if (ancientSprm.OpCode == sprm.OpCode)
                {
                    byte ancient = ancientSprm.Arguments[0];
                    ret = toogleValue(ret,ancient);
                    break;
                }
                 
            }
        }
        return ret;
    }

    private boolean toogleValue(boolean currentValue, byte toggle) throws Exception {
        if (toggle == 1)
            return true;
        else if (toggle == 129)
            //invert the current value
            if (currentValue)
                return false;
            else
                return true; 
        else if (toggle == 128)
            return currentValue;
        else
            return false;   
    }

    //use the current value
    private String lowerFirstChar(String s) throws Exception {
        return s.substring(0, (0) + (1)).toLowerCase() + s.substring(1, (1) + (s.length() - 1));
    }

}


