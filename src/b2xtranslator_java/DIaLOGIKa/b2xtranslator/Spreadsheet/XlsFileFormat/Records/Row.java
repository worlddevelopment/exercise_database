//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:47 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TwipsValue;

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
public class Row  extends BiffRecord 
{
    public static final RecordType ID = RecordType.Row;
    public int rw;
    public int colMic;
    public int colMac;
    public int miyRw;
    public int iOutLevel;
    public boolean fCollapsed;
    public boolean fDyZero;
    public boolean fUnsynced;
    public boolean fGhostDirty;
    public int ixfe_val;
    public boolean fExAsc;
    public boolean fExDes;
    public boolean fPhonetic;
    public Row(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.rw = reader.readUInt16();
        this.colMic = reader.readUInt16();
        this.colMac = reader.readUInt16();
        this.miyRw = reader.readUInt16();
        // read four unused bytes
        reader.readUInt32();
        TwipsValue tv = new TwipsValue(this.miyRw);
        // read 2 byte for some bit operations
        UInt16 buffer = reader.readUInt16();
        /**
        * A - iOutLevel (3 bits): An unsigned integer that specifies the outline level (1) of the row.
        * B - reserved2 (1 bit): MUST be zero, and MUST be ignored.
        * C - fCollapsed (1 bit): A bit that specifies whether
        * D - fDyZero (1 bit): A bit that specifies whether the row is hidden.
        * E - fUnsynced (1 bit): A bit that specifies whether the row height has been manually set.
        * F - fGhostDirty (1 bit): A bit that specifies whether the row has been formatted.
        * reserved3 (1 byte): MUST be 1, and MUST be ignored.
        */
        this.iOutLevel = buffer & 0x0007;
        this.fCollapsed = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x8);
        this.fDyZero = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x10);
        this.fUnsynced = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x20);
        this.fGhostDirty = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x40);
        // read 2 byte for some bit operations
        buffer = reader.readUInt16();
        this.ixfe_val = buffer & 0x0FFF;
        this.fExAsc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x1000);
        this.fExDes = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x2000);
        this.fPhonetic = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(buffer, 0x4000);
        /**
        * ixfe_val (12 bits): An unsigned integer that specifies a XF record for the row formatting.
        * G - fExAsc (1 bit): A bit that specifies whether any cell in the row has a thick top border, or any cell in the row directly above the current row has a thick bottom border. Thick borders are the following enumeration values from BorderStyle: THICK and DOUBLE.
        * H - fExDes (1 bit): A bit that specifies whether any cell in the row has a medium or thick bottom border, or any cell in the row directly below the current row has a medium or thick top border. Thick borders are previously specified. Medium borders are the following enumeration values from BorderStyle: MEDIUM, MEDIUMDASHED, MEDIUMDASHDOT, MEDIUMDASHDOTDOT, and SLANTDASHDOT.
        * I - fPhonetic (1 bit): A bit that specifies whether the phonetic guide feature is enabled for any cell in this row. J - unused2 (1 bit): Undefined and MUST be ignored.
        */
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


