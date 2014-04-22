//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* Declares methods and properties that enables a running checksum to be calculated
*/
public interface ChecksumGenerator   
{
    /**
    * Gets the current value of the checksum
    */
    uint getValue() throws Exception ;

    /**
    * Clears the current checksum to 0
    */
    void reset() throws Exception ;

    /**
    * Updates the current checksum with an array of bytes
    * 
    *  @param data The data to update the checksum with
    */
    void update(byte[] data) throws Exception ;

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
    *  @throws ArgumentNullException 
    *  {@code data}
    *  is a null reference
    *  @throws ArgumentOutOfRangeException Offset or count is negative.
    */
    void update(byte[] data, int offset, int count) throws Exception ;

    /**
    * Updates the current checksum with the data from a string
    * 
    *  @param data The string to update the checksum withThe characters in the string are converted by the UTF-8 encoding
    */
    void update(String data) throws Exception ;

    /**
    * Updates the current checksum with the data from a string, using a specific encoding
    * 
    *  @param data The string to update the checksum with
    *  @param encoding The encoding to use
    */
    void update(String data, Encoding encoding) throws Exception ;

}


