//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:17 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlNode;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.BorderCode;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor;
import DIaLOGIKa.b2xtranslator.DocFileFormat.ShadingDescriptor.ShadingPattern;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;

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
public class PropertiesMapping  extends AbstractOpenXmlMapping 
{
    public PropertiesMapping(XmlWriter writer) throws Exception {
        super(writer);
    }

    protected void appendFlagAttribute(XmlElement node, SinglePropertyModifier sprm, String attributeName) throws Exception {
        XmlAttribute att = _nodeFactory.createAttribute("w",attributeName,OpenXmlNamespaces.WordprocessingML);
        att.setValue(String.valueOf(sprm.Arguments[0]));
        node.getAttributes().add(att);
    }

    protected void appendFlagElement(XmlElement node, SinglePropertyModifier sprm, String elementName, boolean unique) throws Exception {
        XmlElement ele = _nodeFactory.createElement("w", elementName, OpenXmlNamespaces.WordprocessingML);
        if (sprm.Arguments[0] == 0)
        {
            XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
            val.setValue("false");
            ele.getAttributes().add(val);
        }
         
        if (unique)
        {
            for (Object __dummyForeachVar0 : node.getChildNodes())
            {
                XmlElement exEle = (XmlElement)__dummyForeachVar0;
                if (StringSupport.equals(exEle.getName(), ele.getName()))
                {
                    node.removeChild(exEle);
                    break;
                }
                 
            }
        }
         
        node.appendChild(ele);
    }

    protected void appendValueAttribute(XmlElement node, String attributeName, String attributeValue) throws Exception {
        appendValueAttribute(node,"w",attributeName,attributeValue,OpenXmlNamespaces.WordprocessingML);
    }

    protected void appendValueAttribute(XmlElement node, String prefix, String attributeName, String attributeValue, String ns) throws Exception {
        XmlAttribute att = _nodeFactory.createAttribute(prefix,attributeName,ns);
        att.setValue(attributeValue);
        node.getAttributes().add(att);
    }

    protected void appendValueElement(XmlElement node, String elementName, String elementValue, boolean unique) throws Exception {
        XmlElement ele = _nodeFactory.createElement("w", elementName, OpenXmlNamespaces.WordprocessingML);
        if (elementValue != null && !StringSupport.equals(elementValue, ""))
        {
            XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
            val.setValue(elementValue);
            ele.getAttributes().add(val);
        }
         
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
         
        node.appendChild(ele);
    }

    protected void appendDxaElement(XmlElement node, String elementName, String elementValue, boolean unique) throws Exception {
        XmlElement ele = _nodeFactory.createElement("w", elementName, OpenXmlNamespaces.WordprocessingML);
        XmlAttribute val = _nodeFactory.createAttribute("w","w",OpenXmlNamespaces.WordprocessingML);
        val.setValue(elementValue);
        ele.getAttributes().add(val);
        XmlAttribute type = _nodeFactory.createAttribute("w","type",OpenXmlNamespaces.WordprocessingML);
        type.setValue("dxa");
        ele.getAttributes().add(type);
        if (unique)
        {
            for (Object __dummyForeachVar2 : node.getChildNodes())
            {
                XmlElement exEle = (XmlElement)__dummyForeachVar2;
                if (StringSupport.equals(exEle.getName(), ele.getName()))
                {
                    node.removeChild(exEle);
                    break;
                }
                 
            }
        }
         
        node.appendChild(ele);
    }

    protected void addOrSetBorder(XmlNode pBdr, XmlNode border) throws Exception {
        for (Object __dummyForeachVar3 : pBdr.getChildNodes())
        {
            //remove old border if it exist
            XmlNode bdr = (XmlNode)__dummyForeachVar3;
            if (StringSupport.equals(bdr.getName(), border.getName()))
            {
                pBdr.removeChild(bdr);
                break;
            }
             
        }
        //add new
        pBdr.appendChild(border);
    }

