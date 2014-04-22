//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:34 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.FrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.GelFrameSequence;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AreaFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Begin;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.End;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.Frame;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.ShapePropsSequence;
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
public class FrameSequence  extends BiffRecordSequence implements IVisitable
{
    public Frame Frame;
    public Begin Begin;
    public LineFormat LineFormat;
    public AreaFormat AreaFormat;
    public GelFrameSequence GelFrameSequence;
    public ShapePropsSequence ShapePropsSequence;
    public End End;
    public FrameSequence(IStreamReader reader) throws Exception {
        super(reader);
        // FRAME = Frame Begin LineFormat AreaFormat [GELFRAME] [SHAPEPROPS] End
        // Frame
        this.Frame = (Frame)BiffRecord.readRecord(reader);
        // Begin
        this.Begin = (Begin)BiffRecord.readRecord(reader);
        // LineFormat
        this.LineFormat = (LineFormat)BiffRecord.readRecord(reader);
        // AreaFormat
        this.AreaFormat = (AreaFormat)BiffRecord.readRecord(reader);
        // [GELFRAME]
        if (BiffRecord.getNextRecordType(reader) == RecordType.GelFrame)
        {
            this.GelFrameSequence = new GelFrameSequence(reader);
        }
         
        // [SHAPEPROPS]
        if (BiffRecord.getNextRecordType(reader) == RecordType.ShapePropsStream)
        {
            this.ShapePropsSequence = new ShapePropsSequence(reader);
        }
         
        // End
        this.End = (End)BiffRecord.readRecord(reader);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<FrameSequence>)mapping).apply(this);
    }

}


