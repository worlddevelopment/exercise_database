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
* This record specifies that the chart group is a bar of pie chart group or a pie of pie chart group and specifies the chart group attributes.
*/
public class BopPop  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.BopPop;
    public enum Subtype
    {
        __dummyEnum__0,
        PieOfPie,
        BarOfPie
    }
    public enum SplitType
    {
        Position,
        Value,
        Percent,
        Custom
    }
    /**
    * An unsigned integer that specifies whether this chart group is a bar of pie chart group or a pie of pie chart group.
    */
    public Subtype pst = Subtype.PieOfPie;
    /**
    * A Boolean that specifies whether the split point of the chart group is determined automatically.
    * 
    * If the value is 1, when a bar of pie chart group or pie of pie chart group is initially created
    * the data points from the primary pie are selected and inserted into the secondary bar/pie automatically.
    */
    public boolean fAutoSplit;
    /**
    * An unsigned integer that specifies what determines the split between the primary pie and the secondary bar/pie.
    * 
    * MUST be ignored if fAutoSplit is set to 1.
    */
    public SplitType split = SplitType.Position;
    /// <summary>
    /// A signed integer that specifies how many data points are contained in the secondary bar/pie.
    ///
    /// Data points are contained in the secondary bar/pie starting from the end of the series.
    /// For example, if the value is 2, the last 2 data points in the series are contained in the secondary bar/pie.
    ///
    /// MUST be greater than 0, and less than or equal to 32000.
    /// SHOULD <32> be a value less than or equal to 3999. If the value is greater than the number of data
    /// points in the series, the entire series will be in the secondary bar/pie, except for the first
    /// data point. If split is not set to 0x0000 or if fAutoSplit is set to 1, this value MUST be ignored.
    /// </summary>
    public short iSplitPos;
    /**
    * A signed integer that specifies the percentage below which each data point is contained
    * in the secondary bar/pie as opposed to the primary pie.
    * 
    * The percentage value of a data point is calculated using the following formula:
    * 
    * (value of the data point x 100) / sum of all data point in the series
    * 
    * If split is not set to 0x0002 or if fAutoSplit is set to 1, this value MUST be ignored.
    */
    public short pcSplitPercent;
    /**
    * A signed integer that specifies the size of the secondary bar/pie as a percentage of the size of the primary pie.
    * 
    * MUST be a value greater than or equal to 5 and less than or equal to 200.
    */
    public short pcPie2Size;
    /**
    * A signed integer that specifies the distance between the primary pie and the secondary bar/pie.
    * 
    * The distance is specified as a percentage of the average width of the primary pie and secondary bar/pie.
    * 
    * MUST be a value greater than or equal to 0 and less than or equal to 200, where 0 is 0% of the average
    * width of the primary pie and the secondary bar/pie, and 200 is 100% of
    * the average width of the primary pie and the secondary bar/pie.
    */
    public short pcGap;
    /**
    * An Xnum that specifies the split when the split field is set to 0x0001.
    * 
    * The value of this field specifies the threshold that selects which data points of
    * the primary pie move to the secondary bar/pie. The secondary bar/pie contains any
    * data points with a value less than the value of this field. If split is not set to
    * 0x0001 or if fAutoSplit is set to 1, this value MUST be ignored.
    */
    public double numSplitValue;
    /**
    * A bit that specifies whether one or more data points in the chart group have shadows.
    */
    public boolean fHasShadow;
    public BopPop(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.pst = (Subtype)reader.readByte();
        this.fAutoSplit = !(reader.readByte() == 0x0);
        this.split = (SplitType)reader.readUInt16();
        this.iSplitPos = reader.readInt16();
        this.pcSplitPercent = reader.readInt16();
        this.pcPie2Size = reader.readInt16();
        this.pcGap = reader.readInt16();
        this.numSplitValue = reader.readDouble();
        this.fHasShadow = Utils.BitmaskToBool(reader.readUInt16(), 0x0001);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


