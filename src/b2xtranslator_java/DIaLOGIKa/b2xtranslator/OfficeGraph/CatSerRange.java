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
* This record specifies the properties of a category (3) axis, a date axis, or a series axis.
*/
public class CatSerRange  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.CatSerRange;
    /**
    * A signed integer that specifies where the value axis crosses this axis, based on the following table.
    * 
    * If fMaxCross is set to 1, the value this field MUST be ignored. MUST be a value from the following table:
    * 
    * Axis Type                 catCross Range
    * Category (3) axis         This field specifies the category (3) at which the value axis crosses.
    * For example, if this field is 2, the value axis crosses this axis at the second
    * category (3) on this axis. MUST be greater than or equal to 1 and less than or equal to 31999.
    * 
    * Series axis               MUST be 0.
    * 
    * Date axis                 catCross MUST be equal to the value given by the following formula:
    * catCross = catCrossDate – catMin + 1
    * Where catCrossDate is the catCrossDate field of the AxcExt record
    * and catMin is the catMin field of the AxcExt record.
    */
    public short catCross;
    /**
    * A signed integer that specifies the interval between axis labels on this axis.
    * 
    * MUST be greater than or equal to 1 and less than or equal to 31999. MUST be ignored for a date axis.
    */
    public short catLabel;
    /**
    * A signed integer that specifies the interval at which major tick marks and minor tick
    * marks are displayed on the axis. Major tick marks and minor tick marks that would
    * have been visible are hidden unless they are located at a multiple of this field.
    * 
    * MUST be greater than or equal to 1, and less than or equal to 31999. MUST be ignored for a date axis.
    */
    public short catMark;
    /**
    * A bit that specifies whether the value axis crosses this axis between major tick marks.
    */
    public boolean fBetween;
    /**
    * A bit that specifies whether the value axis crosses this axis at the last category (3), the last series, or the maximum date.
    */
    public boolean fMaxCross;
    /**
    * A bit that specifies whether the axis is displayed in reverse order.
    */
    public boolean fReverse;
    public CatSerRange(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.catCross = reader.readInt16();
        this.catLabel = reader.readInt16();
        this.catMark = reader.readInt16();
        UInt16 flags = reader.readUInt16();
        this.fBetween = Utils.BitmaskToBool(flags, 0x0001);
        this.fMaxCross = Utils.BitmaskToBool(flags, 0x0002);
        this.fReverse = Utils.BitmaskToBool(flags, 0x0004);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


