//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:44 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
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
* This record specifies the layout of a picture attached to a picture-filled chart element.
*/
public class PicF  extends OfficeGraphBiffRecord 
{
    public enum LayoutType
    {
        __dummyEnum__0,
        Stretched,
        Stacked,
        StackedAndScaled
    }
    public static final GraphRecordNumber ID = GraphRecordNumber.PicF;
    /**
    * An unsigned integer that specifies the picture layout.
    * If this record is not located in the sequence of records that conforms to the SS rule,
    * then this value MUST be Stretched.
    */
    public LayoutType ptyp = LayoutType.Stretched;
    /**
    * A bit that specifies whether the picture covers the top and bottom fill areas of the data points.
    * The top and bottom fill areas of the data points are parallel to the floor in a 3-D plot area.
    * If a Chart3d record does not exist in the chart sheet substream, or if this record is not in a
    * sequence of records that conforms to the SS rule or if this record is in an SS rule that contains a
    * Chart3DBarShape with the riser field equal to 0x01, this field MUST be true.
    */
    public boolean fTopBottom;
    /**
    * A bit that specifies whether the picture covers the front and back fill areas of the data points on
    * a bar or column chart group. If a Chart3d record does not exist in the chart sheet substream,
    * or if this record is not in a sequence of records that conforms to the SS rule or if this
    * record is in an SS rule that contains a Chart3DBarShape with the riser field equal to 0x01,
    * this field MUST be true.
    */
    public boolean fBackFront;
    /**
    * A bit that specifies whether the picture covers the side fill areas of the data points on a
    * bar or column chart group. If a Chart3d record does not exist in the chart sheet substream,
    * or if this record is not in a sequence of records that conforms to the SS rule or if this record
    * is in an SS rule that contains a Chart3DBarShape with the riser field equal to 0x01,
    * this field MUST be true.
    */
    public boolean fSide;
    /**
    * An Xnum that specifies the number of units on the value axis in which to fit the entire picture.
    * The picture is scaled to fit within this number of units.
    * If the value of ptyp is not 0x0003, this field is undefined and MUST be ignored.
    */
    public double numScale;
    public PicF(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.ptyp = (LayoutType)reader.readInt16();
        reader.readBytes(2);
        //unused
        UInt16 flags = reader.readUInt16();
        // first 9 bits are reserved
        this.fTopBottom = Utils.BitmaskToBool(flags, 0x200);
        this.fBackFront = Utils.BitmaskToBool(flags, 0x400);
        this.fSide = Utils.BitmaskToBool(flags, 0x800);
        this.numScale = reader.readDouble();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


