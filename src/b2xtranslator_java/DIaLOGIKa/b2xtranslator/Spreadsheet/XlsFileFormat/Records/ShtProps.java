//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:49 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
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
* This record specifies properties of a chart as defined by the Chart Sheet Substream ABNF.
*/
public class ShtProps  extends BiffRecord 
{
    public static final RecordType ID = RecordType.ShtProps;
    public enum EmptyCellPlotMode
    {
        PlotNothing,
        PlotAsZero,
        PlotAsInterpolated
    }
    /**
    * A bit that specifies whether series are automatically allocated for the chart.
    */
    public boolean fManSerAlloc;
    /**
    * A bit that specifies whether to plot visible cells only.
    */
    public boolean fPlotVisOnly;
    /**
    * A bit that specifies whether to size the chart with the window.
    */
    public boolean fNotSizeWith;
    /**
    * If fAlwaysAutoPlotArea is true then this field MUST be true.
    * If fAlwaysAutoPlotArea is false then this field MUST be ignored.
    */
    public boolean fManPlotArea;
    /**
    * A bit that specifies whether the default plot area dimension is used.
    * MUST be a value from the following table:
    */
    public boolean fAlwaysAutoPlotArea;
    /**
    * Specifies how the empty cells are plotted.
    */
    public EmptyCellPlotMode mdBlank = EmptyCellPlotMode.PlotNothing;
    public ShtProps(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        UInt16 flags = reader.readUInt16();
        this.fManSerAlloc = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1);
        this.fPlotVisOnly = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x2);
        this.fNotSizeWith = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x4);
        this.fManPlotArea = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x8);
        this.fAlwaysAutoPlotArea = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x10);
        this.mdBlank = (EmptyCellPlotMode)reader.readByte();
        if (length > 3)
        {
            // skip the last optional byte
            reader.readByte();
        }
         
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


