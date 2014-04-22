//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:52 AM
//

package b2xtranslator_java.DIaLOGIKa.b2xtranslator.StructuredStorage.Reader;

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
public interface IStreamReader   
{
    /**
    * Exposes access to the underlying stream of type IStreamReader.
    * 
    *  @return The underlying stream associated with the IStreamReader
    */
    CountingInputStream getBaseStream() throws Exception ;

    /**
    * Closes the current reader and the underlying stream.
    */
    void close() throws Exception ;

    /**
    * Returns the next available character and does not advance the byte or character position.
    * 
    *  @return 
    * The next available character, or -1 if no more characters are available or
    * the stream does not support seeking.
    * 
    *  @throws System.IO.IOException An I/O error occurs.
    */
    int peekChar() throws Exception ;

    /**
    * Reads characters from the underlying stream and advances the current position
    * of the stream in accordance with the Encoding used and the specific character
    * being read from the stream.
    * 
    *  @return 
    * The next character from the input stream, or -1 if no characters are currently available.
    * 
    *  @throws System.IO.IOException An I/O error occurs.
    *  @throws System.ObjectDisposedException The stream is closed.
    */
    int read() throws Exception ;

    /**
    * Reads count bytes from the stream with index as the starting point in the byte array.
    * 
    *  @param buffer The buffer to read data into.
    *  @param index The starting point in the buffer at which to begin reading into the buffer.
    *  @param count The number of characters to read.
    *  @return The number of characters read into buffer. This might be less than the number
    * of bytes requested if that many bytes are not available, or it might be zero
    * if the end of the stream is reached.
    *  @throws System.ArgumentException The buffer length minus index is less than count.
    *  @throws System.ArgumentNullException buffer is null.
    *  @throws System.ArgumentOutOfRangeException index or count is negative.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    int read(byte[] buffer, int index, int count) throws Exception ;

    /**
    * Reads count characters from the stream with index as the starting point in the character array.
    * 
    *  @param buffer The buffer to read data into.
    *  @param index The starting point in the buffer at which to begin reading into the buffer.
    *  @param count The number of characters to read.
    *  @return The total number of characters read into the buffer. This might be less than
    * the number of characters requested if that many characters are not currently
    * available, or it might be zero if the end of the stream is reached.
    *  @throws System.ArgumentException The buffer length minus index is less than count.
    *  @throws System.ArgumentNullException buffer is null.
    *  @throws System.ArgumentOutOfRangeException index or count is negative.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    int read(char[] buffer, int index, int count) throws Exception ;

    /**
    * Reads a Boolean value from the current stream and advances the current position
    * of the stream by one byte.
    * 
    *  @return true if the byte is nonzero; otherwise, false.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    boolean readBoolean() throws Exception ;

    /**
    * Reads the next byte from the current stream and advances the current position
    * of the stream by one byte.
    * 
    *  @return The next byte read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    byte readByte() throws Exception ;

    /**
    * Reads count bytes from the current stream into a byte array and advances
    * the current position by count bytes.
    * 
    *  @param count The number of bytes to read.
    *  @return A byte array containing data read from the underlying stream. This might
    * be less than the number of bytes requested if the end of the stream is reached.
    *  @throws System.IO.IOException An I/O error occurs.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.ArgumentOutOfRangeException count is negative.
    */
    byte[] readBytes(int count) throws Exception ;

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
    byte[] readBytes(long position, int count) throws Exception ;

    /**
    * Reads the next character from the current stream and advances the current
    * position of the stream in accordance with the Encoding used and the specific
    * character being read from the stream.
    * 
    *  @return A character read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    *  @throws System.ArgumentException A surrogate character was read.
    */
    char readChar() throws Exception ;

    /**
    * Reads count characters from the current stream, returns the data in a character
    * array, and advances the current position in accordance with the Encoding
    * used and the specific character being read from the stream.
    * 
    *  @param count The number of characters to read.
    *  @return A character array containing data read from the underlying stream. This might
    * be less than the number of characters requested if the end of the stream
    * is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    *  @throws System.ArgumentOutOfRangeException count is negative.
    */
    char[] readChars(int count) throws Exception ;

    /**
    * Reads a decimal value from the current stream and advances the current position
    * of the stream by sixteen bytes.
    * 
    *  @return A decimal value read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    double readDecimal() throws Exception ;

    /**
    * Reads an 8-byte floating point value from the current stream and advances
    * the current position of the stream by eight bytes.
    * 
    *  @return An 8-byte floating point value read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    double readDouble() throws Exception ;

    /**
    * Reads a 2-byte signed integer from the current stream and advances the current
    * position of the stream by two bytes.
    * 
    *  @return A 2-byte signed integer read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    short readInt16() throws Exception ;

    /**
    * Reads a 4-byte signed integer from the current stream and advances the current
    * position of the stream by four bytes.
    * 
    *  @return A 4-byte signed integer read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    int readInt32() throws Exception ;

    /**
    * Reads an 8-byte signed integer from the current stream and advances the current
    * position of the stream by eight bytes.
    * 
    *  @return An 8-byte signed integer read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    long readInt64() throws Exception ;

    /**
    * Reads a signed byte from this stream and advances the current position of
    * the stream by one byte.
    * 
    *  @return A signed byte read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    byte readSByte() throws Exception ;

    /**
    * Reads a 4-byte floating point value from the current stream and advances
    * the current position of the stream by four bytes.
    * 
    *  @return A 4-byte floating point value read from the current stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    float readSingle() throws Exception ;

    /**
    * Reads a string from the current stream. The string is prefixed with the length,
    * encoded as an integer seven bits at a time.
    * 
    *  @return The string being read.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    String readString() throws Exception ;

    /**
    * Reads a 2-byte unsigned integer from the current stream using little-endian
    * encoding and advances the position of the stream by two bytes.
    * 
    *  @return A 2-byte unsigned integer read from this stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    short readUInt16() throws Exception ;

    /**
    * Reads a 4-byte unsigned integer from the current stream and advances the
    * position of the stream by four bytes.
    * 
    *  @return A 4-byte unsigned integer read from this stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    long readUInt32() throws Exception ;

    /**
    * Reads an 8-byte unsigned integer from the current stream and advances the
    * position of the stream by eight bytes.
    * 
    *  @return An 8-byte unsigned integer read from this stream.
    *  @throws System.IO.EndOfStreamException The end of the stream is reached.
    *  @throws System.ObjectDisposedException The stream is closed.
    *  @throws System.IO.IOException An I/O error occurs.
    */
    BigInteger readUInt64() throws Exception ;

    int read(byte[] buffer, int count) throws Exception ;

    int read(byte[] buffer) throws Exception ;

}


