//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:49 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
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
public class Setup  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Setup;
    public UInt16 iPaperSize = new UInt16();
    public UInt16 iScale = new UInt16();
    public UInt16 iPageStart = new UInt16();
    public UInt16 iFitWidth = new UInt16();
    public UInt16 iFitHeight = new UInt16();
    public UInt16 grbit = new UInt16();
    public UInt16 iRes = new UInt16();
    public UInt16 iVRes = new UInt16();
    public UInt16 iCopies = new UInt16();
    public double numHdr;
    public double numFtr;
    public boolean fLeftToRight;
    public boolean fPortrait;
    public boolean fNoPls;
    public boolean fNoColor;
    public boolean fDraft;
    public boolean fNotes;
    public boolean fNoOrient;
    public boolean fUsePage;
    public boolean fEndNotes;
    public int iErrors;
    public Setup(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.iPaperSize = reader.readUInt16();
        this.iScale = reader.readUInt16();
        this.iPageStart = reader.readUInt16();
        this.iFitWidth = reader.readUInt16();
        this.iFitHeight = reader.readUInt16();
        this.grbit = reader.readUInt16();
        this.iRes = reader.readUInt16();
        this.iVRes = reader.readUInt16();
        this.numHdr = reader.readDouble();
        this.numFtr = reader.readDouble();
        this.iCopies = reader.readUInt16();
        // set flags
        this.fLeftToRight = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x01);
        this.fPortrait = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x02);
        this.fNoPls = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x04);
        this.fNoColor = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x08);
        this.fDraft = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x010);
        this.fNotes = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x020);
        this.fNoOrient = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x040);
        this.fUsePage = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x080);
        this.fEndNotes = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(this.grbit, 0x080);
        this.iErrors = (this.grbit & 0x0C00) << 0x0A;
    }

}


