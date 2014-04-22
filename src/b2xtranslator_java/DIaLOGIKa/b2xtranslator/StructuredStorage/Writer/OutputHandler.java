//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractIOHandler;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InternalBitConverter;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.InvalidSectorSizeException;
import java.io.InputStream;

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
/**
* Class which encapsulates methods which ease writing structured storage components to a stream.
* Author: math
*/
public class OutputHandler  extends AbstractIOHandler 
{
    public InputStream getBaseStream() throws Exception {
        return _stream;
    }

    /**
    * Returns UInt64.MaxValue because size of stream is not defined yet.
    */
    public UInt64 getIOStreamSize() throws Exception {
        return UInt64.MaxValue;
    }

    /**
    * Constructor.
    * 
    *  @param memoryStream The target memory stream.
    */
    public OutputHandler(MemoryStream memoryStream) throws Exception {
        _stream = memoryStream;
        _bitConverter = new InternalBitConverter(true);
    }

    /**
    * Writes a byte to the stream.
    * 
    *  @param value The byte to write.
    */
    public void writeByte(byte value) throws Exception {
        _stream.WriteByte(value);
    }

    /**
    * Writes a UInt16 to the stream.
    * 
    *  @param value The UInt16 to write.
    */
    public void writeUInt16(UInt16 value) throws Exception {
        _stream.write(_bitConverter.getBytes(value),0,2);
    }

    /**
    * Writes a UInt32 to the stream.
    * 
    *  @param value The UInt32 to write.
    */
    public void writeUInt32(long value) throws Exception {
        _stream.write(_bitConverter.getBytes(value),0,4);
    }

    /**
    * Writes a UInt64 to the stream.
    * 
    *  @param value The UInt64 to write.
    */
    public void writeUInt64(UInt64 value) throws Exception {
        _stream.write(_bitConverter.getBytes(value),0,8);
    }

    /**
    * Writes a byte array to the stream.
    * 
    *  @param value The byte array to write.
    */
    public void write(byte[] data) throws Exception {
        _stream.write(data,0,data.length);
    }

    /**
    * Writes sectors to the stream and padding the sector with the given byte.
    * 
    *  @param data The data to write.
    *  @param sectorSize The size of a sector.
    *  @param padding The byte which is used for padding
    */
    public void writeSectors(byte[] data, UInt16 sectorSize, byte padding) throws Exception {
        uint remaining = (uint)(data.LongLength % sectorSize);
        _stream.write(data,0,data.length);
        if (remaining == 0)
        {
            return ;
        }
         
        for (uint i = 0;i < (sectorSize - remaining);i++)
        {
            _stream.WriteByte(padding);
        }
    }

    /**
    * Writes sectors to the stream and padding the sector with the given UInt32.
    * 
    *  @param data The data to write.
    *  @param sectorSize The size of a sector.
    *  @param padding The UInt32 which is used for padding
    */
    public void writeSectors(byte[] data, UInt16 sectorSize, long padding) throws Exception {
        uint remaining = (uint)(data.LongLength % sectorSize);
        _stream.write(data,0,data.length);
        if (remaining == 0)
        {
            return ;
        }
         
        // consistency check
        if ((sectorSize - remaining) % != 0)
        {
            throw new InvalidSectorSizeException();
        }
         
        for (uint i = 0;i < ((sectorSize - remaining) /);i++)
        {
            writeUInt32(padding);
        }
    }

    /**
    * Writes the internal memory stream to a given stream.
    * 
    *  @param stream The output stream.
    */
    public void writeToStream(InputStream stream) throws Exception {
        ;
        BinaryReader reader = new BinaryReader(getBaseStream());
        reader.BaseStream.Seek(0, SeekOrigin.Begin);
        while (true)
        {
            byte[] array = reader.ReadBytes(((int)(bytesToReadAtOnce)));
            stream.write(array,0,array.length);
            if (array.length != bytesToReadAtOnce)
            {
                break;
            }
             
        }
        stream.Flush();
    }

}


