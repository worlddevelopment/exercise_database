//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DotZLib;

import CS2JNet.System.ArgumentException;
import CS2JNet.System.LCC.IDisposable;
import CS2JNet.System.ObjectDisposedException;
import CS2JNet.System.StringSupport;
import DotZLib.CompressLevel;
import DotZLib.ZLibException;
import java.io.InputStream;
import java.io.IOException;

//
// � Copyright Henrik Ravn 2004
//
// Use, modification and distribution are subject to the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
/**
* Implements a compressed 
*  {@link #Stream}
* , in GZip (.gz) format.
*/
public class GZipStream  extends InputStream implements IDisposable
{
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ IntPtr gzopen(String name, String mode) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int gzclose(IntPtr gzFile) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int gzwrite(IntPtr gzFile, int data, int length) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int gzread(IntPtr gzFile, int data, int length) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int gzgetc(IntPtr gzFile) throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ int gzputc(IntPtr gzFile, int c) throws Exception ;

    private IntPtr _gzFile = new IntPtr();
    private boolean _isDisposed = false;
    private boolean _isWriting;
    /**
    * Creates a new file as a writeable GZipStream
    * 
    *  @param fileName The name of the compressed file to create
    *  @param level The compression level to use when adding data
    *  @throws ZLibException If an error occurred in the internal zlib function
    */
    public GZipStream(String fileName, CompressLevel level) throws Exception {
        _isWriting = true;
        _gzFile = gzopen(fileName,String.format(StringSupport.CSFmtStrToJFmtStr("wb{0}"),((Enum)level).ordinal()));
        if (_gzFile == IntPtr.Zero)
            throw new ZLibException(-1,"Could not open " + fileName);
         
    }

    /**
    * Opens an existing file as a readable GZipStream
    * 
    *  @param fileName The name of the file to open
    *  @throws ZLibException If an error occurred in the internal zlib function
    */
    public GZipStream(String fileName) throws Exception {
        _isWriting = false;
        _gzFile = gzopen(fileName,"rb");
        if (_gzFile == IntPtr.Zero)
            throw new ZLibException(-1,"Could not open " + fileName);
         
    }

    /**
    * Returns true of this stream can be read from, false otherwise
    */
    public boolean getCanRead() throws Exception {
        return !_isWriting;
    }

    /**
    * Returns false.
    */
    public boolean getCanSeek() throws Exception {
        return false;
    }

    /**
    * Returns true if this tsream is writeable, false otherwise
    */
    public boolean getCanWrite() throws Exception {
        return _isWriting;
    }

    /**
    * Destroys this instance
    */
    protected void finalize() throws Throwable {
        try
        {
            cleanUp(false);
        }
        finally
        {
            super.finalize();
        }
    }

    /**
    * Closes the external file handle
    */
    public void dispose() throws Exception {
        cleanUp(true);
    }

    // Does the actual closing of the file handle.
    private void cleanUp(boolean isDisposing) throws Exception {
        if (!_isDisposed)
        {
            gzclose(_gzFile);
            _isDisposed = true;
        }
         
    }

    /**
    * Attempts to read a number of bytes from the stream.
    * 
    *  @param buffer The destination data buffer
    *  @param offset The index of the first destination byte in 
    *  {@code buffer}
    * 
    *  @param count The number of bytes requested
    *  @return The number of bytes read
    *  @throws ArgumentNullException If 
    *  {@code buffer}
    *  is null
    *  @throws ArgumentOutOfRangeException If 
    *  {@code count}
    *  or 
    *  {@code offset}
    *  are negative
    *  @throws ArgumentException If 
    *  {@code offset}
    *   + 
    *  {@code count}
    *  is > buffer.Length
    *  @throws NotSupportedException If this stream is not readable.
    *  @throws ObjectDisposedException If this stream has been disposed.
    */
    public int read(byte[] buffer, int offset, int count) throws Exception {
        if (!getCanRead())
            throw new NotSupportedException();
         
        if (buffer == null)
            throw new ArgumentNullException();
         
        if (offset < 0 || count < 0)
            throw new ArgumentOutOfRangeException();
         
        if ((offset + count) > buffer.length)
            throw new ArgumentException();
         
        if (_isDisposed)
            throw new ObjectDisposedException("GZipStream");
         
        GCHandle h = GCHandle.Alloc(buffer, GCHandleType.Pinned);
        int result;
        try
        {
            result = gzread(_gzFile, h.AddrOfPinnedObject().ToInt32() + offset, count);
            if (result < 0)
                throw new IOException();
             
        }
        finally
        {
            h.Free();
        }
        return result;
    }

