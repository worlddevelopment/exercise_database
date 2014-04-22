//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:44 AM
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
* This record specifies that the chart group is a scatter chart group or a bubble chart group,
* and specifies the chart group attributes.
*/
public class Scatter  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.Scatter;
    /**
    * An unsigned integer that specifies the size of the data points as a percentage of their default size. 
    * A value of 100 shows all the data points in their default size, as determined by the application. 
    * MUST be greater or equal to 0 and less than or equal to 300. 
    * MUST be ignored if the fBubbles field is 0.
    */
    public UInt16 pcBubbleSizeRatio = new UInt16();
    /**
    * An unsigned integer that specifies how the default size of the data points represents the value. 
    * MUST be ignored if the fBubbles field is 0. 
    * MUST be a value from the following table:
    * 1 = The area of the data point represents the value.
    * 2 = The width of the data point represents the value.
    */
    public UInt16 wBubbleSize = new UInt16();
    /**
    * A bit that specifies whether this chart group is a scatter chart group or bubble chart group. 
    * MUST be a value from the following table:
    * false = Scatter chart group
    * true = Bubble chart group
    */
    public boolean fBubbles;
    /**
    * A bit that specifies whether data points with negative values in the
    * chart group are shown on the chart. 
    * MUST be ignored if the fBubbles field is 0.
    */
    public boolean fShowNegBubbles;
    /**
    * A bit that specifies whether one or more data markers in a
    * scatter chart group or data points in a bubble chart group has shadows.
    */
    public boolean fHasShadow;
    public Scatter(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.pcBubbleSizeRatio = reader.readUInt16();
        this.wBubbleSize = reader.readUInt16();
        UInt16 flags = reader.readUInt16();
        this.fBubbles = Utils.BitmaskToBool(flags, 0x1);
        this.fShowNegBubbles = Utils.BitmaskToBool(flags, 0x2);
        this.fHasShadow = Utils.BitmaskToBool(flags, 0x4);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


