//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:02 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.NumberSupport;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CharacterRun;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorSchemeAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontCollection;
import DIaLOGIKa.b2xtranslator.PptFileFormat.FontEntityAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.StyleMask;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextMasterStyleAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
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
public class CharacterRunPropsMapping  extends AbstractOpenXmlMapping 
{
    protected ConversionContext _ctx;
    public CharacterRunPropsMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    public void apply(CharacterRun run, String startElement, RegularContainer slide, RefSupport<String> lastColor, RefSupport<String> lastSize, RefSupport<String> lastTypeface, String lang, TextMasterStyleAtom defaultStyle, int lvl) throws Exception {
        _writer.WriteStartElement("a", startElement, OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("lang", lang);
        boolean runExists = run != null;
        if (runExists && run.getSizePresent())
        {
            if (run.Size > 0)
            {
                _writer.writeAttributeString("sz", (run.Size * 100).toString());
                lastSize.setValue((run.Size * 100).toString());
            }
             
        }
        else if (lastSize.getValue().length() > 0)
        {
            _writer.writeAttributeString("sz", lastSize.getValue());
        }
        else if (defaultStyle != null)
        {
            if (defaultStyle.CRuns.get(lvl).getSizePresent())
            {
                _writer.writeAttributeString("sz", (defaultStyle.CRuns.get(lvl).Size * 100).toString());
            }
             
        }
           
        if (runExists && run.getStyleFlagsFieldPresent())
        {
            if ((run.Style & StyleMask.IsBold) == StyleMask.IsBold)
                _writer.writeAttributeString("b", "1");
             
            if ((run.Style & StyleMask.IsItalic) == StyleMask.IsItalic)
                _writer.writeAttributeString("i", "1");
             
            if ((run.Style & StyleMask.IsUnderlined) == StyleMask.IsUnderlined)
                _writer.writeAttributeString("u", "sng");
             
        }
        else if (defaultStyle != null && defaultStyle.CRuns.get(lvl).getStyleFlagsFieldPresent())
        {
            if ((defaultStyle.CRuns.get(lvl).Style & StyleMask.IsBold) == StyleMask.IsBold)
                _writer.writeAttributeString("b", "1");
             
            if ((defaultStyle.CRuns.get(lvl).Style & StyleMask.IsItalic) == StyleMask.IsItalic)
                _writer.writeAttributeString("i", "1");
             
            if ((defaultStyle.CRuns.get(lvl).Style & StyleMask.IsUnderlined) == StyleMask.IsUnderlined)
                _writer.writeAttributeString("u", "sng");
             
        }
          
        if (runExists && run.getColorPresent())
        {
            RefSupport<String> refVar___0 = new RefSupport<String>(lastColor.getValue());
            writeSolidFill(slide,run,refVar___0);
            lastColor.setValue(refVar___0.getValue());
        }
        else if (lastColor.getValue().length() > 0)
        {
            _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("val", lastColor.getValue());
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        else if (defaultStyle != null)
        {
            if (defaultStyle.CRuns.get(lvl).getColorPresent())
            {
                RefSupport<String> refVar___1 = new RefSupport<String>(lastColor.getValue());
                writeSolidFill((RegularContainer)defaultStyle.getParentRecord(),defaultStyle.CRuns.get(lvl),refVar___1);
                lastColor.setValue(refVar___1.getValue());
            }
             
        }
           
        if (runExists && run.getStyleFlagsFieldPresent())
        {
            if ((run.Style & StyleMask.HasShadow) == StyleMask.HasShadow)
            {
                //TODO: these values are default and have to be replaced
                _writer.WriteStartElement("a", "effectLst", OpenXmlNamespaces.DrawingML);
                _writer.WriteStartElement("a", "outerShdw", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("blurRad", "38100");
                _writer.writeAttributeString("dist", "38100");
                _writer.writeAttributeString("dir", "2700000");
                _writer.writeAttributeString("algn", "tl");
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", "C0C0C0");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
            if ((run.Style & StyleMask.IsEmbossed) == StyleMask.IsEmbossed)
            {
                //TODO: these values are default and have to be replaced
                _writer.WriteStartElement("a", "effectDag", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("name", "");
                _writer.WriteStartElement("a", "cont", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("type", "tree");
                _writer.writeAttributeString("name", "");
                _writer.WriteStartElement("a", "effect", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("ref", "fillLine");
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "outerShdw", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("dist", "38100");
                _writer.writeAttributeString("dir", "13500000");
                _writer.writeAttributeString("algn", "br");
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", "FFFFFF");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "cont", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("type", "tree");
                _writer.writeAttributeString("name", "");
                _writer.WriteStartElement("a", "effect", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("ref", "fillLine");
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "outerShdw", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("dist", "38100");
                _writer.writeAttributeString("dir", "2700000");
                _writer.writeAttributeString("algn", "tl");
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("val", "999999");
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.writeEndElement();
                _writer.WriteStartElement("a", "effect", OpenXmlNamespaces.DrawingML);
                _writer.writeAttributeString("ref", "fillLine");
                _writer.writeEndElement();
                _writer.writeEndElement();
            }
             
        }
         
        //TODOS
        //HasAsianSmartQuotes
        //HasHorizonNumRendering
        //ExtensionNibble
        //TODOs:
        //run.ANSITypefacePresent
        //run.FEOldTypefacePresent
        //run.PositionPresent
        //run.SymbolTypefacePresent
        //run.TypefacePresent
        if (runExists && run.getTypefacePresent())
        {
            //List<ColorSchemeAtom> colors = slide.AllChildrenWithType<ColorSchemeAtom>();
            //ColorSchemeAtom MasterScheme = null;
            //foreach (ColorSchemeAtom color in colors)
            //{
            //    if (color.Instance == 1) MasterScheme = color;
            //}
            _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
            try
            {
                FontCollection fonts = _ctx.getPpt().DocumentRecord.firstChildWithType().<FontCollection>FirstChildWithType();
                FontEntityAtom entity = fonts.entities.get((int)run.TypefaceIdx);
                if (entity.TypeFace.indexOf('\0') > 0)
                {
                    _writer.writeAttributeString("typeface", entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                    lastTypeface.setValue(entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                }
                else
                {
                    _writer.writeAttributeString("typeface", entity.TypeFace);
                    lastTypeface.setValue(entity.TypeFace);
                } 
            }
            catch (Exception ex)
            {
                throw ex;
            }

            //_writer.WriteAttributeString("charset", "0");
            _writer.writeEndElement();
        }
        else if (lastTypeface.getValue().length() > 0)
        {
            _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
            _writer.writeAttributeString("typeface", lastTypeface.getValue());
            _writer.writeEndElement();
        }
        else if (defaultStyle != null && defaultStyle.CRuns.get(lvl).getTypefacePresent())
        {
            _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
            try
            {
                FontCollection fonts = _ctx.getPpt().DocumentRecord.firstChildWithType().<FontCollection>FirstChildWithType();
                FontEntityAtom entity = fonts.entities.get((int)defaultStyle.CRuns.get(lvl).TypefaceIdx);
                if (entity.TypeFace.indexOf('\0') > 0)
                {
                    _writer.writeAttributeString("typeface", entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                    lastTypeface.setValue(entity.TypeFace.substring(0, (0) + (entity.TypeFace.indexOf('\0'))));
                }
                else
                {
                    _writer.writeAttributeString("typeface", entity.TypeFace);
                    lastTypeface.setValue(entity.TypeFace);
                } 
            }
            catch (Exception ex)
            {
                throw ex;
            }

            //_writer.WriteAttributeString("charset", "0");
            _writer.writeEndElement();
        }
           
        _writer.writeEndElement();
    }

    public void writeSolidFill(RegularContainer slide, CharacterRun run, RefSupport<String> lastColor) throws Exception {
        _writer.WriteStartElement("a", "solidFill", OpenXmlNamespaces.DrawingML);
        if (run.Color.getIsSchemeColor())
        {
            //TODO: to be fully implemented
            //_writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
            if (slide == null)
            {
                /**
                * /TODO: what shall be used in this case (happens for default text style in presentation.xml)
                */
                //_writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                //_writer.WriteAttributeString("val", "000000");
                //_writer.WriteEndElement();
                _writer.WriteStartElement("a", "schemeClr", OpenXmlNamespaces.DrawingML);
                byte __dummyScrutVar0 = run.Color.Index;
                if (__dummyScrutVar0.equals(0x00))
                {
                    _writer.writeAttributeString("val", "bg1");
                }
                else //background
                if (__dummyScrutVar0.equals(0x01))
                {
                    _writer.writeAttributeString("val", "tx1");
                }
                else //text
                if (__dummyScrutVar0.equals(0x02))
                {
                    _writer.writeAttributeString("val", "dk1");
                }
                else //shadow
                if (__dummyScrutVar0.equals(0x03))
                {
                    _writer.writeAttributeString("val", "tx1");
                }
                else //title text
                if (__dummyScrutVar0.equals(0x04))
                {
                    _writer.writeAttributeString("val", "bg2");
                }
                else //fill
                if (__dummyScrutVar0.equals(0x05))
                {
                    _writer.writeAttributeString("val", "accent1");
                }
                else //accent1
                if (__dummyScrutVar0.equals(0x06))
                {
                    _writer.writeAttributeString("val", "accent2");
                }
                else //accent2
                if (__dummyScrutVar0.equals(0x07))
                {
                    _writer.writeAttributeString("val", "accent3");
                }
                else //accent3
                if (__dummyScrutVar0.equals(0xFE))
                {
                    //sRGB
                    lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(run.Color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Blue, "X"), 2, '0'));
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar0.equals(0xFF))
                {
                }
                          
                //undefined
                _writer.writeEndElement();
            }
            else
            {
                ColorSchemeAtom MasterScheme = null;
                SlideAtom ato = slide.firstChildWithType();
                CSList<ColorSchemeAtom> colors;
                if (ato != null && DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(ato.Flags, 0x1 << 1) && ato.MasterId != 0)
                {
                    colors = _ctx.getPpt().findMasterRecordById(ato.MasterId).allChildrenWithType();
                }
                else
                {
                    colors = slide.allChildrenWithType();
                } 
                for (ColorSchemeAtom color : colors)
                {
                    if (color.Instance == 1)
                        MasterScheme = color;
                     
                }
                _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
                byte __dummyScrutVar1 = run.Color.Index;
                if (__dummyScrutVar1.equals(0x00))
                {
                    //background
                    lastColor.setValue(new RGBColor(MasterScheme.Background,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x01))
                {
                    //text
                    lastColor.setValue(new RGBColor(MasterScheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x02))
                {
                    //shadow
                    lastColor.setValue(new RGBColor(MasterScheme.Shadows,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x03))
                {
                    //title
                    lastColor.setValue(new RGBColor(MasterScheme.TitleText,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x04))
                {
                    //fill
                    lastColor.setValue(new RGBColor(MasterScheme.Fills,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x05))
                {
                    //accent1
                    lastColor.setValue(new RGBColor(MasterScheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x06))
                {
                    //accent2
                    lastColor.setValue(new RGBColor(MasterScheme.AccentAndHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0x07))
                {
                    //accent3
                    lastColor.setValue(new RGBColor(MasterScheme.AccentAndFollowedHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0xFE))
                {
                    //sRGB
                    lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(run.Color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Blue, "X"), 2, '0'));
                    _writer.writeAttributeString("val", lastColor.getValue());
                }
                else if (__dummyScrutVar1.equals(0xFF))
                {
                }
                          
                //undefined
                _writer.writeEndElement();
            } 
        }
        else
        {
            //_writer.WriteEndElement();
            _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
            lastColor.setValue(StringSupport.PadLeft(NumberSupport.format(run.Color.Red, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Green, "X"), 2, '0') + StringSupport.PadLeft(NumberSupport.format(run.Color.Blue, "X"), 2, '0'));
            _writer.writeAttributeString("val", lastColor.getValue());
            _writer.writeEndElement();
        } 
        _writer.writeEndElement();
    }

}


