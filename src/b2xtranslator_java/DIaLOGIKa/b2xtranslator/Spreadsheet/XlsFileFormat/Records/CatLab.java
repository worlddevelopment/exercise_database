//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:41 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
/**
* This record specifies the attributes of the axis label.
*/
public class CatLab  extends BiffRecord 
{
    public static final RecordType ID = RecordType.CatLab;
    public enum Alignment
    {
        /**
        * Top-aligned if the trot field of the Text record of the axis is not equal to 0.
        * Left-aligned if the iReadingOrder field of the Text record of the
        * axis specifies left-to-right reading order; otherwise, right-aligned.
        */
        __dummyEnum__0,
        Top,
        /**
        * Center-alignment
        */
        Center,
        /**
        * Bottom-aligned if the trot field of the Text record of the axis is not equal to 0.
        * Right-aligned if the iReadingOrder field of the Text record of the
        * axis specifies left-to-right reading order; otherwise, left-aligned.
        */
        Bottom
    }
    public enum CatLabelType
    {
        /**
        * The value is set to caLabel field as specified by CatSerRange record.
        */
        Custom,
        /**
        * The value is set to the default value. The number of category (3) labels
        * is automatically calculated by the application based on the data in the chart.
        */
        Auto
    }
    /**
    * An FrtHeaderOld. The frtHeaderOld.rt field MUST be 0x0856.
    * 
    * This structure specifies a future record.
    */
    public long frtHeaderOld;
    /**
    * An unsigned integer that specifies the distance between the axis and axis label.
    * It contains the offset as a percentage of the default distance.
    * The default distance is equal to 1/3 the height of the font calculated in pixels.
    * 
    * MUST be a value greater than or equal to 0 (0%) and less than or equal to 1000 (1000%).
    */
    public UInt16 wOffset = new UInt16();
    /**
    * An unsigned integer that specifies the alignment of the axis label.
    */
    public Alignment at = Alignment.Top;
    public CatLabelType cAutoCatLabelReal = CatLabelType.Custom;
    public CatLab(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeaderOld = reader.readUInt32();
        this.wOffset = reader.readUInt16();
        this.at = (Alignment)reader.readUInt16();
        this.cAutoCatLabelReal = (CatLabelType)DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToUInt16(reader.readUInt16(), 0x0001);
        // ignore last 2 bytes (reserved and optional)
        if (this.Length > 10)
        {
            reader.readBytes(2);
        }
         
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


