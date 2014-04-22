//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:40 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecord;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* This record specifies image data for a sheet background.
*/
public class BkHim  extends BiffRecord 
{
    public enum ImageFormat
    {
        __dummyEnum__0,
        __dummyEnum__1,
        __dummyEnum__2,
        __dummyEnum__3,
        __dummyEnum__4,
        __dummyEnum__5,
        __dummyEnum__6,
        __dummyEnum__7,
        __dummyEnum__8,
        Bitmap,
        __dummyEnum__9,
        __dummyEnum__10,
        __dummyEnum__11,
        __dummyEnum__12,
        Native
    }
    /**
    * Specifies the image format.
    */
    public ImageFormat cf = ImageFormat.Bitmap;
    /**
    * A signed integer that specifies the size of imageBlob in bytes. 
    * MUST be greater than or equal to 1.
    */
    public int lcb;
    /**
    * An array of bytes that specifies the image data for the given format.
    */
    public byte[] imageBlob;
    public BkHim(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        this.cf = (ImageFormat)reader.readInt16();
        reader.readBytes(2);
        // skip 2 bytes
        this.lcb = reader.readInt32();
        this.imageBlob = reader.readBytes(lcb);
    }

}


