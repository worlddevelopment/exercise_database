//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationMLContentTypes;

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
public class PresentationDocument  extends OpenXmlPackage 
{
    protected PresentationPart _presentationPart;
    protected DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType _documentType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
    protected PresentationDocument(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        super(fileName);
        switch(type)
        {
            case Document: 
                this._presentationPart = new PresentationPart(this, PresentationMLContentTypes.Presentation);
                break;
            case MacroEnabledDocument: 
                this._presentationPart = new PresentationPart(this, PresentationMLContentTypes.PresentationMacro);
                break;
            case Template: 
                break;
            case MacroEnabledTemplate: 
                break;
        
        }
        this.AddPart(_presentationPart);
    }

    public static PresentationDocument create(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        PresentationDocument presentation = new PresentationDocument(fileName, type);
        return presentation;
    }

    public PresentationPart getPresentationPart() throws Exception {
        return _presentationPart;
    }

}


