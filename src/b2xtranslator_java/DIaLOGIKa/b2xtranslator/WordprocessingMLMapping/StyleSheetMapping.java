//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:19 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FontFamilyName;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheet;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheetDescription;
import DIaLOGIKa.b2xtranslator.DocFileFormat.StyleSheetDescription.StyleIdentifier;
import DIaLOGIKa.b2xtranslator.DocFileFormat.WordDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.CharacterPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ParagraphPropertiesMapping;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.RevisionData.RevisionType;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.TablePropertiesMapping;

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
public class StyleSheetMapping  extends AbstractOpenXmlMapping implements IMapping<StyleSheet>
{
    private ConversionContext _ctx;
    private WordDocument _parentDoc;
    public StyleSheetMapping(ConversionContext ctx, WordDocument parentDoc, OpenXmlPart targetPart) throws Exception {
        super(XmlWriter.Create(targetPart.getStream(), ctx.getWriterSettings()));
        _ctx = ctx;
        _parentDoc = parentDoc;
    }

    public void apply(StyleSheet sheet) throws Exception {
        _writer.WriteStartDocument();
        _writer.WriteStartElement("w", "styles", OpenXmlNamespaces.WordprocessingML);
        //document defaults
        _writer.WriteStartElement("w", "docDefaults", OpenXmlNamespaces.WordprocessingML);
        writeRunDefaults(sheet);
        writeParagraphDefaults(sheet);
        _writer.writeEndElement();
        //write the default styles
        if (sheet.Styles.get(11) == null)
        {
            //NormalTable
            writeNormalTableStyle();
        }
         
        for (StyleSheetDescription style : sheet.Styles)
        {
            if (style != null)
            {
                _writer.WriteStartElement("w", "style", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, style.stk.toString());
                if (style.sti != StyleIdentifier.Null && style.sti != StyleIdentifier.User)
                {
                    //it's a default style
                    _writer.WriteAttributeString("w", "default", OpenXmlNamespaces.WordprocessingML, "1");
                }
                 
                _writer.WriteAttributeString("w", "styleId", OpenXmlNamespaces.WordprocessingML, makeStyleId(style));
                // <w:name val="" />
                _writer.WriteStartElement("w", "name", OpenXmlNamespaces.WordprocessingML);
                _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, getStyleName(style));
                _writer.writeEndElement();
                // <w:basedOn val="" />
                if (style.istdBase != 4095 && style.istdBase < sheet.Styles.size())
                {
                    _writer.WriteStartElement("w", "basedOn", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, makeStyleId(sheet.Styles.get((int)style.istdBase)));
                    _writer.writeEndElement();
                }
                 
                // <w:next val="" />
                if (style.istdNext < sheet.Styles.size())
                {
                    _writer.WriteStartElement("w", "next", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, makeStyleId(sheet.Styles.get((int)style.istdNext)));
                    _writer.writeEndElement();
                }
                 
                // <w:link val="" />
                if (style.istdLink < sheet.Styles.size())
                {
                    _writer.WriteStartElement("w", "link", OpenXmlNamespaces.WordprocessingML);
                    _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, makeStyleId(sheet.Styles.get((int)style.istdLink)));
                    _writer.writeEndElement();
                }
                 
                // <w:locked/>
                if (style.fLocked)
                {
                    _writer.WriteElementString("w", "locked", OpenXmlNamespaces.WordprocessingML, null);
                }
                 
                // <w:hidden/>
                if (style.fHidden)
                {
                    _writer.WriteElementString("w", "hidden", OpenXmlNamespaces.WordprocessingML, null);
                }
                 
                // <w:semiHidden/>
                if (style.fSemiHidden)
                {
                    _writer.WriteElementString("w", "semiHidden", OpenXmlNamespaces.WordprocessingML, null);
                }
                 
                //write paragraph properties
                if (style.papx != null)
                {
                    style.papx.Convert(new ParagraphPropertiesMapping(_writer,_ctx,_parentDoc,null));
                }
                 
                //write character properties
                if (style.chpx != null)
                {
                    RevisionData rev = new RevisionData();
                    rev.Type = RevisionType.NoRevision;
                    style.chpx.Convert(new CharacterPropertiesMapping(_writer,_parentDoc,rev,style.papx,true));
                }
                 
                //write table properties
                if (style.tapx != null)
                {
                    style.tapx.Convert(new TablePropertiesMapping(_writer,sheet,new CSList<Short>()));
                }
                 
                _writer.writeEndElement();
            }
             
        }
        _writer.writeEndElement();
        _writer.WriteEndDocument();
        _writer.Flush();
    }

    private void writeRunDefaults(StyleSheet sheet) throws Exception {
        _writer.WriteStartElement("w", "rPrDefault", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "rPr", OpenXmlNamespaces.WordprocessingML);
        //write default fonts
        _writer.WriteStartElement("w", "rFonts", OpenXmlNamespaces.WordprocessingML);
        FontFamilyName ffnAscii = (FontFamilyName)_ctx.getDoc().FontTable.Data[sheet.stshi.rgftcStandardChpStsh[0]];
        _writer.WriteAttributeString("w", "ascii", OpenXmlNamespaces.WordprocessingML, ffnAscii.xszFtn);
        FontFamilyName ffnAsia = (FontFamilyName)_ctx.getDoc().FontTable.Data[sheet.stshi.rgftcStandardChpStsh[1]];
        _writer.WriteAttributeString("w", "eastAsia", OpenXmlNamespaces.WordprocessingML, ffnAsia.xszFtn);
        FontFamilyName ffnAnsi = (FontFamilyName)_ctx.getDoc().FontTable.Data[sheet.stshi.rgftcStandardChpStsh[2]];
        _writer.WriteAttributeString("w", "hAnsi", OpenXmlNamespaces.WordprocessingML, ffnAsia.xszFtn);
        FontFamilyName ffnComplex = (FontFamilyName)_ctx.getDoc().FontTable.Data[sheet.stshi.rgftcStandardChpStsh[3]];
        _writer.WriteAttributeString("w", "cs", OpenXmlNamespaces.WordprocessingML, ffnComplex.xszFtn);
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

    private void writeParagraphDefaults(StyleSheet sheet) throws Exception {
        //if there is no pPrDefault, Word will not used the default paragraph settings.
        //writing an empty pPrDefault will cause Word to load the default paragraph settings.
        _writer.WriteStartElement("w", "pPrDefault", OpenXmlNamespaces.WordprocessingML);
        _writer.writeEndElement();
    }

    /**
    * Generates a style id for custom style names or returns the build-in identifier for build-in styles.
    * 
    *  @param std The StyleSheetDescription
    *  @return
    */
    public static String makeStyleId(StyleSheetDescription std) throws Exception {
        if (std.sti != StyleIdentifier.User && std.sti != StyleIdentifier.Null)
        {
            return std.sti.toString();
        }
        else
        {
            //use the identifier
            //if no identifier is set, use the name
            String ret = std.xstzName;
            ret = ret.replace(" ", "");
            ret = ret.replace("(", "");
            ret = ret.replace(")", "");
            ret = ret.replace("'", "");
            ret = ret.replace("\"", "");
            return ret;
        } 
    }

    /**
    * Chooses the correct style name.
    * Word 2007 needs the identifier instead of the stylename for translating it into the UI language.
    * 
    *  @param std The StyleSheetDescription
    *  @return
    */
    private String getStyleName(StyleSheetDescription std) throws Exception {
        if (std.sti != StyleIdentifier.User && std.sti != StyleIdentifier.Null)
        {
            return std.sti.toString();
        }
        else
        {
            return std.xstzName;
        } 
    }

    //use the identifier
    //if no identifier is set, use the name
    /**
    * Writes the "NormalTable" default style
    */
    private void writeNormalTableStyle() throws Exception {
        _writer.WriteStartElement("w", "style", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "table");
        _writer.WriteAttributeString("w", "default", OpenXmlNamespaces.WordprocessingML, "1");
        _writer.WriteAttributeString("w", "styleId", OpenXmlNamespaces.WordprocessingML, "TableNormal");
        _writer.WriteStartElement("w", "name", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "Normal Table");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "uiPriority", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, "99");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "semiHidden", OpenXmlNamespaces.WordprocessingML);
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "unhideWhenUsed", OpenXmlNamespaces.WordprocessingML);
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "qFormat", OpenXmlNamespaces.WordprocessingML);
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "tblPr", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "tblInd", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "w", OpenXmlNamespaces.WordprocessingML, "0");
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "dxa");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "tblCellMar", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteStartElement("w", "top", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "w", OpenXmlNamespaces.WordprocessingML, "0");
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "dxa");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "left", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "w", OpenXmlNamespaces.WordprocessingML, "108");
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "dxa");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "bottom", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "w", OpenXmlNamespaces.WordprocessingML, "0");
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "dxa");
        _writer.writeEndElement();
        _writer.WriteStartElement("w", "right", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "w", OpenXmlNamespaces.WordprocessingML, "108");
        _writer.WriteAttributeString("w", "type", OpenXmlNamespaces.WordprocessingML, "dxa");
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

}


