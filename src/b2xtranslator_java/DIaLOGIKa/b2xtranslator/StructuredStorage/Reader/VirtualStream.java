//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ChainSizeMismatchException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ReadBytesAmountMismatchException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.SectorId;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.AbstractFat;
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
* Encapsulates a virtual stream in a compound file
* Author: math
*/
public class VirtualStream  extends InputStream 
{
    AbstractFat _fat;
    protected long _position;
    protected long _length;
    String _name;
    CSList<Long> _sectors;
    /**
    * Initializes a virtual stream
    * 
    *  @param fat Handle to the fat of the respective file
    *  @param startSector Start sector of the stream (sector 0 is sector immediately following the header)
    *  @param sizeOfStream Size of the stream in bytes
    *  @param name Name of the stream
    */
    public VirtualStream(AbstractFat fat, long startSector, long sizeOfStream, String name) throws Exception {
        _fat = fat;
        _length = sizeOfStream;
        _name = name;
        if (startSector == SectorId.ENDOFCHAIN || getLength() == 0)
        {
            return ;
        }
         
        init(startSector);
    }

    /**
    * The current position within the stream.
    * The supported range is from 0 to 2^31 - 1 = 2147483647 = 2GB
    */
    public long getPosition() throws Exception {
        return _position;
    }

    public void setPosition(long value) throws Exception {
        _position = value;
    }

    /**
    * A long value representing the length of the stream in bytes.
    */
    public long getLength() throws Exception {
        return _length;
    }

    /**
    * Reads bytes from the current position in the virtual stream.
    * The number of bytes to read is determined by the length of the array.
    * 
    *  @param array Array which will contain the read bytes after successful execution.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the length of the array if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] array) throws Exception {
        return read(array,array.length);
    }

    /**
    * Reads bytes from the current position in the virtual stream.
    * 
    *  @param array Array which will contain the read bytes after successful execution.
    *  @param count Number of bytes to read.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] array, int count) throws Exception {
        return read(array,0,count);
    }

    /**
    * Reads bytes from a virtual stream.
    * 
    *  @param array Array which will contain the read bytes after successful execution.
    *  @param offset Offset in the array.
    *  @param count Number of bytes to read.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] array, int offset, int count) throws Exception {
        return read(array,offset,count,_position);
    }

    /**
    * Reads bytes from the virtual stream.
    * 
    *  @param array Array which will contain the read bytes after successful execution.
    *  @param offset Offset in the array.
    *  @param count Number of bytes to read.
    *  @param position Start position in the stream.
    *  @return The total number of bytes read into the buffer.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int read(byte[] array, int offset, int count, long position) throws Exception {
        // Checks whether reading is possible
        if (array.length < 1 || count < 1 || position < 0 || offset < 0)
        {
            return 0;
        }
         
        if (offset + count > array.length)
        {
            return 0;
        }
         
        if (position + count > this.getLength())
        {
            count = (int)Math.round(getLength() - position);
            if (count < 1)
            {
                return 0;
            }
             
        }
         
        _position = position;
        int sectorInChain = (int)(position / _fat.getSectorSize());
        int bytesRead = 0;
        int totalBytesRead = 0;
        int positionInArray = offset;
        // Read part in first relevant sector
        int positionInSector = (int)Math.round(position % _fat.getSectorSize());
        _fat.SeekToPositionInSector(_sectors.get(sectorInChain), positionInSector);
        int bytesToReadInFirstSector = (count > _fat.getSectorSize() - positionInSector) ? (_fat.getSectorSize() - positionInSector) : count;
        bytesRead = _fat.uncheckedRead(array,positionInArray,bytesToReadInFirstSector);
        // Update variables
        _position += bytesRead;
        positionInArray += bytesRead;
        totalBytesRead += bytesRead;
        sectorInChain++;
        if (bytesRead != bytesToReadInFirstSector)
        {
            return totalBytesRead;
        }
         
        while (totalBytesRead + _fat.getSectorSize() < count)
        {
            // Read full sectors
            _fat.SeekToPositionInSector(_sectors.get(sectorInChain), 0);
            bytesRead = _fat.UncheckedRead(array, positionInArray, _fat.getSectorSize());
            // Update variables
            _position += bytesRead;
            positionInArray += bytesRead;
            totalBytesRead += bytesRead;
            sectorInChain++;
            if (bytesRead != _fat.getSectorSize())
            {
                return totalBytesRead;
            }
             
        }
        // Finished reading
        if (totalBytesRead >= count)
        {
            return totalBytesRead;
        }
         
        // Read remaining part in last relevant sector
        _fat.SeekToPositionInSector(_sectors.get(sectorInChain), 0);
        bytesRead = _fat.uncheckedRead(array,positionInArray,count - totalBytesRead);
        // Update variables
        _position += bytesRead;
        positionInArray += bytesRead;
        totalBytesRead += bytesRead;
        return totalBytesRead;
    }

    public UInt16 readUInt16() throws Exception {
        byte[] buffer = new byte[];
        if (!= read(buffer))
        {
            throw new ReadBytesAmountMismatchException();
        }
         
        return BitConverter.ToUInt16(buffer, 0);
    }

    public short readInt16() throws Exception {
        byte[] buffer = new byte[];
        if (!= read(buffer))
        {
            throw new ReadBytesAmountMismatchException();
        }
         
        return BitConverter.ToInt16(buffer, 0);
    }

    public long readUInt32() throws Exception {
        byte[] buffer = new byte[];
        if (!= read(buffer))
        {
            throw new ReadBytesAmountMismatchException();
        }
         
        return BitConverter.ToUInt32(buffer, 0);
    }

    public int readInt32() throws Exception {
        byte[] buffer = new byte[];
        if (!= read(buffer))
        {
            throw new ReadBytesAmountMismatchException();
        }
         
        return BitConverter.ToInt32(buffer, 0);
    }

    /**
    * Skips bytes in the virtual stream.
    * 
    *  @param count Number of bytes to skip.
    *  @return The total number of bytes skipped.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int skip(uint count) throws Exception {
        return this.Read(new byte[count]);
    }

    // TODO: Someone more familiar with StructuredStorage.Reader
    // than I am is free to do a more efficient implementation of this. -- flgr
    /**
    * Reads a byte from the current position in the virtual stream.
    * 
    *  @return The byte read or -1 if end of stream
    */
    //public override int ReadByte()
    //{
    //    int result = ReadByte(_position);
    //    _position++;
    //    return result;
    //}
    /**
    * Reads a byte from the given position in the virtual stream.
    * 
    *  @return The byte read or -1 if end of stream
    */
    //public int ReadByte(long position)
    //{
    //    if (position < 0)
    //    {
    //        return -1;
    //    }
    //    int sectorInChain = (int)(position / _fat.SectorSize);
    //    if (sectorInChain >= _entries.Count)
    //    {
    //        return -1;
    //    }
    //    _fat.SeekToPositionInSector(_entries[sectorInChain], position % _fat.SectorSize);
    //    return _fat.UncheckedReadByte();
    //}
    /**
    * Initalizes the stream.
    */
    private void init(long startSector) throws Exception {
        _sectors = _fat.getSectorChain(startSector,(UInt64)Math.Ceiling((double)_length / _fat.getSectorSize()),_name);
        checkConsistency();
    }

