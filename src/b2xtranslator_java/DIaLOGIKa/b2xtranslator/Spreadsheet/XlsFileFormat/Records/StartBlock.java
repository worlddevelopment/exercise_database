//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:49 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.FrtHeaderOld;
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
public class StartBlock  extends BiffRecord 
{
    public enum ObjectType
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
    public static final RecordType ID = RecordType.StartBlock;
    public FrtHeaderOld frtHeaderOld;
    public ObjectType iObjectKind = ObjectType.AxisGroup;
    /**
    * An unsigned integer that specifies the context of the object.
    * This value further specifies the object specified in iObjectKind.
    */
    public UInt16 iObjectContext = new UInt16();
    /**
    * An unsigned integer that specifies additional information about the context
    * of the object, along with iObjectContext, iObjectInstance2 and iObjectKind.
    */
    public UInt16 iObjectInstance1 = new UInt16();
    /**
    * An unsigned integer that specifies more information about the object context,
    * along with iObjectContext, iObjectInstance1 and iObjectKind.
    */
    public UInt16 iObjectInstance2 = new UInt16();
    public StartBlock(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeaderOld = new FrtHeaderOld(reader);
        this.iObjectKind = (ObjectType)reader.readUInt16();
        this.iObjectContext = reader.readUInt16();
        this.iObjectInstance1 = reader.readUInt16();
        this.iObjectInstance2 = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


