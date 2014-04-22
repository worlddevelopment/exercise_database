//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.BackgroundMode;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.HorizontalAlignment;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeGraph.ReadingOrder;
import DIaLOGIKa.b2xtranslator.OfficeGraph.VerticalAlignment;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor;
import DIaLOGIKa.b2xtranslator.Tools.RGBColor.ByteOrder;
import DIaLOGIKa.b2xtranslator.Tools.Utils;

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
* This record specifies the properties of an attached label and specifies the beginning of a
* collection of records as defined by the chart sheet substream ABNF.
* This collection of records specifies an attached label.
*/
public class Text  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.Text;
    /**
    * Specifies the horizontal alignment of the text.
    */
    public HorizontalAlignment at = HorizontalAlignment.Left;
    /**
    * Specifies the vertical alignment of the text.
    */
    public VerticalAlignment vat = VerticalAlignment.Top;
    /**
    * Specifies the display mode of the background of the text.
    */
    public BackgroundMode wBkgMode = BackgroundMode.Transparent;
    /**
    * Specifies the color of the text.
    */
    public RGBColor rgbText;
    /// <summary>
    /// A signed integer that specifies the horizontal position of the text,
    /// relative to the upper-left of the chart area, in SPRC.<br/>
    /// MUST be ignored when this record is preceded by a DefaultText record or is followed by a Pos record; <br/>
    /// MUST be greater than or equal to 0, and less than or equal to 32767.<br/>
    /// SHOULD <65> be less than or equal to 4000.
    /// </summary>
    public int x;
    /// <summary>
    /// A signed integer that specifies the vertical position of the text, relative
    /// to the upper-left of the chart area, in SPRC. <br/>
    /// MUST be ignored when this record is preceded by a DefaultText record or is followed by a Pos record;<br/>
    /// MUST be greater than or equal to 0, and less than or equal to 32767. <br/>
    /// SHOULD <66> be less than or equal to 4000.
    /// </summary>
    public int y;
    /// <summary>
    /// A signed integer that specifies the horizontal size of the text,
    /// relative to the chart area, in SPRC. <br/>
    /// MUST be ignored when this record is followed by a Pos record; <br/>
    /// MUST be greater than or equal to 0, and less than or equal to 32767.<br/>
    /// SHOULD <67> be less than or equal to 4000.
    /// </summary>
    public int dx;
    /// <summary>
    /// A signed integer that specifies the vertical size of the text, relative to the chart area, in SPRC.<br/>
    /// MUST be ignored when this record is followed by a Pos record;<br/>
    /// MUST be greater than or equal to 0, and less than or equal to 32767.<br/>
    /// SHOULD <68> be less than or equal to 4000.
    /// </summary>
    public int dy;
    /**
    * A bit that specifies whether the foreground text color is determined automatically.
    */
    public boolean fAutoColor;
    /**
    * A bit that specifies whether the text is attached to a legend key.
    */
    public boolean fShowKey;
    /**
    * A bit that specifies whether the value, or the vertical value on bubble or scatter chart groups, is displayed in the data label.
    */
    public boolean fShowValue;
    /**
    * A bit that specifies whether the text value of this text field is automatically generated and unchanged.
    */
    public boolean fAutoText;
    /**
    * A bit that specifies whether the properties of this text field are automatically generated and unchanged.
    */
    public boolean fGenerated;
    /**
    * A bit that specifies whether this data label has been deleted by the user.
    */
    public boolean fDeleted;
    /**
    * A bit that specifies whether the background color is determined automatically.
    */
    public boolean fAutoMode;
    /**
    * A bit that specifies whether the category (3) name and the value,
    * represented as a percentage of the sum of the values of the series the data label is
    * associated with, are displayed in the data label.
    */
    public boolean fShowLabelAndPerc;
    /**
    * A bit that specifies whether the value, represented as a percentage of the sum of the
    * values of the series the data label is associated with, is displayed in the data label.
    */
    public boolean fShowPercent;
    /**
    * A bit that specifies whether the bubble size is displayed in the data label.
    */
    public boolean fShowBubbleSizes;
    /**
    * A bit that specifies whether the category (3), or the horizontal value on bubble or
    * scatter chart groups, is displayed in the data label on a non-area chart group,
    * or the series name is displayed in the data label on an area chart group.
    */
    public boolean fShowLabel;
    /**
    * Specifies the color of the text.
    */
    public UInt16 icvText = new UInt16();
    /**
    * Specifies the data label positioning of the text, relative to the graph object item the text is attached to.
    */
    public int dlp;
    /**
    * An unsigned integer that specifies the reading order of the text.
    * MUST be a value from the following table:
    */
    public ReadingOrder iReadingOrder = ReadingOrder.Complex;
    /**
    * An unsigned integer that specifies the text rotation. 
    * MUST be a value from the following table:
    * 0 to 90 = Text rotated 0 to 90 degrees counter-clockwise
    * 91 to 180 = Text rotated 1 to 90 degrees clockwise (angle is trot – 90)
    * 255 = Text top-to-bottom with letters upright
    */
    public UInt16 trot = new UInt16();
    public Text(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.at = (HorizontalAlignment)reader.readByte();
        this.vat = (VerticalAlignment)reader.readByte();
        this.wBkgMode = (BackgroundMode)reader.readUInt16();
        this.rgbText = new RGBColor(reader.readInt32(),ByteOrder.RedFirst);
        this.x = reader.readInt32();
        this.y = reader.readInt32();
        this.dx = reader.readInt32();
        this.dy = reader.readInt32();
        UInt16 flags = reader.readUInt16();
        this.fAutoColor = Utils.BitmaskToBool(flags, 0x1);
        this.fShowKey = Utils.BitmaskToBool(flags, 0x2);
        this.fShowValue = Utils.BitmaskToBool(flags, 0x4);
        // 0x8 is unused
        this.fAutoText = Utils.BitmaskToBool(flags, 0x10);
        this.fGenerated = Utils.BitmaskToBool(flags, 0x20);
        this.fDeleted = Utils.BitmaskToBool(flags, 0x40);
        this.fAutoMode = Utils.BitmaskToBool(flags, 0x80);
        // 0x100, 0x200, 0x400 are unused
        this.fShowLabelAndPerc = Utils.BitmaskToBool(flags, 0x800);
        this.fShowPercent = Utils.BitmaskToBool(flags, 0x1000);
        this.fShowBubbleSizes = Utils.BitmaskToBool(flags, 0x2000);
        this.fShowLabel = Utils.BitmaskToBool(flags, 0x4000);
        //0x8000 is reserved
        this.icvText = reader.readUInt16();
        UInt16 values = reader.readUInt16();
        this.dlp = Utils.BitmaskToInt(values, 0xF);
        this.iReadingOrder = (ReadingOrder)Utils.BitmaskToInt(values, 0xC000);
        this.trot = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


