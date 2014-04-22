//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:45 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.LineFormat;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;

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
* This record specifies the appearance of a line.
*/
public class LineFormat  extends BiffRecord implements IVisitable
{
    public enum LineStyle
    {
        Solid,
        Dash,
        Dot,
        DashDot,
        DashDotDot,
        None,
        DarkGrayPattern,
        MediumGrayPattern,
        LightGrayPattern
    }
    public enum LineWeight
    {
        Hairline,
        Narrow,
        Medium,
        Wide
    }
    public static final RecordType ID = RecordType.LineFormat;
    /**
    * Specifies the color of the line.
    */
    public RGBColor rgb;
    /**
    * Specifies the style of the line.
    */
    public LineStyle lns = LineStyle.Solid;
    /**
    * Specifies the thickness of the line.
    */
    public LineWeight we = LineWeight.Hairline;
    /**
    * A bit that specifies whether the line has default formatting.
    * If the value is false, the line has formatting as specified by lns, we, and icv.
    * If the value is true, lns, we, icv, and rgb MUST be ignored and default values are used as specified in the following table:
    * lns = Solid
    * we = Narrow
    * icv = 0x004D
    * rgb = Match the default color used for icv
    */
    public boolean fAuto;
    /**
    * A bit that specifies whether the axis line is displayed.
    */
    public boolean fAxisOn;
    /**
    * A bit that specifies whether icv equals 0x004D.
    * If the value is true, icv MUST equal 0x004D. 
    * If the value is false, icv MUST NOT equal 0x004D.
    */
    public boolean fAutoCo;
    /**
    * An unsigned integer that specifies the color of the line.
    * The value SHOULD be an IcvChart value. 
    * The value MUST be an IcvChart value, 0x0040, or 0x0041. 
    * The color MUST match the color specified by rgb.
    */
    public UInt16 icv = new UInt16();
    public LineFormat(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.rgb = new RGBColor(reader.readInt32(),ByteOrder.RedFirst);
        this.lns = (LineStyle)reader.readInt16();
        this.we = (LineWeight)reader.readInt16();
        UInt16 flags = reader.readUInt16();
        this.fAuto = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x1);
        // 0x2 is reserved
        this.fAxisOn = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x4);
        this.fAutoCo = DIaLOGIKa.b2xtranslator.Tools.Utils.BitmaskToBool(flags, 0x8);
        this.icv = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<LineFormat>)mapping).apply(this);
    }

}


