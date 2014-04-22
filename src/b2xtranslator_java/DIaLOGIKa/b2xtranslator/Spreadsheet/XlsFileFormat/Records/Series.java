//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:48 AM
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
* This record specifies properties of the data for a series, a trendline, or error bars, and
* specifies the beginning of a collection of records as defined by the Chart Sheet Substream ABNF.
* The collection of records specifies a series, a trendline, or error bars.
*/
public class Series  extends BiffRecord 
{
    public enum SeriesDataType
    {
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        Text,
        Numeric
    }
    public static final RecordType ID = RecordType.Series;
    /**
    * specifies the type of data in categories (3), or horizontal values on
    * bubble and scatter chart groups, in the series.
    */
    public SeriesDataType sdtX = SeriesDataType.Text;
    /**
    * An unsigned integer that specifies that the values, or vertical values on bubble and
    * scatter chart groups, in the series contain numeric information.
    * It MUST be Numeric, and MUST be ignored.
    */
    public SeriesDataType sdtY = SeriesDataType.Text;
    /**
    * An unsigned integer that specifies the count of categories (3),
    * or horizontal values on bubble and scatter chart groups, in the series. 
    * The value MUST be less than or equal to 3999.
    */
    public UInt16 cValx = new UInt16();
    /**
    * An unsigned integer that specifies the count of values, or vertical
    * values on bubble and scatter chart groups, in the series. 
    * The value MUST be less than or equal to 3999.
    */
    public UInt16 cValy = new UInt16();
    /**
    * specifies that the bubble size values in the series contain numeric information.
    * The value MUST be Numeric, and MUST be ignored.
    */
    public SeriesDataType sdtBSize = SeriesDataType.Text;
    /**
    * An unsigned integer that specifies the count of bubble size values in the series. 
    * The value MUST be less than or equal to 3999.
    */
    public UInt16 cValBSize = new UInt16();
    public Series(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.sdtX = (SeriesDataType)reader.readUInt16();
        this.sdtY = (SeriesDataType)reader.readUInt16();
        this.cValx = reader.readUInt16();
        this.cValy = reader.readUInt16();
        this.sdtBSize = (SeriesDataType)reader.readUInt16();
        this.cValBSize = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


