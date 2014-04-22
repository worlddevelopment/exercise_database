//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:44 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
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
public class HeaderFooter  extends BiffRecord 
{
    public static final RecordType ID = RecordType.HeaderFooter;
    /**
    * An FrtHeader. The frtHeader.rt field MUST be 0x089C.
    */
    public FrtHeader frtHeader;
    /**
    * A GUID as specified by [MS-DTYP] that specifies the current sheet view. 
    * If it is zero, it means the current sheet. 
    * Otherwise, this field MUST match the guid field of the preceding UserSViewBegin record.
    */
    public UUID guidSView;
    /**
    * An unsigned integer that specifies the number of characters in strHeaderEven. 
    * MUST be less than or equal to 255. 
    * MUST be zero if fHFDiffOddEven is zero.
    */
    public UInt16 cchHeaderEven = new UInt16();
    /**
    * An unsigned integer that specifies the number of characters in strFooterEven.
    * MUST be less than or equal to 255. 
    * MUST be zero if fHFDiffOddEven is zero.
    */
    public UInt16 cchFooterEven = new UInt16();
    /**
    * An unsigned integer that specifies the number of characters in strHeaderFirst.
    * MUST be less than or equal to 255. 
    * MUST be zero if fHFDiffFirst is zero.
    */
    public UInt16 cchHeaderFirst = new UInt16();
    /**
    * An unsigned integer that specifies the number of characters in strFooterFirst.
    * MUST be less than or equal to 255. 
    * MUST be zero if fHFDiffFirst is zero.
    */
    public UInt16 cchFooterFirst = new UInt16();
    /**
    * An XLUnicodeString that specifies the header text on the even pages. 
    * The number of characters in the string MUST be equal to cchHeaderEven. 
    * The string can contain special commands, for example a placeholder for the page number,
    * current date or text formatting attributes. 
    * Refer to Header for more details about the string format.
    */
    public String strHeaderEven;
    /**
    * An XLUnicodeString that specifies the footer text on the even pages.
    * The number of characters in the string MUST be equal to cchFooterEven.
    * The string can contain special commands, for example a placeholder for the page number,
    * current date or text formatting attributes.
    * Refer to Header for more details about the string format.
    */
    public String strFooterEven;
    /**
    * An XLUnicodeString that specifies the header text on the first page. 
    * The number of characters in the string MUST be equal to cchHeaderFirst. 
    * The string can containspecial commands, for example a placeholder for the page number,
    * current date or text formatting attributes. 
    * Refer to Header for more details about the string format.
    */
    public String strHeaderFirst;
    /**
    * An XLUnicodeString that specifies the footer text on the first page. 
    * The number of characters in the string MUST be equal to cchFooterFirst. 
    * The string can contain special commands, for example a placeholder for the page number,
    * current date or text formatting attributes. 
    * Refer to Header for more details about the string format.
    */
    public String strFooterFirst;
    public HeaderFooter(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeader = new FrtHeader(reader);
        this.guidSView = new UUID(reader.readBytes(16));
        UInt16 flags = reader.readUInt16();
        this.cchHeaderEven = reader.readUInt16();
        this.cchFooterEven = reader.readUInt16();
        this.cchHeaderFirst = reader.readUInt16();
        this.cchFooterFirst = reader.readUInt16();
        byte[] strHeaderEvenBytes = reader.ReadBytes(cchHeaderEven);
        byte[] strFooterEvenBytes = reader.ReadBytes(cchFooterEven);
        byte[] strHeaderFirstBytes = reader.ReadBytes(cchHeaderFirst);
        byte[] strFooterFirstBytes = reader.ReadBytes(cchFooterFirst);
        //this.strHeaderEven = new XLUnicodeString(reader).Value;
        //this.strFooterEven = new XLUnicodeString(reader).Value;
        //this.strHeaderFirst = new XLUnicodeString(reader).Value;
        //this.strFooterFirst = new XLUnicodeString(reader).Value;
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


