//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:51 AM
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
/**
* This record specifies properties of the value multiplier for a value axis
* and specifies the beginning of a collection of records as defined by the
* Chart Sheet Substream ABNF.
* 
* The collection of records specifies a display units label.
*/
public class YMult  extends BiffRecord 
{
    public static final RecordType ID = RecordType.YMult;
    public enum AxisMultiplier
    {
        Custom,
        Factor1,
        Factor100,
        Factor1000,
        Factor10000,
        Factor100000,
        Factor1000000,
        Factor10000000,
        Factor100000000,
        Factor1000000000,
        Factor1000000000000
    }
    /**
    * An FrtHeaderOld. The frtHeaderOld.rt field MUST be 0x0857.
    */
    public FrtHeaderOld frtHeaderOld;
    /**
    * A signed integer that specifies the axis multiplier type.
    */
    public AxisMultiplier axmid = AxisMultiplier.Custom;
    /**
    * An Xnum that specifies a custom multiplier. The value on the axis will be
    * multiplied by the value of this field.
    * 
    * MUST be greater than 0.0. If axmid is set to a value other than 0xFFFF, this field is ignored.
    */
    public double numLabelMultiplier;
    /**
    * A bit that specifies whether the display units label is displayed.
    */
    public boolean fAutoShowMultiplier;
    /**
    * A bit that specifies whether the display units label is currently being edited.
    */
    public boolean fBeingEdited;
    public YMult(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.frtHeaderOld = new FrtHeaderOld(reader);
        this.axmid = (AxisMultiplier)reader.readInt16();
        this.numLabelMultiplier = reader.readDouble();
        UInt16 flags = reader.readUInt16();
        this.fAutoShowMultiplier = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0002);
        this.fBeingEdited = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x0004);
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


