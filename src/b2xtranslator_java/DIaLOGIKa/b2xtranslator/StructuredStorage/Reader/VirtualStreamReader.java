//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
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
public class VirtualStreamReader  extends BinaryReader implements IStreamReader
{
    /**
    * Ctor
    * 
    * 
    *  @param stream
    */
    public VirtualStreamReader(VirtualStream stream) throws Exception {
        super(stream);
    }

    /**
    * Second constructor to create a StreamReader with a MemoryStream.
    * 
    *  @param stream
    */
    public VirtualStreamReader(MemoryStream stream) throws Exception {
        super(stream);
    }

    /**
    * Reads bytes from the current position in the virtual stream.
    * The number of bytes to read is determined by the length of the array.
    * 
    *  @param buffer Array which will contain the read bytes after successful execution.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the length of the array if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] buffer) throws Exception {
        return getBaseStream().read(buffer,0,buffer.length);
    }

    /**
    * Reads bytes from the current position in the virtual stream.
    * 
    *  @param buffer Array which will contain the read bytes after successful execution.
    *  @param count Number of bytes to read.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] buffer, int count) throws Exception {
        return getBaseStream().read(buffer,0,count);
    }

    /**
    * Reads count bytes from the current stream into a byte array and advances
    * the current position by count bytes.
    * 
    *  @param position The absolute byte offset where to read.
    *  @param count The number of bytes to read.
    *  @return A byte array containing data read from the underlying stream. This might
    * be less than the number of bytes requested if the end of the stream is reached.
    *  @throws System.IO.IOException An I/O error occurs.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.ArgumentOutOfRangeException count is negative.
    */
    public byte[] readBytes(long position, int count) throws Exception {
        getBaseStream().Seek(position, SeekOrigin.Begin);
        return readBytes(count);
    }

}


