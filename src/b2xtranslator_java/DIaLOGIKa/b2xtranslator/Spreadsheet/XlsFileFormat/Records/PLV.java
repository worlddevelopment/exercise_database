//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:46 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeader;
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
* This record specifies the settings of a Page Layout view for a sheet.
*/
public class PLV  extends BiffRecord 
{
    public static final RecordType ID = RecordType.PLV;
    /**
    * An FrtHeader. The frtHeader.rt field MUST be 0x088B.
    */
    public FrtHeader frtHeader;
    /**
    * An unsigned integer that specifies zoom scale as a percentage for the
    * Page Layout view of the current sheet. For example, if the value is 107,
    * then the zoom scale is 107%. The value 0 means that the zoom scale has not
    * been set. If the value is nonzero, it MUST be greater than or equal
    * to 10 and less than or equal to 400.
    */
    public UInt16 wScalePLV = new UInt16();
    /**
    * A bit that specifies whether the sheet is in the Page Layout view.
    * If the fSLV in Window2 record is 1 for this sheet, it MUST be 0
    */
    public boolean fPageLayoutView;
    /**
    * A bit that specifies whether the application displays the ruler.
    */
    public boolean fRulerVisible;
    /**
    * A bit that specifies whether the margins between pages are hidden in the Page Layout view.
    */
    public boolean fWhitespaceHidden;
    public PLV(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeader = new FrtHeader(reader);
        this.wScalePLV = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        this.fPageLayoutView = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0001);
        this.fRulerVisible = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        this.fWhitespaceHidden = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