    protected void appendBorderAttributes(BorderCode brc, XmlNode border) throws Exception {
        if (brc.fNil)
        {
            XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
            val.setValue("nil");
            border.getAttributes().add(val);
        }
        else
        {
            XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
            val.setValue(getBorderType(brc.brcType));
            border.getAttributes().add(val);
            XmlAttribute color = _nodeFactory.createAttribute("w","color",OpenXmlNamespaces.WordprocessingML);
            color.setValue(new RGBColor(brc.cv,ByteOrder.RedFirst).SixDigitHexCode);
            border.getAttributes().add(color);
            XmlAttribute space = _nodeFactory.createAttribute("w","space",OpenXmlNamespaces.WordprocessingML);
            space.setValue(String.valueOf(brc.dptSpace));
            border.getAttributes().add(space);
            XmlAttribute sz = _nodeFactory.createAttribute("w","sz",OpenXmlNamespaces.WordprocessingML);
            sz.setValue(String.valueOf(brc.dptLineWidth));
            border.getAttributes().add(sz);
            if (brc.fShadow)
            {
                XmlAttribute shadow = _nodeFactory.createAttribute("w","shadow",OpenXmlNamespaces.WordprocessingML);
                shadow.setValue("1");
                border.getAttributes().add(shadow);
            }
             
        } 
    }

    protected void appendShading(XmlElement parent, ShadingDescriptor desc) throws Exception {
        XmlElement shd = _nodeFactory.createElement("w", "shd", OpenXmlNamespaces.WordprocessingML);
        //fill color
        XmlAttribute fill = _nodeFactory.createAttribute("w","fill",OpenXmlNamespaces.WordprocessingML);
        if (desc.cvBack != 0)
            fill.setValue(new RGBColor((int)desc.cvBack,ByteOrder.RedLast).SixDigitHexCode);
        else
            fill.setValue(desc.icoBack.toString()); 
        shd.getAttributes().add(fill);
        //foreground color
        XmlAttribute color = _nodeFactory.createAttribute("w","color",OpenXmlNamespaces.WordprocessingML);
        if (desc.cvFore != 0)
            color.setValue(new RGBColor((int)desc.cvFore,ByteOrder.RedFirst).SixDigitHexCode);
        else
            color.setValue(desc.icoFore.toString()); 
        shd.getAttributes().add(color);
        //pattern
        XmlAttribute val = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
        val.setValue(getShadingPattern(desc));
        shd.getAttributes().add(val);
        parent.appendChild(shd);
    }

    protected String getBorderType(byte type) throws Exception {
        byte __dummyScrutVar0 = type;
        if (__dummyScrutVar0.equals(0))
        {
            return "none";
        }
        else if (__dummyScrutVar0.equals(1))
        {
            return "single";
        }
        else if (__dummyScrutVar0.equals(2))
        {
            return "thick";
        }
        else if (__dummyScrutVar0.equals(3))
        {
            return "double";
        }
        else if (__dummyScrutVar0.equals(4))
        {
            return "none";
        }
        else //unused
        if (__dummyScrutVar0.equals(5))
        {
            return "hairline";
        }
        else if (__dummyScrutVar0.equals(6))
        {
            return "dotted";
        }
        else if (__dummyScrutVar0.equals(7))
        {
            return "dashed";
        }
        else if (__dummyScrutVar0.equals(8))
        {
            return "dotDash";
        }
        else if (__dummyScrutVar0.equals(9))
        {
            return "dotDotDash";
        }
        else if (__dummyScrutVar0.equals(10))
        {
            return "triple";
        }
        else if (__dummyScrutVar0.equals(11))
        {
            return "thinThickSmallGap";
        }
        else if (__dummyScrutVar0.equals(12))
        {
            return "thickThinSmallGap";
        }
        else if (__dummyScrutVar0.equals(13))
        {
            return "thinThickThinSmallGap";
        }
        else if (__dummyScrutVar0.equals(14))
        {
            return "thinThickMediumGap";
        }
        else if (__dummyScrutVar0.equals(15))
        {
            return "thickThinMediumGap";
        }
        else if (__dummyScrutVar0.equals(16))
        {
            return "thinThickThinMediumGap";
        }
        else if (__dummyScrutVar0.equals(17))
        {
            return "thinThickLargeGap";
        }
        else if (__dummyScrutVar0.equals(18))
        {
            return "thickThinLargeGap";
        }
        else if (__dummyScrutVar0.equals(19))
        {
            return "thinThickThinLargeGap";
        }
        else if (__dummyScrutVar0.equals(20))
        {
            return "wave";
        }
        else if (__dummyScrutVar0.equals(21))
        {
            return "doubleWave";
        }
        else if (__dummyScrutVar0.equals(22))
        {
            return "dashSmallGap";
        }
        else if (__dummyScrutVar0.equals(23))
        {
            return "dashDotStroked";
        }
        else if (__dummyScrutVar0.equals(24))
        {
            return "threeDEmboss";
        }
        else if (__dummyScrutVar0.equals(25))
        {
            return "threeDEngrave";
        }
        else
        {
            return "none";
        }                          
    }

