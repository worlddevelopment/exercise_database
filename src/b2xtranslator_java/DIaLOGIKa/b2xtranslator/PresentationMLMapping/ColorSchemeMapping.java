//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:02 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlDocument;
import CS2JNet.System.Xml.XmlNodeList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ColorSchemeAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
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
public class ColorSchemeMapping  extends AbstractOpenXmlMapping 
{
    protected ConversionContext _ctx;
    public ColorSchemeMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    public void apply(CSList<ColorSchemeAtom> schemes) throws Exception {
        _writer.WriteStartElement("a", "theme", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("name", "dummyTheme");
        _writer.WriteStartElement("a", "themeElements", OpenXmlNamespaces.DrawingML);
        ColorSchemeAtom s = schemes.get(schemes.size() - 1);
        writeScheme(s);
        //write fontScheme
        writeFontScheme();
        //write fmtScheme
        writeFmtScheme();
        _writer.writeEndElement();
        // themeElements
        if (schemes.size() > 1)
        {
            _writer.WriteStartElement("a", "extraClrSchemeLst", OpenXmlNamespaces.DrawingML);
            for (ColorSchemeAtom scheme : schemes)
            {
                _writer.WriteStartElement("a", "extraClrScheme", OpenXmlNamespaces.DrawingML);
                writeScheme(scheme);
                _writer.writeEndElement();
            }
            // extraClrScheme
            _writer.writeEndElement();
        }
         
        // extraClrSchemeLst
        _writer.writeEndElement();
    }

    // theme
    private void writeFmtScheme() throws Exception {
        //TODO: real implementation instead of default
        XmlDocument xmlDoc = Utils.getDefaultDocument("theme");
        XmlNodeList l = xmlDoc.GetElementsByTagName("fmtScheme", OpenXmlNamespaces.DrawingML);
        l.get(0).WriteTo(_writer);
    }

    //_writer.WriteStartElement("a", "fmtScheme", OpenXmlNamespaces.DrawingML);
    //_writer.WriteAttributeString("name", "dummyFmtScheme");
    /**
    * /fillStyleList
    * /lnStyleLst
    * /effectStyleLst
    * /bgFillStyleLst
    */
    //_writer.WriteEndElement(); // fmtScheme
    private void writeFontScheme() throws Exception {
        //TODO: real implementation instead of default
        _writer.WriteStartElement("a", "fontScheme", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("name", "dummyFontScheme");
        _writer.WriteStartElement("a", "majorFont", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "Arial Black");
        _writer.writeEndElement();
        // latin
        _writer.WriteStartElement("a", "ea", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "");
        _writer.writeEndElement();
        // ea
        _writer.WriteStartElement("a", "cs", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "");
        _writer.writeEndElement();
        // cs
        _writer.writeEndElement();
        // majorFont
        _writer.WriteStartElement("a", "minorFont", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "latin", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "Arial");
        _writer.writeEndElement();
        // latin
        _writer.WriteStartElement("a", "ea", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "");
        _writer.writeEndElement();
        // ea
        _writer.WriteStartElement("a", "cs", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("typeface", "");
        _writer.writeEndElement();
        // cs
        _writer.writeEndElement();
        // minorFont
        _writer.writeEndElement();
    }

    // fontScheme
    private void writeScheme(ColorSchemeAtom scheme) throws Exception {
        //TODO: verify the mappings; at leat accent4 - 6 are wrong
        _writer.WriteStartElement("a", "clrScheme", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("name", "dummyScheme");
        _writer.WriteStartElement("a", "dk1", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.TextAndLines,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // dk1
        _writer.WriteStartElement("a", "lt1", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Background,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // lt1
        _writer.WriteStartElement("a", "dk2", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.TitleText,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // dk2
        _writer.WriteStartElement("a", "lt2", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Shadows,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // lt2
        _writer.WriteStartElement("a", "accent1", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Fills,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent1
        _writer.WriteStartElement("a", "accent2", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent2
        _writer.WriteStartElement("a", "accent3", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Background,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent3
        _writer.WriteStartElement("a", "accent4", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent4
        _writer.WriteStartElement("a", "accent5", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent5
        _writer.WriteStartElement("a", "accent6", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.Accent,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // accent6
        _writer.WriteStartElement("a", "hlink", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.AccentAndHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // hlink
        _writer.WriteStartElement("a", "folHlink", OpenXmlNamespaces.DrawingML);
        _writer.WriteStartElement("a", "srgbClr", OpenXmlNamespaces.DrawingML);
        _writer.writeAttributeString("val", new RGBColor(scheme.AccentAndFollowedHyperlink,ByteOrder.RedFirst).SixDigitHexCode);
        _writer.writeEndElement();
        // srgbClr
        _writer.writeEndElement();
        // folHlink
        _writer.writeEndElement();
    }

}


// clrScheme