//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:29 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML.SpreadsheetDocument;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.SheetData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.XlsDocument;

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
* Includes some attributes and methods required by the mapping classes
*/
public class ExcelContext   
{
    private SpreadsheetDocument spreadDoc;
    private XmlWriterSettings writerSettings = new XmlWriterSettings();
    private XlsDocument xlsDoc;
    private SheetData currentSheet;
    /**
    * The settings of the XmlWriter which writes to the part
    */
    public XmlWriterSettings getWriterSettings() throws Exception {
        return writerSettings;
    }

    public void setWriterSettings(XmlWriterSettings value) throws Exception {
        writerSettings = value;
    }

    /**
    * The XlsDocument
    */
    public SpreadsheetDocument getSpreadDoc() throws Exception {
        return spreadDoc;
    }

    public void setSpreadDoc(SpreadsheetDocument value) throws Exception {
        this.spreadDoc = value;
    }

    /**
    * The XlsDocument
    */
    public XlsDocument getXlsDoc() throws Exception {
        return xlsDoc;
    }

    public void setXlsDoc(XlsDocument value) throws Exception {
        this.xlsDoc = value;
    }

    /**
    * Current working sheet !! !
    */
    public SheetData getCurrentSheet() throws Exception {
        return this.currentSheet;
    }

    public void setCurrentSheet(SheetData value) throws Exception {
        this.currentSheet = value;
    }

    /**
    * Ctor
    * 
    *  @param xlsDoc Xls document 
    *  @param writerSettings the xml writer settings
    */
    public ExcelContext(XlsDocument xlsDoc, XmlWriterSettings writerSettings) throws Exception {
        this.xlsDoc = xlsDoc;
        this.writerSettings = writerSettings;
    }

}


