//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.System.ArgumentException;
import DotZLib.ChecksumGeneratorBase;

/**
* Implements a checksum generator that computes the Adler checksum on data
*/
public final class AdlerChecksum  extends ChecksumGeneratorBase 
{
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ uint adler32(uint adler, int data, uint length) throws Exception ;

    /**
    * Initializes a new instance of the Adler checksum generator
    */
    public AdlerChecksum() throws Exception {
        super();
    }

    /**
    * Initializes a new instance of the Adler checksum generator with a specified value
    * 
    *  @param initialValue The value to set the current checksum to
    */
    public AdlerChecksum(uint initialValue) throws Exception {
        super(initialValue);
    }

    /**
    * Updates the current checksum with part of an array of bytes
    * 
    *  @param data The data to update the checksum with
    *  @param offset Where in 
    *  {@code data}
    *  to start updating
    *  @param count The number of bytes from 
    *  {@code data}
    *  to use
    *  @throws ArgumentException The sum of offset and count is larger than the length of 
    *  {@code data}
    * 
    *  @throws NullReferenceException 
    *  {@code data}
    *  is a null reference
    *  @throws ArgumentOutOfRangeException Offset or count is negative.
    */
    public void update(byte[] data, int offset, int count) throws Exception {
        if (offset < 0 || count < 0)
            throw new ArgumentOutOfRangeException();
         
        if ((offset + count) > data.length)
            throw new ArgumentException();
         
        GCHandle hData = GCHandle.Alloc(data, GCHandleType.Pinned);
        try
        {
            _current = adler32(_current, hData.AddrOfPinnedObject().ToInt32() + offset, (uint)count);
        }
        finally
        {
            hData.Free();
        }
    }

}