    /**
    * Checks whether the size specified in the header matches the actual size
    */
    private void checkConsistency() throws Exception {
        if (((UInt64)_sectors.size()) != Math.Ceiling((double)_length / _fat.getSectorSize()))
        {
            throw new ChainSizeMismatchException(_name);
        }
         
    }

    public boolean getCanRead() throws Exception {
        return true;
    }

    public boolean getCanSeek() throws Exception {
        return true;
    }

    public boolean getCanWrite() throws Exception {
        return false;
    }

    public void flush() throws Exception {
        throw new NotSupportedException("This method is not supported on a read-only stream.");
    }

    public long seek(long offset, SeekOrigin origin) throws Exception {
        SeekOrigin __dummyScrutVar0 = origin;
        if (__dummyScrutVar0.equals(System.IO.SeekOrigin.Begin))
        {
            _position = offset;
        }
        else if (__dummyScrutVar0.equals(System.IO.SeekOrigin.Current))
        {
            _position += offset;
        }
        else if (__dummyScrutVar0.equals(System.IO.SeekOrigin.End))
        {
            _position = _length - offset;
        }
           
        if (_position < 0)
        {
            _position = 0;
        }
        else if (_position > _length)
        {
            _position = _length;
        }
          
        return _position;
    }

    public void setLength(long value) throws Exception {
        throw new NotSupportedException("This method is not supported on a read-only stream.");
    }

    public void write(byte[] buffer, int offset, int count) throws Exception {
        throw new NotSupportedException("This method is not supported on a read-only stream.");
    }

}


