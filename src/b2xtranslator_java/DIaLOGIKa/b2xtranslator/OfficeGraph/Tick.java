//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:45 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.BackgroundMode;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeGraph.ReadingOrder;
import DIaLOGIKa.b2xtranslator.OfficeGraph.TextRotation;
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
* This record specifies the attributes of the axis labels, major tick marks, and minor tick marks associated with an axis.
*/
public class Tick  extends OfficeGraphBiffRecord 
{
    public enum MarkLocation
    {
        None,
        Inside,
        Outside,
        Crossing
    }
    public enum MarkLabelLocation
    {
        Complex,
        Low,
        High,
        NextToAxis
    }
    public static final GraphRecordNumber ID = GraphRecordNumber.Tick;
    /**
    * Specifies the location of major tick marks.
    */
    public MarkLocation tktMajor = MarkLocation.None;
    /**
    * Specifies the location of minor tick marks.
    */
    public MarkLocation tktMinor = MarkLocation.None;
    /**
    * Specifies the location of axis labels.
    */
    public MarkLabelLocation tlt = MarkLabelLocation.Complex;
    /**
    * Specifies the display mode of the background of the text of the axis labels.
    */
    public BackgroundMode wBkgMode = BackgroundMode.Transparent;
    /**
    * Specifies the color of the text for the axis labels.
    */
    public RGBColor rgb;
    /**
    * A bit that specifies if the foreground text color of the axis labels is determined automatically.
    */
    public boolean fAutoCo;
    /**
    * A bit that specifies if the background color of the axis label is determined automatically.
    */
    public boolean fAutoMode;
    /**
    * Specifies text rotation of the axis labels. 
    * If Custom use value from trot.
    */
    public TextRotation rot = TextRotation.Custom;
    /**
    * A bit that specifies whether the text rotation of the axis label is determined automatically.
    */
    public boolean fAutoRot;
    /**
    * specifies the reading order of the axis labels.
    */
    public ReadingOrder iReadingOrder = ReadingOrder.Complex;
    /**
    * Specifies the color of the text in the color palette.
    */
    public UInt16 icv = new UInt16();
    /**
    * MUST be a value from the following table:
    * 0 to 90 = Text rotated 0 to 90 degrees counterclockwise
    * 91 to 180 = Text rotated 1 to 90 degrees clockwise (angle is trot – 90)
    * 255 = Text top-to-bottom with letters upright
    */
    public UInt16 trot = new UInt16();
    public Tick(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.tktMajor = (MarkLocation)reader.readByte();
        this.tktMinor = (MarkLocation)reader.readByte();
        this.tlt = (MarkLabelLocation)reader.readByte();
        this.wBkgMode = (BackgroundMode)reader.readByte();
        this.rgb = new RGBColor(reader.readInt32(),ByteOrder.RedFirst);
        reader.readBytes(16);
        // rerserved
        UInt16 flags = reader.readUInt16();
        this.fAutoCo = Utils.BitmaskToBool(flags, 0x1);
        this.fAutoMode = Utils.BitmaskToBool(flags, 0x2);
        this.rot = (TextRotation)Utils.BitmaskToInt(flags, 0x1C);
        this.fAutoRot = Utils.BitmaskToBool(flags, 0x10);
        this.iReadingOrder = (ReadingOrder)Utils.BitmaskToInt(flags, 0xC000);
        this.icv = reader.readUInt16();
        this.trot = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


