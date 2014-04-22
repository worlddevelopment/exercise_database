//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:50 AM
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
/**
* TABLESTYLE: Table Style (88Fh)
* 
* This record is used for each custom Table style in use in the document.
*/
public class TableStyle  extends BiffRecord 
{
    public static final RecordType ID = RecordType.TableStyle;
    /**
    * Record type; this matches the BIFF rt in the first two bytes of the record; =088Fh
    */
    public UInt16 rt = new UInt16();
    /**
    * FRT cell reference flag; =0 currently
    */
    public UInt16 grbitFrt = new UInt16();
    /**
    * Currently not used, and set to 0
    */
    public UInt64 reserved0 = new UInt64();
    /**
    * A packed bit field
    */
    private UInt16 grbitTS = new UInt16();
    /**
    * Count of TABLESTYLEELEMENT records to follow.
    */
    public long ctse;
    /**
    * Length of Table style name in 2 byte characters.
    */
    public UInt16 cchName = new UInt16();
    /**
    * Table style name in 2 byte characters
    */
    public byte[] rgchName;
    /**
    * Should always be 0.
    */
    public boolean fIsBuiltIn;
    /**
    * =1 if Table style can be applied to PivotTables
    */
    public boolean fIsPivot;
    /**
    * =1 if Table style can be applied to Tables
    */
    public boolean fIsTable;
    /**
    * Reserved; must be 0 (zero)
    */
    public UInt16 fReserved0 = new UInt16();
    public TableStyle(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        rt = reader.readUInt16();
        grbitFrt = reader.readUInt16();
        reserved0 = reader.readUInt64();
        grbitTS = reader.readUInt16();
        fIsBuiltIn = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbitTS, 0x0001);
        fIsPivot = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbitTS, 0x0002);
        fIsTable = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(grbitTS, 0x0004);
        fReserved0 = (ushort)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToInt(grbitTS, 0xFFF8);
        ctse = reader.readUInt32();
        cchName = reader.readUInt16();
        rgchName = reader.ReadBytes(cchName * 2);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


