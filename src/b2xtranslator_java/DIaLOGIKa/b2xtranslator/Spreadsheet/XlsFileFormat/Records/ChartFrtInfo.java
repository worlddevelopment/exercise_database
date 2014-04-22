//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:41 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.CFrtId;
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
/// <summary>
/// This record specifies the versions of the application that originally created
/// and last saved the file, and the Future Record IDs that are used in the file.
/// This property was introduced by a version of the application <34> as a Chart Future Record for charts.
///
/// In a file written by some versions of the application <35>, this record appears
/// before the end of the Chart record collection and before any other Future Record
/// in the record stream. This record does not exist in a file created by certain
/// versions of the application <36>, but appears after the End record of the Chart
/// record collection in a file updated by other versions of the application <37>,
/// in which case the verWriter field MUST be a certain version of the application <38>
/// regardless of the actual value in the record.
///
/// This record MUST be immediately precede the first chart-specific future record,
/// which is a record that has a record number greater than or equal to 2048 and less
/// than or equal to 2303 according to Record Enumeration.
/// </summary>
public class ChartFrtInfo  extends BiffRecord 
{
    public enum OriginatorVersion
    {
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        Excel2000,
        Excel2002Excel2003,
        __dummyEnum__9,
        Excel2007
    }
    public enum WriterVersion
    {
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        Excel2000,
        Excel2002Excel2003,
        __dummyEnum__9,
        Excel2007
    }
    public static final RecordType ID = RecordType.ChartFrtInfo;
    public FrtHeaderOld frtHeaderOld;
    public OriginatorVersion verOriginator = OriginatorVersion.Excel2000;
    public WriterVersion verWriter = WriterVersion.Excel2000;
    public UInt16 cCFRTID = new UInt16();
    public CFrtId[] rgCFRTID;
    public ChartFrtInfo(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeaderOld = new FrtHeaderOld(reader);
        this.verOriginator = (OriginatorVersion)reader.readByte();
        this.verWriter = (WriterVersion)reader.readByte();
        this.cCFRTID = reader.readUInt16();
        this.rgCFRTID = new CFrtId[cCFRTID];
        for (int i = 0;i < this.cCFRTID;i++)
        {
            this.rgCFRTID[i] = new CFrtId(reader);
        }
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


