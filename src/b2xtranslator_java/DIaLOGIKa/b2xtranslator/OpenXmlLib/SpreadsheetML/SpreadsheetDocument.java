//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:48 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.SpreadsheetDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.WorkbookPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetMLContentTypes;

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
/**
* Includes some information about the spreadsheetdocument
*/
public class SpreadsheetDocument  extends OpenXmlPackage 
{
    protected WorkbookPart workBookPart;
    protected DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType _documentType = DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType.Document;
    /**
    * Ctor
    * 
    *  @param fileName Filename of the file which should be written
    */
    protected SpreadsheetDocument(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        super(fileName);
        switch(type)
        {
            case Document: 
                this.workBookPart = new WorkbookPart(this,SpreadsheetMLContentTypes.Workbook);
                break;
            case MacroEnabledDocument: 
                this.workBookPart = new WorkbookPart(this,SpreadsheetMLContentTypes.WorkbookMacro);
                break;
        
        }
        //case OpenXmlPackage.DocumentType.Template:
        //    workBookPart = new WorkbookPart(this, WordprocessingMLContentTypes.MainDocumentTemplate);
        //    break;
        //case OpenXmlPackage.DocumentType.MacroEnabledTemplate:
        //    workBookPart = new WorkbookPart(this, WordprocessingMLContentTypes.MainDocumentMacroTemplate);
        //    break;
        _documentType = type;
        this.AddPart(this.workBookPart);
    }

    /**
    * creates a new excel document with the choosen filename
    * 
    *  @param fileName The name of the file which should be written
    *  @return The object itself
    */
    public static SpreadsheetDocument create(String fileName, DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType type) throws Exception {
        SpreadsheetDocument spreadsheet = new SpreadsheetDocument(fileName,type);
        return spreadsheet;
    }

    public DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType getDocumentType() throws Exception {
        return _documentType;
    }

    public void setDocumentType(DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPackage.DocumentType value) throws Exception {
        _documentType = value;
    }

    /**
    * returns the workbookPart from the new excel document
    */
    public WorkbookPart getWorkbookPart() throws Exception {
        return this.workBookPart;
    }

}


