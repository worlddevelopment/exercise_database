//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:40 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.ShortXLUnicodeString;
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
* BOUNDSHEET: Sheet Information (85h)
* 
* This record stores the sheet name, sheet type, and stream position.
*/
public class BoundSheet8  extends BiffRecord 
{
    public static final RecordType ID = RecordType.BoundSheet8;
    public enum HiddenState
    {
        /**
        * Visible
        */
        Visible,
        /**
        * Hidden
        */
        Hidden,
        /**
        * Very Hidden; the sheet is hidden and cannot be displayed using the user interface.
        */
        VeryHidden
    }
    public enum SheetType
    {
        /**
        * Worksheet or dialog sheet
        */
        Worksheet,
        /**
        * Excel 4.0 macro sheet
        */
        Macrosheet,
        /**
        * Chart sheet
        */
        Chartsheet,
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        /**
        * Visual Basic module
        */
        VisualBasicModule
    }
    /**
    * A FilePointer as specified in [MS-OSHARED] section 2.2.1.5 that specifies the
    * stream position of the start of the BOF record for the sheet.
    */
    public long lbPlyPos;
    /**
    * A ShortXLUnicodeString that specifies the unique case-insensitive name of the sheet.
    * The character count of this string, stName.ch, MUST be greater than or equal to 1
    * and less than or equal to 31.
    * 
    * The string MUST NOT contain the any of the following characters:
    * 
    * - 0x0000
    * - 0x0003
    * - colon (:)
    * - backslash (\)
    * - asterisk (*)
    * - question mark (?)
    * - forward slash (/)
    * - opening square bracket ([)
    * - closing square bracket (])
    * 
    * The string MUST NOT begin or end with the single quote (') character.
    */
    public ShortXLUnicodeString stName;
    // TODO: check for correct interpretation of Unicode strings
    /**
    * The hidden status of the workbook
    */
    public HiddenState hsState = HiddenState.Visible;
    /**
    * The sheet type value
    */
    public SheetType dt = SheetType.Worksheet;
    /**
    * extracts the boundsheetdata from the biffrecord
    * 
    *  @param reader IStreamReader 
    *  @param id Type of the record 
    *  @param length Length of the record
    */
    public BoundSheet8(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.lbPlyPos = this.Reader.ReadUInt32();
        byte flags = reader.readByte();
        // Bitmask is 0003h -> first two bits
        this.hsState = (HiddenState)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToByte(flags, 0x0003);
        this.dt = (SheetType)reader.readByte();
        this.stName = new ShortXLUnicodeString(reader);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

    /**
    * Simple ToString Method
    * 
    *  @return String from the object
    */
    public String toString() {
        try
        {
            String returnvalue = "BOUNDSHEET - RECORD: \n";
            returnvalue += "-- Name: " + this.stName.getValue() + "\n";
            returnvalue += "-- Offset: " + this.lbPlyPos + "\n";
            returnvalue += "-- HiddenState: " + this.hsState + "\n";
            returnvalue += "-- SheetType: " + this.dt + "\n";
            return returnvalue;
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


