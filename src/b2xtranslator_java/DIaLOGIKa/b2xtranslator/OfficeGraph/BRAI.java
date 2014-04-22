//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:42 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

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
* This record specifies a reference to data in a sheet that is used by a part of a series or by a legend entry.
*/
public class BRAI  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.BRAI;
    public enum BraiId
    {
        /**
        * Referenced data is used for series name or text of a legend entry.
        */
        SeriesNameOrLegendText,
        /**
        * Referenced data is used for series values
        */
        SeriesValues,
        /**
        * Referenced data is used for series category (3) name.
        */
        SeriesCategory,
        /**
        * Referenced data specifies the bubble size values of the series.
        */
        BubbleSizeValues
    }
    public enum Formatting
    {
        FromReference,
        Custom
    }
    /**
    * An unsigned integer that specifies the part of the chart for which the referenced data is used for.
    */
    public BraiId braiId = BraiId.SeriesNameOrLegendText;
    /**
    * An unsigned integer that specifies the type of data that is being referenced.
    * 
    * MUST be a value from the following table:
    * 
    * Value      Meaning
    * 0x00       The data source is a category (3) name or bubble size that is automatically generated.
    * 0x01       The data source as specified by rowcol.
    * 0x02       The data source as specified by rowcol.
    */
    public byte rt;
    /**
    * A bit that specifies whether the part of the chart specified by the id field uses number formatting from the referenced data.
    */
    public Formatting fUnlinkedIfmt = Formatting.FromReference;
    /// <summary>
    /// An unsigned integer that specifies the identifier of a number format.
    ///
    /// The identifier specified by this field MUST be a valid built-in number format identifier
    /// or the identifier of a custom number format as specified using a Format record.
    ///
    /// Custom number format identifiers MUST be greater than or equal to 0x00A4 less than or
    /// equal to 0x0188, and SHOULD <78> be less than or equal to 0x017E.
    ///
    /// The built-in number formats are listed in [ECMA-376] Part 4: Markup Language Reference, section 3.8.30.
    /// </summary>
    public UInt16 ifmt = new UInt16();
    /**
    * An unsigned integer that specifies the row or column information of the reference.
    * 
    * If the fSeriesInRows field of the Orient record is 1, it MUST contain the row number of the
    * specified information. If the fSeriesInRows field of the Orient record is 0, it
    * MUST contain the column number of the specified information. Row number is a zero-based
    * index of the row in the datasheet and column number is a zero-based index of the
    * column of the data sheet. MUST be less than or equal to 0x0F9F.
    */
    public UInt16 rowcol = new UInt16();
    public BRAI(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.braiId = (BraiId)reader.readByte();
        this.rt = reader.readByte();
        this.fUnlinkedIfmt = (Formatting)Utils.BitmaskToUInt16(reader.readUInt16(), 0x1);
        this.ifmt = reader.readUInt16();
        this.rowcol = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


