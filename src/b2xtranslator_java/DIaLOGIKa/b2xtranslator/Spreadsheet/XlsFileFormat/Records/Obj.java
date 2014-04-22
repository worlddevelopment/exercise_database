//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:45 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtCbls;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtCblsData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtCf;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtCmo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtEdoData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtGmo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtMacro;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtNts;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtPictFmla;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtPioGrbit;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtRbo;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtRboData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FtSbs;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.ObjLinkFmla;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

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
public class Obj  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Obj;
    public FtCmo cmo;
    public FtGmo gmo;
    public FtCf pictFormat;
    public FtPioGrbit pictFlags;
    public FtCbls cbls;
    public FtRbo rbo;
    public FtSbs sbs;
    public FtNts nts;
    public FtMacro macro;
    public FtPictFmla pictFmla;
    public ObjLinkFmla linkFmla;
    public FtCblsData checkBox;
    public FtRboData radioButton;
    public FtEdoData edit;
    public Obj(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        // TODO: place code here
        reader.ReadBytes(length);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


