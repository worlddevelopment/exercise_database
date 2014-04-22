//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:43 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.FrtHeaderOld;
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
/**
* This record specifies the end of a collection of records.
* Future records contained in this collection specify saved features to allow
* applications that do not support the feature to preserve the information.
* 
* This record MUST have an associated StartBlock record. StartBlock and EndBlock
* pairs can be nested. Up to 100 levels of blocks can be nested.
* 
* EndBlock records MUST be written according to the following rules:
* 
* 1. If there exists a StartBlock record with iObjectKind equal to 0x0002
* without a matching EndBlock, then the matching EndBlock record
* MUST be written immediately before writing the End record of the current AttachedLabel.
* 
* 2. If there exists a StartBlock record with iObjectKind equal to 0x0004 without a
* matching EndBlock, then the matching EndBlock record MUST be written immediately
* before writing the End record of the current Axis.
* 
* 3. If there exists a StartBlock record with iObjectKind equal to 0x0005 without a
* matching EndBlock, then the matching EndBlock record MUST be written immediately
* before writing the End record of the current Chart Group.
* 
* 4. If there exists a StartBlock record with iObjectKind equal to 0x0000 without a
* matching EndBlock, then the matching EndBlock record MUST be written immediately
* before writing the End record of the current Axis Group.
* 
* 5. If there exists a StartBlock record with iObjectKind equal to 0x000D without a
* matching EndBlock, then the matching EndBlock record MUST be written immediately
* before writing the End record of the current Sheet.
*/
public class EndBlock  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.EndBlock;
    public enum ObjectKind
    {
        AxisGroup,
        __dummyEnum__0,
        AttachedLabel,
        __dummyEnum__1,
        Axis,
        ChartGroup,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        Sheet
    }
    /**
    * FrtHeaderOld. The frtHeaderOld.rt field MUST be 0x0853
    */
    public FrtHeaderOld frtHeaderOld;
    /**
    * An unsigned integer that specifies the type of object that is encompassed by the block.
    * 
    * MUST equal the iObjectKind field of the matching StartBlock record.
    */
    public ObjectKind iObjectKind = ObjectKind.AxisGroup;
    public EndBlock(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.frtHeaderOld = new FrtHeaderOld(reader);
        this.iObjectKind = (ObjectKind)reader.readUInt16();
        reader.readBytes(6);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


