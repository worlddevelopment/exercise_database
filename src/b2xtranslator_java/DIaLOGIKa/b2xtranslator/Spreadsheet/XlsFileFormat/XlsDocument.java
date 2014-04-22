//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:54 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.WorkBookData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExtractorException;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.WorkbookExtractor;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.XlsDocument;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStreamReader;

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
public class XlsDocument   implements IVisitable
{
    /**
    * Some constant strings
    */
    private static final String WORKBOOK = "Workbook";
    private static final String ALTERNATE1 = "Book";
    /**
    * The workbook streamreader
    */
    private VirtualStreamReader workBookStreamReader;
    /**
    * The Workbookextractor / container
    */
    private WorkbookExtractor workBookExtr;
    /**
    * This attribute stores the hole Workbookdata
    */
    public WorkBookData WorkBookData;
    /**
    * The StructuredStorageFile itself
    */
    public StructuredStorageReader Storage;
    /**
    * Ctor
    * 
    *  @param file
    */
    public XlsDocument(StructuredStorageReader reader) throws Exception {
        this.WorkBookData = new WorkBookData();
        this.Storage = reader;
        if (reader.getFullNameOfAllStreamEntries().Contains("\\" + WORKBOOK))
        {
            this.workBookStreamReader = new VirtualStreamReader(reader.getStream(WORKBOOK));
        }
        else if (reader.getFullNameOfAllStreamEntries().Contains("\\" + ALTERNATE1))
        {
            this.workBookStreamReader = new VirtualStreamReader(reader.getStream(ALTERNATE1));
        }
        else
        {
            throw new ExtractorException(ExtractorException.WORKBOOKSTREAMNOTFOUND);
        }  
        this.workBookExtr = new WorkbookExtractor(this.workBookStreamReader,this.WorkBookData);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<XlsDocument>)mapping).apply(this);
    }

}


