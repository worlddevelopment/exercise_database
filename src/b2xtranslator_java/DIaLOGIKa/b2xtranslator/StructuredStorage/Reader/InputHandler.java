//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

import java.io.*;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.AbstractIOHandler;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.FileHandlerNotCorrectlyInitializedException;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.Measures;
import b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Common.ReadBytesAmountMismatchException;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.commons.io.input.CountingInputStream;

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
* Provides methods for accessing the file stream
* Author: math
*/
public class InputHandler  extends AbstractIOHandler 
{
    /**
    * Constructor, opens the given file
    */
    public InputHandler(String fileName) throws Exception {
        _stream = new CountingInputStream(new FileInputStream(fileName));
    }

    public InputHandler(CountingInputStream stream) throws Exception {
        _stream = stream;
    }

    /**
    * The size of the associated stream in bytes.
    * J.R.I.B.-Wein: Attention: This might block until there is data to read if there is no data at first.
    */
    public /*UInt64*/BigInteger getIOStreamSize() throws Exception {
    	BigInteger streamSizeInBytes = BigInteger.ZERO;
    	while (_stream.read() != -1) {
    		streamSizeInBytes = streamSizeInBytes.add(BigInteger.ONE);
    	}
    	// optional check:
    	if (streamSizeInBytes.compareTo(BigInteger.valueOf(_stream.getByteCount())) != 0) {
    		//okay we trust apache:
    		//return _stream.getByteCount();<--TODO which one to trust.
    	}
        return (/*UInt64*/BigInteger)streamSizeInBytes;//_stream.Length;
    }

    /**
    * Seeks relative to the current position by the given offset
    */
    public BigInteger relativeSeek(BigInteger offset) throws Exception {
        if (offset.compareTo(BigInteger.ZERO) < 0)
        {
            throw new Exception("offset while seeking relative to the current position is out of range/bounds: "
            		+ offset);
        }
         
        return offset.add(BigInteger.valueOf(_stream.getByteCount()));//.Seek(offset/*new position relative to origin*/, SeekOrigin.Current/*<-the origin*/);
    }

