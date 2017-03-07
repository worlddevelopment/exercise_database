//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:48 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.ChartNumNillable;
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
* This record specifies a trendline.
*/
public class SerAuxTrend  extends BiffRecord 
{
    public enum TrendlineType
    {
        Polynomial,
        Exponential,
        Logarithmic,
        Power,
        MovingAverage
    }
    public static final RecordType ID = RecordType.SerAuxTrend;
    /**
    * Specifies the type of trendline.
    */
    public TrendlineType regt = TrendlineType.Polynomial;
    /**
    * specifies the polynomial order or moving average period. 
    * MUST be greater than or equal to 0x02 and less than or equal to 0x06 if regt equals 0x00; 
    * MUST be greater than or equal to 0x02 and less than or equal to the value of the cValx field of
    * the Series record specified by the preceding SerParent record minus one if regt equals 0x04. 
    * MUST be ignored for other values of regt.
    */
    public byte ordUser;
    public Double numIntercept;
    /**
    * Specifies where the trendline intersects the value Axis or vertical Axis on bubble and scatter chart groups. 
    * If no intercept is specified, this ChartNumNillable MUST be null
    * 
    * A Boolean that specifies whether the trendline equation is displayed in the trendline label.
    * MUST be ignored if regt equals 0x04. 
    * MUST be ignored if the chart sheet substream contains an attached label with an
    * ObjectLink record that contains both a wLinkObj field equal to 0x0004 and
    * a wLinkVar1 field equal to the zero-based index into a Series record in the
    * collection of Series records in the current chart sheet substream that represents
    * this trendline, and the attached label contains a SeriesText record.
    */
    public boolean fEquation;
    /**
    * A Boolean that specifies whether the R-squared value is displayed in the trendline label.
    * MUST be ignored if regt equals 0x04.
    * MUST be ignored if the chart sheet substream contains an attached label with an ObjectLink
    * record that contains both a wLinkObj field equal to 0x0004 and a wLinkVar1 field equal to
    * the zero-based index into a Series record in the collection of Series records in the
    * current chart sheet substream that represents this trendline, and the attached label
    * contains a SeriesText record.
    */
    public boolean fRSquared;
    /**
    * An Xnum that specifies the number of periods to forecast forward.
    */
    public double numForecast;
    /**
    * An Xnum that specifies the number of periods to forecast backward.
    */
    public double numBackcast;
    public SerAuxTrend(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.regt = (TrendlineType)reader.readByte();
        this.ordUser = reader.readByte();
        //read the nullable double value (ChartNumNillable)
        this.numIntercept = new ChartNumNillable(reader).value;
        this.fEquation = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool(reader.readByte());
        this.fRSquared = DIaLOGIKa.b2xtranslator.Tools.Utils.byteToBool(reader.readByte());
        this.numForecast = reader.readDouble();
        this.numBackcast = reader.readDouble();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}

