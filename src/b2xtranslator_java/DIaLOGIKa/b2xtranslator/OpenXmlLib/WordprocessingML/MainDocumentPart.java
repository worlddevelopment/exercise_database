//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.ContentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.VbaProjectPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.CommentsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.EndnotesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FontTablePart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FooterPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.FootnotesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.GlossaryPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.HeaderPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.KeyMapCustomizationsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.NumberingDefinitionsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.SettingsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.StyleDefinitionsPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingMLContentTypes;

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
public class MainDocumentPart  extends ContentPart 
{
    protected StyleDefinitionsPart _styleDefinitionsPart;
    protected FontTablePart _fontTablePart;
    protected NumberingDefinitionsPart _numberingDefinitionsPart;
    protected SettingsPart _settingsPart;
    protected FootnotesPart _footnotesPart;
    protected EndnotesPart _endnotesPart;
    protected CommentsPart _commentsPart;
    protected VbaProjectPart _vbaProjectPart;
    protected GlossaryPart _glossaryPart;
    protected KeyMapCustomizationsPart _customizationsPart;
    protected int _headerPartCount = 0;
    protected int _footerPartCount = 0;
    private String _contentType = WordprocessingMLContentTypes.MainDocument;
    public MainDocumentPart(OpenXmlPartContainer parent, String contentType) throws Exception {
        super(parent);
        _contentType = contentType;
    }

    public String getContentType() throws Exception {
        return _contentType;
    }

    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.OfficeDocument;
    }

    public String getTargetName() throws Exception {
        return "document";
    }

    public String getTargetDirectory() throws Exception {
        return "word";
    }

    // unique parts
    public KeyMapCustomizationsPart getCustomizationsPart() throws Exception {
        if (_customizationsPart == null)
        {
            _customizationsPart = new KeyMapCustomizationsPart(this);
            this.AddPart(_customizationsPart);
        }
         
        return _customizationsPart;
    }

    public GlossaryPart getGlossaryPart() throws Exception {
        if (_glossaryPart == null)
        {
            _glossaryPart = new GlossaryPart(this, WordprocessingMLContentTypes.Glossary);
            this.AddPart(_glossaryPart);
        }
         
        return _glossaryPart;
    }

    public StyleDefinitionsPart getStyleDefinitionsPart() throws Exception {
        if (_styleDefinitionsPart == null)
        {
            _styleDefinitionsPart = new StyleDefinitionsPart(this);
            this.AddPart(_styleDefinitionsPart);
        }
         
        return _styleDefinitionsPart;
    }

    public SettingsPart getSettingsPart() throws Exception {
        if (_settingsPart == null)
        {
            _settingsPart = new SettingsPart(this);
            this.AddPart(_settingsPart);
        }
         
        return _settingsPart;
    }

    public NumberingDefinitionsPart getNumberingDefinitionsPart() throws Exception {
        if (_numberingDefinitionsPart == null)
        {
            _numberingDefinitionsPart = new NumberingDefinitionsPart(this);
            this.AddPart(_numberingDefinitionsPart);
        }
         
        return _numberingDefinitionsPart;
    }

    public FontTablePart getFontTablePart() throws Exception {
        if (_fontTablePart == null)
        {
            _fontTablePart = new FontTablePart(this);
            this.AddPart(_fontTablePart);
        }
         
        return _fontTablePart;
    }

    public EndnotesPart getEndnotesPart() throws Exception {
        if (_endnotesPart == null)
        {
            _endnotesPart = new EndnotesPart(this);
            this.AddPart(_endnotesPart);
        }
         
        return _endnotesPart;
    }

    public FootnotesPart getFootnotesPart() throws Exception {
        if (_footnotesPart == null)
        {
            _footnotesPart = new FootnotesPart(this);
            this.AddPart(_footnotesPart);
        }
         
        return _footnotesPart;
    }

    public CommentsPart getCommentsPart() throws Exception {
        if (_commentsPart == null)
        {
            _commentsPart = new CommentsPart(this);
            this.AddPart(_commentsPart);
        }
         
        return _commentsPart;
    }

    public VbaProjectPart getVbaProjectPart() throws Exception {
        if (_vbaProjectPart == null)
        {
            _vbaProjectPart = this.AddPart(new VbaProjectPart(this));
        }
         
        return _vbaProjectPart;
    }

    // non unique parts
    public HeaderPart addHeaderPart() throws Exception {
        return this.AddPart(new HeaderPart(this, ++_headerPartCount));
    }

    public FooterPart addFooterPart() throws Exception {
        return this.AddPart(new FooterPart(this,++_footerPartCount));
    }

}


