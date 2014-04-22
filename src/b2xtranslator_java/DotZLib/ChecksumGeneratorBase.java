//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.System.Text.EncodingSupport;
import DotZLib.ChecksumGenerator;

//
// � Copyright Henrik Ravn 2004
//
// Use, modification and distribution are subject to the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
/**
* Implements the common functionality needed for all 
*  {@link #ChecksumGenerator}
* s
*/
public abstract class ChecksumGeneratorBase   implements ChecksumGenerator
{
    /**
    * The value of the current checksum
    */
    protected uint _current;
    /**
    * Initializes a new instance of the checksum generator base - the current checksum is
    * set to zero
    */
    public ChecksumGeneratorBase() throws Exception {
        _current = 0;
    }

    /**
    * Initializes a new instance of the checksum generator basewith a specified value
    * 
    *  @param initialValue The value to set the current checksum to
    */
    public ChecksumGeneratorBase(uint initialValue) throws Exception {
        _current = initialValue;
    }

    /**
    * Resets the current checksum to zero
    */
    public void reset() throws Exception {
        _current = 0;
    }

    /**
    * Gets the current checksum value
    */
    public uint getValue() throws Exception {
        return _current;
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
    *  @throws ArgumentOutOfRangeException Offset or count is negative.All the other 
    *  {@code Update}
    *  methods are implmeneted in terms of this one.
    * This is therefore the only method a derived class has to implement
    */
    public abstract void update(byte[] data, int offset, int count) throws Exception ;

    /**
    * Updates the current checksum with an array of bytes.
    * 
    *  @param data The data to update the checksum with
    */
    public void update(byte[] data) throws Exception {
        update(data,0,data.length);
    }

    /**
    * Updates the current checksum with the data from a string
    * 
    *  @param data The string to update the checksum withThe characters in the string are converted by the UTF-8 encoding
    */
    public void update(String data) throws Exception {
        update(EncodingSupport.GetEncoder("UTF-8").getBytes(data));
    }

    /**
    * Updates the current checksum with the data from a string, using a specific encoding
    * 
    *  @param data The string to update the checksum with
    *  @param encoding The encoding to use
    */
    public void update(String data, Encoding encoding) throws Exception {
        update(encoding.getBytes(data));
    }

}


