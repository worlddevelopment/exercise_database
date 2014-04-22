//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:43 AM
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
* This record specifies the text elements that are formatted using the
* information specified by the Text record immediately following this record.
*/
public class DefaultText  extends OfficeGraphBiffRecord 
{
    public static final GraphRecordNumber ID = GraphRecordNumber.DefaultText;
    public enum DefaultTextType
    {
        NoShowPercentNoShowValue,
        ShowPercentNoShowValue,
        ScalableNoFontInfo,
        ScalableFontInfo
    }
    /**
    * An unsigned integer that specifies the text elements that are formatted using
    * the position and appearance information specified by the Text record
    * immediately following this record.
    * 
    * If this record is located in the sequence of records that conforms to the CRT
    * rule as specified by the Chart Sheet Substream ABNF, this record MUST be 0x0000 or 0x0001.
    * 
    * If this record is not located in the CRT rule as specified by the Chart Sheet
    * Substream ABNF, this record MUST be 0x0002 or 0x0003.
    * 
    * MUST be a value from the following table:
    * 
    * Value       Meaning
    * 0x0000      Format all Text records in the chart group where fShowPercent equals 0 or fShowValue equals 0.
    * 0x0001      Format all Text records in the chart group where fShowPercent equals 1 or fShowValue equals 1.
    * 0x0002      Format all Text records in the chart where the value of fScalable of the associated FontInfo structure equals 0.
    * 0x0003      Format all Text records in the chart where the value of fScalable of the associated FontInfo structure equals 1.
    */
    public DefaultTextType defaultTextId = DefaultTextType.NoShowPercentNoShowValue;
    public DefaultText(IStreamReader reader, GraphRecordNumber id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.getId() == ID);
        // initialize class members from stream
        this.defaultTextId = (DefaultTextType)reader.readUInt16();
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.getOffset() + this.getLength() == Unsupported.throwUnsupported("this.getReader().getBaseStream().Position"));
    }

}


