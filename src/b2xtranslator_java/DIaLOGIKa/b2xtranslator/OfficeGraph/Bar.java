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
* This record specifies that the chart group is a bar chart group or
* a column chart group, and specifies the chart group attributes.
*/
public class Bar  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.Bar;
    /**
    * A signed integer that specifies the overlap between data points in the same category
    * as a percentage of the data point width.
    * 
    * MUST be greater than or equal to -100 and less than or equal to 100.
    * 
    * MUST be a value from the following table:
    * 
    * Value         Meaning
    * -100 to -1    Size of the separation between data points
    * 0             No overlap
    * 1 to 100      Size of the overlap between data points
    */
    public short pcOverlap;
    /**
    * An unsigned integer that specifies the width of the gap between the categories
    * and the left and right edges of the plot area as a percentage of the data point width divided by 2.
    * 
    * It also specifies the width of the gap between adjacent categories
    * as a percentage of the data point width.
    * 
    * MUST be less than or equal to 500.
    */
    public UInt16 pcGap = new UInt16();
    /**
    * A bit that specifies whether the data points and value axis are horizontal
    * (for a bar chart group) or vertical (for a column chart group).
    * 
    * MUST be a value from the following table:
    * 
    * Value   Meaning
    * 0       Data points and value axis are vertical.
    * 1       Data points and value axis are horizontal.
    */
    public boolean fTranspose;
    /**
    * A bit that specifies whether the data points in the chart group
    * that share the same category are stacked one on top of the next.
    */
    public boolean fStacked;
    /**
    * A bit that specifies whether the data points in the chart group are displayed
    * as a percentage of the sum of all data points in the chart group
    * that share the same category.
    * 
    * MUST be 0 if fStacked is 0.
    */
    public boolean f100;
    /**
    * A bit that specifies whether one or more data points in the chart group has shadows.
    */
    public boolean fHasShadow;
    public Bar(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.pcOverlap = reader.readInt16();
        this.pcGap = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        this.fTranspose = Utils.BitmaskToBool(flags, 0x1);
        this.fStacked = Utils.BitmaskToBool(flags, 0x2);
        this.f100 = Utils.BitmaskToBool(flags, 0x4);
        this.fHasShadow = Utils.BitmaskToBool(flags, 0x8);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


