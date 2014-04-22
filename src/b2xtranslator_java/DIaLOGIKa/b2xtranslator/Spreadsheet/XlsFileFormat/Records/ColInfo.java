//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:41 AM
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
public class ColInfo  extends BiffRecord 
{
    public static final RecordType ID = RecordType.ColInfo;
    public int colFirst;
    public int colLast;
    public int coldx;
    public boolean fUserSet;
    public boolean fHidden;
    public boolean fBestFit;
    public boolean fPhonetic;
    public int iOutLevel;
    public boolean fCollapsed;
    public int ixfe;
    public ColInfo(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.colFirst = reader.readUInt16();
        this.colLast = reader.readUInt16();
        this.coldx = reader.readUInt16();
        this.ixfe = reader.readUInt16();
        int buffer = reader.readUInt16();
        /**
        * A - fHidden (1 bit)
        * B - fUserSet (1 bit)
        * C - fBestFit (1 bit)
        * D - fPhonetic (1 bit)
        * E - reserved1 (4 bits): MUST be zero, and MUST be ignored.
        * F - iOutLevel (3 bits)
        * G - unused1 (1 bit): Undefined and MUST be ignored.
        * H - fCollapsed (1 bit)
        * I - reserved2 (3 bits): MUST be zero, and MUST be ignored.
        */
        this.fHidden = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(buffer,0x0001);
        this.fUserSet = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(buffer,0x0002);
        this.fBestFit = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(buffer,0x0004);
        this.fPhonetic = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(buffer,0x0008);
        this.iOutLevel = ((int)((buffer & 0x0700))) >> 0x8;
        this.fCollapsed = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool(buffer,0x1000);
        // read two following not documented bytes
        reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


