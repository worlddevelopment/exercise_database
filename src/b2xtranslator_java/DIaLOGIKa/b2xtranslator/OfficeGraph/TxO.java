//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeGraph.TextRotation;
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
* This record specifies the text in a text box or a form control.
*/
public class TxO  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.TxO;
    public enum HorizontalAlignment
    {
        __dummyEnum__0,
        Left,
        Centered,
        Right,
        Justify,
        __dummyEnum__1,
        __dummyEnum__2,
        JustifyDistributed
    }
    public enum VerticalAlignment
    {
        __dummyEnum__0,
        Top,
        Middle,
        Bottom,
        Justify,
        __dummyEnum__1,
        __dummyEnum__2,
        JustifyDistributed
    }
    /**
    * Specifies the horizontal alignment.
    */
    public HorizontalAlignment hAlignment = HorizontalAlignment.Left;
    /**
    * Specifies the vertical alignment.
    */
    public VerticalAlignment vAlignment = VerticalAlignment.Top;
    /**
    * Specifies the orientation of the text within the object boundary.
    */
    public TextRotation rot = TextRotation.Custom;
    /**
    * An unsigned integer that specifies the number of characters in the text string
    * contained in the Continue records immediately following this record. 
    * MUST be less than or equal to 255.
    */
    public UInt16 cchText = new UInt16();
    /**
    * An unsigned integer that specifies the number of bytes of formatting run information in the
    * TxORuns structure contained in the Continue records following this record.
    * If cchText is 0, this value MUST be 0.
    * Otherwise the value MUST be greater than or equal to 16 and MUST be a multiple of 8.
    */
    public UInt16 cbRuns = new UInt16();
    /**
    * A FontIndex that specifies the font when cchText is 0.
    */
    public UInt16 ifntEmpty = new UInt16();
    public TxO(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        UInt16 flags = reader.readUInt16();
        this.hAlignment = (HorizontalAlignment)Utils.BitmaskToInt(flags, 0xE);
        this.vAlignment = (VerticalAlignment)Utils.BitmaskToInt(flags, 0x70);
        this.rot = (TextRotation)reader.readUInt16();
        reader.readBytes(6);
        // reserved
        this.cchText = reader.readUInt16();
        this.cbRuns = reader.readUInt16();
        this.ifntEmpty = reader.readUInt16();
        reader.readBytes(2);
        // reserved
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


