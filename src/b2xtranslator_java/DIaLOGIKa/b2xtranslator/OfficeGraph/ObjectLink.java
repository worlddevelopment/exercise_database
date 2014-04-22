//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:44 AM
//

package DIaLOGIKa.b2xtranslator.OfficeGraph;

import CS2JNet.JavaSupport.Unsupported;
import DIaLOGIKa.b2xtranslator.OfficeGraph.GraphRecordNumber;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecord;
import DIaLOGIKa.b2xtranslator.OfficeGraph.OfficeGraphBiffRecordAttribute;
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
* This record specifies an object on a chart, or the entire chart, to which Text is linked.
*/
public class ObjectLink  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.ObjectLink;
    /**
    * An unsigned integer that specifies the object that the Text is linked to. 
    * MUST be a value from the following table:
    * 0x0001 = Entire chart.
    * 0x0002 = Value axis, or vertical value axis on bubble and scatter chart groups.
    * 0x0003 = category (3) axis, or horizontal value axis on bubble and scatter chart groups.
    * 0x0004 = Series or data points.
    * 0x0007 = Series axis.
    * 0x000C = Display units labels of an axis.
    */
    public UInt16 wLinkObj = new UInt16();
    /**
    * An unsigned integer that specifies the zero-based index into a Series record in the collection
    * of Series records in the current chart sheet substream.
    * Each referenced Series record specifies a series for the chart group to which the Text is linked.
    * When the wLinkObj field is 4, MUST be less than or equal to 254.
    * When the wLinkObj field is not 4, MUST be zero, and MUST be ignored.
    */
    public UInt16 wLinkVar1 = new UInt16();
    /**
    * An unsigned integer that specifies the zero-based index into the category (3) within the series
    * specified by wLinkVar1, to which the Text is linked.
    * When the wLinkObj field is 4, if the Text is linked to a series instead of a single data point,
    * the value MUST be 0xFFFF; if the Text is linked to a data point,
    * the value MUST be less than or equal to 31999.
    * When the wLinkObj field is not 4, MUST be zero, and MUST be ignored.
    */
    public UInt16 wLinkVar2 = new UInt16();
    public ObjectLink(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.wLinkObj = reader.readUInt16();
        this.wLinkVar1 = reader.readUInt16();
        this.wLinkVar2 = reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


