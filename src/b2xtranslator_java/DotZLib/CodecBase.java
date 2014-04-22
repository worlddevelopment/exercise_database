//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.System.LCC.IDisposable;
import DotZLib.Codec;
import DotZLib.DataAvailableHandler;
import DotZLib.ZStream;

//
// � Copyright Henrik Ravn 2004
//
// Use, modification and distribution are subject to the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
/**
* Implements the common functionality needed for all 
*  {@link #Codec}
* s
*/
public abstract class CodecBase   implements Codec, IDisposable
{
    /**
    * Instance of the internal zlib buffer structure that is
    * passed to all functions in the zlib dll
    */
    public ZStream _ztream = new ZStream();
    /**
    * True if the object instance has been disposed, false otherwise
    */
    protected boolean _isDisposed = false;
    /**
    * The size of the internal buffers
    */
    protected static final int kBufferSize = 16384;
    private byte[] _outBuffer = new byte[kBufferSize];
    private byte[] _inBuffer = new byte[kBufferSize];
    private GCHandle _hInput = new GCHandle();
    private GCHandle _hOutput = new GCHandle();
    private uint _checksum = 0;
    /**
    * Initializes a new instance of the 
    *  {@code CodeBase}
    *  class.
    */
    public CodecBase() throws Exception {
        try
        {
            _hInput = GCHandle.Alloc(_inBuffer, GCHandleType.Pinned);
            _hOutput = GCHandle.Alloc(_outBuffer, GCHandleType.Pinned);
        }
        catch (Exception __dummyCatchVar0)
        {
            cleanUp(false);
            throw __dummyCatchVar0;
        }
    
    }

    /**
    * Occurs when more processed data are available.
    */
    public DataAvailableHandler DataAvailable;
    /**
    * Fires the 
    *  {@link #DataAvailable}
    *  event
    */
    protected void onDataAvailable() throws Exception {
        if (_ztream.total_out > 0)
        {
            if (DataAvailable != null)
                DataAvailable.invoke(_outBuffer,0,(int)_ztream.total_out);
             
            resetOutput();
        }
         
    }

    /**
    * Adds more data to the codec to be processed.
    * 
    *  @param data Byte array containing the data to be added to the codecAdding data may, or may not, raise the 
    *  {@code DataAvailable}
    *  event
    */
    public void add(byte[] data) throws Exception {
        add(data,0,data.length);
    }

    /**
    * Adds more data to the codec to be processed.
    * 
    *  @param data Byte array containing the data to be added to the codec
    *  @param offset The index of the first byte to add from 
    *  {@code data}
    * 
    *  @param count The number of bytes to addAdding data may, or may not, raise the 
    *  {@code DataAvailable}
    *  eventThis must be implemented by a derived class
    */
    public abstract void add(byte[] data, int offset, int count) throws Exception ;

    /**
    * Finishes up any pending data that needs to be processed and handled.
    * This must be implemented by a derived class
    */
    public abstract void finish() throws Exception ;

    /**
    * Gets the checksum of the data that has been added so far
    */
    public uint getChecksum() throws Exception {
        return _checksum;
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
    * Releases any unmanaged resources and calls the 
    *  {@link #CleanUp()}
    *  method of the derived class
    */
    public void dispose() throws Exception {
        cleanUp(true);
    }

    /**
    * Performs any codec specific cleanup
    * This must be implemented by a derived class
    */
    protected abstract void cleanUp() throws Exception ;

    // performs the release of the handles and calls the dereived CleanUp()
    private void cleanUp(boolean isDisposing) throws Exception {
        if (!_isDisposed)
        {
            cleanUp();
            if (_hInput.IsAllocated)
                _hInput.Free();
             
            if (_hOutput.IsAllocated)
                _hOutput.Free();
             
            _isDisposed = true;
        }
         
    }

    /**
    * Copies a number of bytes to the internal codec buffer - ready for proccesing
    * 
    *  @param data The byte array that contains the data to copy
    *  @param startIndex The index of the first byte to copy
    *  @param count The number of bytes to copy from 
    *  {@code data}
    */
    protected void copyInput(byte[] data, int startIndex, int count) throws Exception {
        Array.Copy(data, startIndex, _inBuffer, 0, count);
        _ztream.next_in = _hInput.AddrOfPinnedObject();
        _ztream.total_in = 0;
        _ztream.avail_in = (uint)count;
    }

    /**
    * Resets the internal output buffers to a known state - ready for processing
    */
    protected void resetOutput() throws Exception {
        _ztream.total_out = 0;
        _ztream.avail_out = kBufferSize;
        _ztream.next_out = _hOutput.AddrOfPinnedObject();
    }

    /**
    * Updates the running checksum property
    * 
    *  @param newSum The new checksum value
    */
    protected void setChecksum(uint newSum) throws Exception {
        _checksum = newSum;
    }

}


