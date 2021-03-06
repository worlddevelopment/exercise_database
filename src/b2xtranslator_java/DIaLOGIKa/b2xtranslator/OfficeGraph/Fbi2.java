//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:43 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
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
/// <summary>
/// This record specifies the font information at the time the scalable font is added to the chart. <47>
/// </summary>
public class Fbi2  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.Fbi2;
    /**
    * An unsigned integer that specifies the font width, in twips, when the font was first applied.
    * 
    * MUST be greater than or equal to 0 and less than or equal to 0x7FFF.
    */
    public UInt16 dmixBasis = new UInt16();
    /**
    * An unsigned integer that specifies the font height, in twips, when the font was first applied.
    * 
    * MUST be greater than or equal to 0 and less than or equal to 0x7FFF.
    */
    public UInt16 dmiyBasis = new UInt16();
    /**
    * An unsigned integer that specifies the default font height in twips.
    * 
    * MUST be greater than or equal to 20 and less than or equal to 8180.
    */
    public UInt16 twpHeightBasis = new UInt16();
    /**
    * A Boolean that specifies the scale to use. The value MUST be one of the following values:
    * 
    * Value       Meaning
    * 0x0000      Scale by chart area
    * 0x0001      Scale by plot area
    */
    public boolean scab;
    public UInt16 ifnt = new UInt16();
    // TODO: implement FontIndex???
    public Fbi2(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.dmixBasis = reader.readUInt16();
        this.dmiyBasis = reader.readUInt16();
        this.twpHeightBasis = reader.readUInt16();
        this.scab = reader.readUInt16() == 0x0001;
        this.ifnt = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


