//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.CustomXmlPropertiesPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.MainDocumentPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.WordprocessingML.WordprocessingDocument;
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
public class WordprocessingDocument  extends OpenXmlPackage 
{
    protected DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType _documentType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
    protected CustomXmlPropertiesPart _customFilePropertiesPart;
    protected MainDocumentPart _mainDocumentPart;
    protected WordprocessingDocument(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        super(fileName);
        switch(type)
        {
            case Document: 
                _mainDocumentPart = new MainDocumentPart(this,WordprocessingMLContentTypes.MainDocument);
                break;
            case MacroEnabledDocument: 
                _mainDocumentPart = new MainDocumentPart(this,WordprocessingMLContentTypes.MainDocumentMacro);
                break;
            case Template: 
                _mainDocumentPart = new MainDocumentPart(this,WordprocessingMLContentTypes.MainDocumentTemplate);
                break;
            case MacroEnabledTemplate: 
                _mainDocumentPart = new MainDocumentPart(this,WordprocessingMLContentTypes.MainDocumentMacroTemplate);
                break;
        
        }
        _documentType = type;
        this.AddPart(_mainDocumentPart);
    }

    public static WordprocessingDocument create(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        WordprocessingDocument doc = new WordprocessingDocument(fileName,type);
        return doc;
    }

    public DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType getDocumentType() throws Exception {
        return _documentType;
    }

    public void setDocumentType(DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType value) throws Exception {
        _documentType = value;
    }

    public CustomXmlPropertiesPart getCustomFilePropertiesPart() throws Exception {
        return _customFilePropertiesPart;
    }

    public MainDocumentPart getMainDocumentPart() throws Exception {
        return _mainDocumentPart;
    }

}


