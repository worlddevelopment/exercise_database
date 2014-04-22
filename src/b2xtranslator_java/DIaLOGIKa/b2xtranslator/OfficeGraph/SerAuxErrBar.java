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
* This record specifies properties of an error bar.
*/
public class SerAuxErrBar  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.SerAuxErrBar;
    public enum ErrorBarDirection
    {
        __dummyEnum__0,
        HorizontalPositive,
        HorizontalNegative,
        VerticalPositive,
        VerticalNegative
    }
    public enum ErrorAmoutType
    {
        __dummyEnum__0,
        Percentage,
        FixedValue,
        StandardDeviation,
        __dummyEnum__1,
        StandardError
    }
    /**
    * Specifies the direction of the error bars.
    */
    public ErrorBarDirection sertm = ErrorBarDirection.HorizontalPositive;
    /**
    * Specifies the error amount type of the error bars.
    */
    public ErrorAmoutType ebsrc = ErrorAmoutType.Percentage;
    /**
    * A Boolean that specifies whether the error bars are T-shaped.
    */
    public boolean fTeeTop;
    /**
    * An Xnum that specifies the fixed value, percentage, or number of standard deviations for the error bars.
    * If ebsrc is StandardError this MUST be ignored.
    */
    public double numValue;
    public SerAuxErrBar(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.sertm = (ErrorBarDirection)reader.readByte();
        this.ebsrc = (ErrorAmoutType)reader.readByte();
        this.fTeeTop = Utils.byteToBool(reader.readByte());
        reader.readByte();
        // reserved
        this.numValue = reader.readDouble();
        reader.readBytes(2);
        //unused
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


