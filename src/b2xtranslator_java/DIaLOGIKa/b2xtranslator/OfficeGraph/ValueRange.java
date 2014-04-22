//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
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
* This record specifies the properties of a value axis.
*/
public class ValueRange  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.ValueRange;
    /**
    * An Xnum that specifies the minimum value of the value axis.
    * 
    * MUST be less than numMax. If the value of fAutoMin is 1, this field MUST be ignored.
    */
    public double numMin;
    /**
    * An Xnum that specifies the maximum value of the value axis.
    * 
    * MUST be greater than numMin. If the value of fAutoMax is 1, this field MUST be ignored.
    */
    public double numMax;
    /**
    * An Xnum that specifies the interval at which major tick marks and major gridlines are displayed.
    * 
    * MUST be greater than or equal to numMinor. If the value of fAutoMajor is 1, this field MUST be ignored.
    */
    public double numMajor;
    /**
    * An Xnum that specifies the interval at which minor tick marks and minor gridlines are displayed.
    * 
    * MUST be greater than or equal to zero. If the value of fAutoMinor is 1, this field MUST be ignored.
    */
    public double numMinor;
    /**
    * An Xnum that specifies at which value the other axes in the axis group cross this value axis.
    * 
    * If the value of fAutoCross is 1, this field MUST be ignored.
    */
    public double numCross;
    /**
    * A bit that specifies whether numMin is calculated automatically.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The value specified by numMin is used as the minimum value of the value axis.
    * 1           numMin is calculated such that the data point with the minimum value can be displayed in the plot area.
    */
    public boolean fAutoMin;
    /**
    * A bit that specifies whether numMax is calculated automatically.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The value specified by numMax is used as the maximum value of the value axis.
    * 1           numMax is calculated such that the data point with the maximum value can be displayed in the plot area.
    */
    public boolean fAutoMax;
    /**
    * A bit that specifies whether numMajor is calculated automatically.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The value specified by numMajor is used as the interval at which major tick marks and major gridlines are displayed.
    * 1           numMajor is calculated automatically.
    */
    public boolean fAutoMajor;
    /**
    * A bit that specifies whether numMinor is calculated automatically.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The value specified by numMinor is used as the interval at which minor tick marks and minor gridlines are displayed.
    * 1           numMinor is calculated automatically.
    */
    public boolean fAutoMinor;
    /**
    * A bit that specifies whether numCross is calculated automatically.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The value specified by numCross is used as the point at which the
    * other axes in the axis group cross this value axis.
    * 1           numCross is calculated so that the crossing point is displayed in the plot area.
    */
    public boolean fAutoCross;
    /**
    * A bit that specifies whether the value axis has a logarithmic scale.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The scale of the value axis is linear.
    * 1           The scale of the value axis is logarithmic in base 10.
    */
    public boolean fLog;
    /**
    * A bit that specifies whether the values on the value axis are displayed in reverse order.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           Values are displayed from smallest-to-largest from left-to-right or
    * bottom-to-top, respectively, depending on the orientation of the axis.
    * 1           The values are displayed in reverse order, meaning largest-to-smallest
    * from left-to-right or bottom-to-top, respectively.
    */
    public boolean fReversed;
    /**
    * A bit that specifies whether the other axes in the axis group cross this value axis at the maximum value.
    * 
    * MUST be one of the following:
    * 
    * Value       Meaning
    * 0           The other axes in the axis group cross this value axis at the value specified by numCross.
    * 1           The other axes in the axis group cross the value axis at the maximum value.
    * If fMaxCross is 1, then both fAutoCross and numCross MUST be ignored.
    */
    public boolean fMaxCross;
    public ValueRange(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.numMin = reader.readDouble();
        this.numMax = reader.readDouble();
        this.numMajor = reader.readDouble();
        this.numMinor = reader.readDouble();
        this.numCross = reader.readDouble();
        UInt16 flags = reader.readUInt16();
        this.fAutoMin = Utils.BitmaskToBool(flags, 0x0001);
        this.fAutoMax = Utils.BitmaskToBool(flags, 0x0002);
        this.fAutoMajor = Utils.BitmaskToBool(flags, 0x0004);
        this.fAutoMinor = Utils.BitmaskToBool(flags, 0x0008);
        this.fAutoCross = Utils.BitmaskToBool(flags, 0x0010);
        this.fLog = Utils.BitmaskToBool(flags, 0x0020);
        this.fReversed = Utils.BitmaskToBool(flags, 0x0040);
        this.fMaxCross = Utils.BitmaskToBool(flags, 0x0080);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


