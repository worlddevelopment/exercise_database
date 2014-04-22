//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:49 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ExcelHelperClass;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Ptg.AbstractPtg;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class ShrFmla  extends BiffRecord 
{
    public static final RecordType ID = RecordType.ShrFmla;
    /**
    * Rownumber
    */
    public UInt16 rwFirst = new UInt16();
    /**
    * Rownumber
    */
    public UInt16 rwLast = new UInt16();
    /**
    * Colnumber
    */
    public UInt16 colFirst = new UInt16();
    /**
    * Colnumber
    */
    public UInt16 colLast = new UInt16();
    /**
    * length of the formular data !!
    */
    public UInt16 cce = new UInt16();
    /**
    * LinkedList with the Ptg records !!
    */
    public Stack<AbstractPtg> ptgStack = new Stack<AbstractPtg>();
    public ShrFmla(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.rwFirst = reader.readUInt16();
        this.rwLast = reader.readUInt16();
        this.colFirst = (UInt16)reader.readByte();
        this.colLast = (UInt16)reader.readByte();
        // read two unnessesary bytes
        reader.readUInt16();
        this.cce = reader.readUInt16();
        this.ptgStack = new Stack<AbstractPtg>();
        // reader.ReadBytes(this.cce);
        long oldStreamPosition = this.Reader.BaseStream.Position;
        try
        {
            this.ptgStack = ExcelHelperClass.getFormulaStack(this.Reader, this.cce);
        }
        catch (Exception ex)
        {
            this.Reader.BaseStream.Seek(oldStreamPosition, System.IO.SeekOrigin.Begin);
            this.Reader.BaseStream.Seek(this.cce, System.IO.SeekOrigin.Current);
            TraceLogger.error(ex.getStackTrace().toString());
        }

        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