    /**
    * Attempts to read a single byte from the stream.
    * 
    *  @return The byte that was read, or -1 in case of error or End-Of-File
    */
    public int readByte() throws Exception {
        if (!getCanRead())
            throw new NotSupportedException();
         
        if (_isDisposed)
            throw new ObjectDisposedException("GZipStream");
         
        return gzgetc(_gzFile);
    }

    /**
    * Writes a number of bytes to the stream
    * 
    *  @param buffer 
    *  @param offset 
    *  @param count 
    *  @throws ArgumentNullException If 
    *  {@code buffer}
    *  is null
    *  @throws ArgumentOutOfRangeException If 
    *  {@code count}
    *  or 
    *  {@code offset}
    *  are negative
    *  @throws ArgumentException If 
    *  {@code offset}
    *   + 
    *  {@code count}
    *  is > buffer.Length
    *  @throws NotSupportedException If this stream is not writeable.
    *  @throws ObjectDisposedException If this stream has been disposed.
    */
    public void write(byte[] buffer, int offset, int count) throws Exception {
        if (!getCanWrite())
            throw new NotSupportedException();
         
        if (buffer == null)
            throw new ArgumentNullException();
         
        if (offset < 0 || count < 0)
            throw new ArgumentOutOfRangeException();
         
        if ((offset + count) > buffer.length)
            throw new ArgumentException();
         
        if (_isDisposed)
            throw new ObjectDisposedException("GZipStream");
         
        GCHandle h = GCHandle.Alloc(buffer, GCHandleType.Pinned);
        try
        {
            int result = gzwrite(_gzFile, h.AddrOfPinnedObject().ToInt32() + offset, count);
            if (result < 0)
                throw new IOException();
             
        }
        finally
        {
            h.Free();
        }
    }

    /**
    * Writes a single byte to the stream
    * 
    *  @param value The byte to add to the stream.
    *  @throws NotSupportedException If this stream is not writeable.
    *  @throws ObjectDisposedException If this stream has been disposed.
    */
    public void writeByte(byte value) throws Exception {
        if (!getCanWrite())
            throw new NotSupportedException();
         
        if (_isDisposed)
            throw new ObjectDisposedException("GZipStream");
         
        int result = gzputc(_gzFile,(int)value);
        if (result < 0)
            throw new IOException();
         
    }

    /**
    * Not supported.
    * 
    *  @param value 
    *  @throws NotSupportedException Always thrown
    */
    public void setLength(long value) throws Exception {
        throw new NotSupportedException();
    }

    /**
    * Not suppported.
    * 
    *  @param offset 
    *  @param origin 
    *  @return 
    *  @throws NotSupportedException Always thrown
    */
    public long seek(long offset, SeekOrigin origin) throws Exception {
        throw new NotSupportedException();
    }

    /**
    * Flushes the 
    *  {@code GZipStream}
    * .
    * In this implementation, this method does nothing. This is because excessive
    * flushing may degrade the achievable compression rates.
    */
    public void flush() throws Exception {
    }

    // left empty on purpose
    /**
    * Gets/sets the current position in the 
    *  {@code GZipStream}
    * . Not suppported.
    * In this implementation this property is not supported
    *  @throws NotSupportedException Always thrown
    */
    public long getPosition() throws Exception {
        throw new NotSupportedException();
    }

    public void setPosition(long value) throws Exception {
        throw new NotSupportedException();
    }

    /**
    * Gets the size of the stream. Not suppported.
    * In this implementation this property is not supported
    *  @throws NotSupportedException Always thrown
    */
    public long getLength() throws Exception {
        throw new NotSupportedException();
    }

}


