//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:13 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FontFamilyName;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StringTable;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;

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
public class FontTableMapping  extends AbstractOpenXmlMapping implements IMapping<StringTable>
{
    protected public enum FontFamily
    {
        auto,
        decorative,
        modern,
        roman,
        script,
        swiss
    }
    public FontTableMapping(ConversionContext ctx, OpenXmlPart targetPart) throws Exception {
        super(XmlWriter.Create(targetPart.getStream(), ctx.getWriterSettings()));
    }

    public void apply(StringTable table) throws Exception {
        _writer.WriteStartElement("w", "fonts", OpenXmlNamespaces.WordprocessingML);
        for (Object __dummyForeachVar1 : table.Data)
        {
            FontFamilyName font = (FontFamilyName)__dummyForeachVar1;
            _writer.WriteStartElement("w", "font", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "name", OpenXmlNamespaces.WordprocessingML, font.xszFtn);
            //alternative name
            if (font.xszAlt != null && font.xszAlt.length() > 0)
            {
                _writer.WriteStartElement("w", "altName", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, font.xszAlt);
                _writer.writeEndElement();
            }
             
            //charset
            _writer.WriteStartElement("w", "charset", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),font.chs));
            _writer.writeEndElement();
            //font family
            _writer.WriteStartElement("w", "family", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, ((FontFamily)font.ff).toString());
            _writer.writeEndElement();
            //panose
            _writer.WriteStartElement("w", "panose1", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteStartAttribute("w", "val", OpenXmlNamespaces.WordprocessingML);
            for (byte b : font.panose)
            {
                _writer.writeString(String.format(StringSupport.CSFmtStrToJFmtStr("{0:x2}"),b));
            }
            _writer.WriteEndAttribute();
            _writer.writeEndElement();
            //pitch
            _writer.WriteStartElement("w", "pitch", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.valueOf(font.prq));
            _writer.writeEndElement();
            //truetype
            if (!font.fTrueType)
            {
                _writer.WriteStartElement("w", "notTrueType", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "true");
                _writer.writeEndElement();
            }
             
            //font signature
            _writer.WriteStartElement("w", "sig", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "usb0", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.UnicodeSubsetBitfield0));
            _writer.WriteAttributeString("w", "usb1", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.UnicodeSubsetBitfield1));
            _writer.WriteAttributeString("w", "usb2", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.UnicodeSubsetBitfield2));
            _writer.WriteAttributeString("w", "usb3", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.UnicodeSubsetBitfield3));
            _writer.WriteAttributeString("w", "csb0", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.CodePageBitfield0));
            _writer.WriteAttributeString("w", "csb1", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x8}"),font.fs.CodePageBitfield1));
            _writer.writeEndElement();
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
        _writer.Flush();
    }

}