    /**
    * Seeks to a given sector in the compound file.
    * May only be used after SetHeaderReference() is called. <--TODO: check if still valid?
    * 
    * Annotation by JRIBWein: Pay attention to the reset() method. If somewhere else mark is called
    * as mark() sets the restarting point of reset to the current stream position at point of time 
    * mark is called. 
    */
    public BigInteger seekToSector(BigInteger sector) throws Exception {
        if (_header == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if (sector.compareTo(BigInteger.ZERO) < 0)
        {
            throw new Exception("sector ArgumentOutOfRange");
        }
         
        // header sector == -1
        if (sector.compareTo(BigInteger.valueOf(-1)) == 0)
        {
        	_stream.reset();//<--TODO how to check if we really are at beginning as mark() could have changed restart point of reset()?
            return BigInteger.ZERO;//_stream.reset();//.Seek(0, SeekOrigin.Begin);
        }
         
//        return _stream.Seek( (sector.shiftLeft(_header.getSectorShift())).add(BigInteger.valueOf(Measures.HeaderSize))
//        		, SeekOrigin.Begin);
        Global.resetStream(_stream);
        return (sector.shiftLeft(_header.getSectorShift())).add(BigInteger.valueOf(Measures.HeaderSize));
    }

    /**
    * Seeks to a given sector and position in the compound file.
    * May only be used after SetHeaderReference() is called.
    * 
    *  @return The new position in the stream.
    */
    public BigInteger seekToPositionInSector(BigInteger sector, BigInteger position) throws Exception {
        if (_header == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if ( position.compareTo(BigInteger.ZERO) < 0 || position.compareTo(BigInteger.valueOf(_header.getSectorSize()) ) >= 0/*_header.getSectorSize()*/)
        {
            throw new Exception("position out of range/bounds.");
        }
         
        // header sector == -1
        if ( sector./*equals(new BigInteger("-1"))*/compareTo(new BigInteger("-1")) == -1 )
        {//TODO InputStream.mark does nothing according to JavaDoc!!
            _stream.reset();
            return _stream.position;//.Seek(position, SeekOrigin.Begin);
        }
         
        return _stream.Seek((sector.shiftLeft(_header.getSectorShift())).add(BigInteger.valueOf(Measures.HeaderSize).add(position)), SeekOrigin.Begin);
    }

    /**
    * Reads a byte at the current position of the file stream.
    * Advances the stream pointer accordingly.
    * 
    *  @return The byte value read from the stream.
    */
    public byte readByte() throws Exception {
        int result = _stream.read/*byte*/();
        if (result == -1)
        {
            throw new ReadBytesAmountMismatchException();
        }
         
        return (byte)result;
    }

    /**
    * Reads bytes at the current position of the file stream into a byte array.
    * The array size determines the number of bytes to read.
    * Advances the stream pointer accordingly.
    */
    public void read(byte[] array) throws Exception {
        read(array,0,array.length);
    }

    /**
    * Reads bytes at the current position of the file stream into a byte array.
    * Advances the stream pointer accordingly.
    * 
    *  @param array The array to read to
    *  @param offset The offset in the array to read to
    *  @param count The number of bytes to read
    */
    public void read(byte[] array, int offset, int count) throws Exception {
        int result = _stream.read(array,offset,count);
        if (result != count)
        {
            throw new ReadBytesAmountMismatchException();
        }
         
    }

    /**
    * Reads a byte at the current position of the file stream.
    * Advances the stream pointer accordingly.
    * 
    *  @return The byte cast to an int, or -1 if reading from the end of the stream.
    */
    public int uncheckedReadByte() throws Exception {
        return _stream.read/*Byte*/();
    }

    /**
    * Reads bytes at the current position of the file stream into a byte array.
    * Advances the stream pointer accordingly.
    * 
    *  @param array The array to read to
    *  @param offset The offset in the array to read to
    *  @param count The number of bytes to read
    *  @return The total number of bytes read into the buffer.
    * This might be less than the number of bytes requested if that number
    * of bytes are not currently available, or zero if the end of the stream is reached.
    */
    public int uncheckedRead(byte[] array, int offset, int count) throws Exception {
        return _stream.read(array,offset,count);
    }

    /**
    * Reads bytes at the given position of the file stream into a byte array.
    * The array size determines the number of bytes to read.
    * Advances the stream pointer accordingly.
    */
    public void readPosition(byte[] array, BigInteger position) throws Exception {
        if (position.compareTo(BigInteger.ZERO) < 0)
        {
            throw new Exception("position ArgumentOutOfRange in readPosition.");
        }
        if (!_stream.markSupported()) {
        	System.out.println("Advancing or retiring to (seek) a position in byte stream is not supported.");
        }
        	
        _stream.mark(0);//go to position 0 //Seek(position, 0);//SeekOrigin.BEGINNING? END
        int result = _stream.read(array,0,array.length);
        if (result != array.length)
        {
            throw new ReadBytesAmountMismatchException();
        }
         
    }

    /**
    * Reads a BigInteger at the current position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The BigInteger value read from the stream.
    */
    public short readUInt16() throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        byte[] array = new byte[2];
        read(array);
        return _bitConverter.toUInt16(array);
    }

    /**
    * Reads a UInt32 at the current position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The UInt32 value read from the stream.
    */
    public BigInteger readUInt32() throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        byte[] array = new byte[4];
        read(array);
        return _bitConverter.toUInt32(array);
    }

    /**
    * Reads a BigInteger at the current position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The BigInteger value read from the stream.
    */
    public BigInteger readUInt64() throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        byte[] array = new byte[8];
        read(array);
        return _bitConverter.toUInt64(array);
    }

    /**
    * Reads a BigInteger at the given position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The BigInteger value read at the given position.
    */
    public BigInteger readUInt16(BigInteger position) throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if (position < 0)
        {
            throw new Exception("position ArgumentOutOfRange.");
        }
         
        byte[] array = new byte[2];
        readPosition(array,position);
        return _bitConverter.toUInt16(array);
    }

    /**
    * Reads a UInt32 at the given position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The UInt32 value read at the given position.
    */
    public BigInteger readUInt32(BigInteger position) throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if (position < 0)
        {
            throw new Exception("position ArgumentOutOfRange.");
        }
         
        byte[] array = new byte[4];
        readPosition(array,position);
        return _bitConverter.toUInt32(array);
    }

    /**
    * Reads a BigInteger at the given position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @return The BigInteger value read at the given position.
    */
    public BigInteger readUInt64(BigInteger position) throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if (position.compareTo(BigInteger.ZERO) < 0)
        {
            throw new Exception("position ArgumentOutOfRange.");
        }
         
        byte[] array = new byte[8];
        readPosition(array,position);
        return _bitConverter.toUInt64(array);
    }

    /**
    * Reads a UTF-16 encoded unicode string at the current position of the file stream.
    * May only be used after InitBitConverter() is called.
    * Advances the stream pointer accordingly.
    * 
    *  @param size The maximum size of the string in bytes (1 char = 2 bytes) including the Unicode NULL.
    *  @return The string read from the stream.
    */
    public String readString(int size) throws Exception {
        if (_bitConverter == null)
        {
            throw new FileHandlerNotCorrectlyInitializedException();
        }
         
        if (size < 1)
        {
            throw new Exception("size ArgumentOutOfRange.");
        }
         
        byte[] array = new byte[size];
        read(array);
        return _bitConverter.toString(array);
    }

}


