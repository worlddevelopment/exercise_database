//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:52 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.Run;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Structures.TxOLastRun;
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
* This structure specifies the formatting run information for the TxO
* record and zero or more Continue records immediately following.
*/
public class TxORuns   
{
    /**
    * An array of Run. Each Run specifies the formatting information for a text run.
    * formatRun.ich MUST be less than or equal to cchText of the preceding TxO record.
    * The number of elements in this array is (cbRuns of the preceding TxO record / 8 – 1).
    */
    public Run[] rgTxoRuns;
    /// <summary>
    /// A TxOLastRun that marks the end of the text run. This field is only present
    /// in the last Continue record following the TxO record. <174>
    /// </summary>
    public TxOLastRun lastRun;
    public TxORuns(IStreamReader reader, UInt16 cbRuns) throws Exception {
        int noOfRuns = (cbRuns / 8) - 1;
        this.rgTxoRuns = new Run[noOfRuns];
        for (int i = 0;i < noOfRuns;i++)
        {
            if (i == 1028 && BiffRecord.getNextRecordType(reader) == RecordType.Continue)
            {
                // yet another Continue record to be parsed -> skip record header
                UInt16 id = reader.readUInt16();
                UInt16 size = reader.readUInt16();
            }
             
            this.rgTxoRuns[i] = new Run(reader);
        }
        this.lastRun = new TxOLastRun(reader);
    }

}


