//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:30 AM
//

package DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping;

import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.XlsDocument;
import DIaLOGIKa.b2xtranslator.SpreadsheetMLMapping.ExcelContext;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.DirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StorageDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageWriter;
import java.util.UUID;

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
public class MacroBinaryMapping  extends AbstractOpenXmlMapping implements IMapping<XlsDocument>
{
    private ExcelContext ctx;
    private String projectFolder = "\\_VBA_PROJECT_CUR";
    private String vbaFolder = "\\_VBA_PROJECT_CUR\\VBA";
    private String projectFile = "\\_VBA_PROJECT_CUR\\PROJECT";
    private String projectWmFile = "\\_VBA_PROJECT_CUR\\PROJECTwm";
    public MacroBinaryMapping(ExcelContext ctx) throws Exception {
        super(null);
        this.ctx = ctx;
    }

    public void apply(XlsDocument xls) throws Exception {
        //get the Class IDs of the directories
        UUID macroClsid = UUID.randomUUID();
        UUID vbaClsid = UUID.randomUUID();
        for (DirectoryEntry entry : xls.Storage.getAllEntries())
        {
            if (StringSupport.equals(entry.getPath(), projectFolder))
            {
                macroClsid = entry.getClsId();
            }
            else if (StringSupport.equals(entry.getPath(), vbaFolder))
            {
                vbaClsid = entry.getClsId();
            }
              
        }
        //create a new storage
        StructuredStorageWriter storage = new StructuredStorageWriter();
        storage.getRootDirectoryEntry().setClsId(macroClsid);
        //copy the VBA directory
        StorageDirectoryEntry vba = storage.getRootDirectoryEntry().addStorageDirectoryEntry("VBA");
        vba.setClsId(vbaClsid);
        for (DirectoryEntry entry : xls.Storage.getAllStreamEntries())
        {
            if (entry.getPath().startsWith(vbaFolder))
            {
                vba.addStreamDirectoryEntry(entry.getName(),xls.Storage.getStream(entry.getPath()));
            }
             
        }
        //copy the project streams
        storage.getRootDirectoryEntry().addStreamDirectoryEntry("PROJECT",xls.Storage.getStream(projectFile));
        storage.getRootDirectoryEntry().addStreamDirectoryEntry("PROJECTwm",xls.Storage.getStream(projectWmFile));
        //write the storage to the xml part
        storage.write(ctx.getSpreadDoc().getWorkbookPart().getVbaProjectPart().getStream());
    }

}