    private String getShadingPattern(ShadingDescriptor shd) throws Exception {
        String pattern = "";
        switch(shd.ipat)
        {
            case Automatic: 
                pattern = "auto";
                break;
            case Solid: 
                pattern = "solid";
                break;
            case Percent_5: 
                pattern = "pct5";
                break;
            case Percent_10: 
                pattern = "pct10";
                break;
            case Percent_20: 
                pattern = "pct20";
                break;
            case Percent_25: 
                pattern = "pct25";
                break;
            case Percent_30: 
                pattern = "pct30";
                break;
            case Percent_40: 
                pattern = "pct40";
                break;
            case Percent_50: 
                pattern = "pct50";
                break;
            case Percent_60: 
                pattern = "pct60";
                break;
            case Percent_70: 
                pattern = "pct70";
                break;
            case Percent_75: 
                pattern = "pct75";
                break;
            case Percent_80: 
                pattern = "pct80";
                break;
            case Percent_90: 
                pattern = "pct90";
                break;
            case DarkHorizontal: 
                pattern = "thinHorzStripe";
                break;
            case DarkVertical: 
                pattern = "thinVertStripe";
                break;
            case DarkForwardDiagonal: 
                break;
            case DarkBackwardDiagonal: 
                break;
            case DarkCross: 
                break;
            case DarkDiagonalCross: 
                pattern = "thinDiagCross";
                break;
            case Horizontal: 
                pattern = "horzStripe";
                break;
            case Vertical: 
                pattern = "vertStripe";
                break;
            case ForwardDiagonal: 
                break;
            case BackwardDiagonal: 
                break;
            case Cross: 
                break;
            case DiagonalCross: 
                pattern = "diagCross";
                break;
            case Percent_2_5: 
                pattern = "pct5";
                break;
            case Percent_7_5: 
                pattern = "pct10";
                break;
            case Percent_12_5: 
                pattern = "pct12";
                break;
            case Percent_15: 
                pattern = "pct15";
                break;
            case Percent_17_5: 
                pattern = "pct15";
                break;
            case Percent_22_5: 
                pattern = "pct20";
                break;
            case Percent_27_5: 
                pattern = "pct30";
                break;
            case Percent_32_5: 
                pattern = "pct35";
                break;
            case Percent_35: 
                pattern = "pct35";
                break;
            case Percent_37_5: 
                pattern = "pct37";
                break;
            case Percent_42_5: 
                pattern = "pct40";
                break;
            case Percent_45: 
                pattern = "pct45";
                break;
            case Percent_47_5: 
                pattern = "pct45";
                break;
            case Percent_52_5: 
                pattern = "pct50";
                break;
            case Percent_55: 
                pattern = "pct55";
                break;
            case Percent_57_5: 
                pattern = "pct55";
                break;
            case Percent_62_5: 
                pattern = "pct62";
                break;
            case Percent_65: 
                pattern = "pct65";
                break;
            case Percent_67_5: 
                pattern = "pct65";
                break;
            case Percent_72_5: 
                pattern = "pct70";
                break;
            case Percent_77_5: 
                pattern = "pct75";
                break;
            case Percent_82_5: 
                pattern = "pct80";
                break;
            case Percent_85: 
                pattern = "pct85";
                break;
            case Percent_87_5: 
                pattern = "pct87";
                break;
            case Percent_92_5: 
                pattern = "pct90";
                break;
            case Percent_95: 
                pattern = "pct95";
                break;
            case Percent_97_5: 
                pattern = "pct95";
                break;
            case Percent_97: 
                pattern = "pct95";
                break;
            default: 
                pattern = "nil";
                break;
        
        }
        return pattern;
    }

}


