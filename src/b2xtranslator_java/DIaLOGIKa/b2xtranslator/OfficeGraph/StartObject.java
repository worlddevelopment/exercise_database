//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
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
public class StartObject  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.StartObject;
    public FrtHeaderOld frtHeaderOld;
    /**
    * An unsigned integer that specifies the type of object that is encompassed by the block.
    * MUST be a value from the following table:
    * 0x0010
    * 0x0011
    * 0x0012
    */
    public UInt16 iObjectKind = new UInt16();
    /**
    * An unsigned integer that specifies the object context.
    * MUST be 0x0000.
    */
    public UInt16 iObjectContext = new UInt16();
    /**
    * An unsigned integer that specifies additional information about the context of the object,
    * along with iObjectContext, iObjectInstance2 and iObjectKind.
    * This field MUST equal 0x0000 if iObjectKind equals 0x0010 or 0x0012.
    * MUST be a value from the following table if iObjectKind equals 0x0011:
    * 0x0008 = Specifies the application version. <60>
    * 0x0009 = Specifies the application version. <61>
    * 0x000A = Specifies the application version. <62>
    * 0x000B = Specifies the application version. <63>
    * 0x000C = Specifies the application version. <64>
    */
    public UInt16 iObjectInstance1 = new UInt16();
    /**
    * An unsigned integer that specifies more information about the object context,
    * along with iObjectContext, iObjectInstance1 and iObjectKind. 
    * This field MUST equal 0x0000.
    */
    public UInt16 iObjectInstance2 = new UInt16();
    public StartObject(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.frtHeaderOld = new FrtHeaderOld(reader);
        this.iObjectKind = reader.readUInt16();
        this.iObjectContext = reader.readUInt16();
        this.iObjectInstance1 = reader.readUInt16();
        this.iObjectInstance2 = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


