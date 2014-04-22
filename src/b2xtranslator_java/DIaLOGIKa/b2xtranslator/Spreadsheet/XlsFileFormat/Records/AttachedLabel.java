//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:40 AM
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
* This record specifies properties of a data label on a chart group, series, or data point.
* 
* Refer to the data label overview for additional information on how this record is used and when this record is ignored.
*/
public class AttachedLabel  extends BiffRecord 
{
    public static final RecordType ID = RecordType.AttachedLabel;
    /**
    * A bit that specifies whether the value, or the vertical value on bubble or scatter chart groups, is displayed in the data label.
    * 
    * This value MUST be 0 if this record is in a chart group and either fLabelAndPerc or fShowPercent is equal to 1.
    */
    public boolean fShowValue;
    /**
    * A bit that specifies whether the value, represented as a percentage of
    * the sum of the values of the series the data label is associated with,
    * is displayed in the data label.
    * 
    * MUST equal 0 if the chart group type of the corresponding chart group,
    * series, or data point, is not bar of pie, doughnut, pie, or pie of pie chart group.
    * 
    * If this record is contained in a chart group and f
    * ShowLabelAndPerc equals 1 then this field MUST equal 1.
    */
    public boolean fShowPercent;
    /**
    * A bit that specifies whether the category (3) name and value, represented
    * as a percentage of the sum of the values of the series the data label
    * is associated with, are displayed in the data label.
    * 
    * MUST equal 0 if the chart group type of the corresponding chart group,
    * series, or data point is not bar of pie, doughnut, pie, or pie of pie chart group.
    */
    public boolean fShowLabelAndPerc;
    /**
    * A bit that specifies whether the category (3), or the horizontal
    * value on bubble or scatter chart groups, is displayed in the data
    * label on a non-area chart group, or the series name is displayed
    * in the data label on an area chart group.
    * 
    * This field MUST equal 0 if this record is contained in a chart
    * group and one of the following conditions is satisfied:
    * - The fShowValue field equals 1.
    * - The fShowLabelAndPerc field equals 0 and the fShowPercent field equals 1.
    */
    public boolean fShowLabel;
    /**
    * A bit that specifies whether the bubble size is displayed in the data label.
    * 
    * MUST equal 0 if the chart group type of the corresponding
    * chart group, series, or data point is not bubble chart group.
    * 
    * If the current record is contained in a chart group and
    * fShowPercent, fShowValue, or fShowLabel equal 1, this field MUST equal 0.
    */
    public boolean fShowBubbleSizes;
    /**
    * A bit that specifies whether the data label contains the name of the series.
    * 
    * If the current record is contained in a chart group and fShowLabelAndPerc,
    * fShowPercent, fShowValue, fShowValue, fShowLabel, or
    * fShowBubbleSizes equal 1 then this MUST equal to 0.
    */
    public boolean fShowSeriesName;
    public AttachedLabel(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        UInt16 flags = reader.readUInt16();
        this.fShowValue = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1);
        this.fShowPercent = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x2);
        this.fShowLabelAndPerc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x4);
        // Note: bit mask 0x8 is skipped
        this.fShowLabel = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x10);
        this.fShowBubbleSizes = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x20);
        this.fShowSeriesName = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x40);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


