//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:45 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.DataContainer.MergeCellData;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

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
/**
* This class extracts the data from a mergecell biffrecord
* 
* MERGECELLS: Merged Cells (E5h)
* This record stores all merged cells. Record Data
* Offset 	Field Name 	Size 	Contents
* 4 	cmcs 	2 	Count of REF structures
* 
* The REF structure has the following fields.
* Offset 	Field Name 	Size 	Contents
* 8 	rwFirst 	2 	The first row of the range associated with the record
* 10 	rwLast 	2 	The last row of the range associated with the record
* 12 	colFirst 	2 	The first column of the range associated with the record
* 14 	colLast 	2 	The last column of the range associated with the record
*/
public class MergeCells  extends BiffRecord 
{
    public static final RecordType ID = RecordType.MergeCells;
    /**
    * List with datarecords
    */
    public CSList<MergeCellData> mergeCellDataList;
    /**
    * Count REF structures
    */
    public UInt16 cmcs = new UInt16();
    /**
    * Ctor
    * 
    *  @param reader 
    *  @param id 
    *  @param length
    */
    public MergeCells(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.mergeCellDataList = new CSList<MergeCellData>();
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        this.cmcs = this.Reader.ReadUInt16();
        for (int i = 0;i < cmcs;i++)
        {
            MergeCellData mcd = new MergeCellData();
            mcd.rwFirst = this.Reader.ReadUInt16();
            mcd.rwLast = this.Reader.ReadUInt16();
            mcd.colFirst = this.Reader.ReadUInt16();
            mcd.colLast = this.Reader.ReadUInt16();
            this.mergeCellDataList.add(mcd);
        }
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


