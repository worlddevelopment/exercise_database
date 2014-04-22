//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DotZLib;

import CS2JNet.JavaSupport.language.IEventCollection;
import CS2JNet.JavaSupport.util.ListSupport;
import DotZLib.DataAvailableHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* Declares methods and events for implementing compressors/decompressors
*/
public interface Codec   
{
    /**
    * Occurs when more processed data are available.
    */
    IEventCollection<DataAvailableHandler> DataAvailable() throws Exception ;

    /**
    * Adds more data to the codec to be processed.
    * 
    *  @param data Byte array containing the data to be added to the codecAdding data may, or may not, raise the 
    *  {@code DataAvailable}
    *  event
    */
    void add(byte[] data) throws Exception ;

    /**
    * Adds more data to the codec to be processed.
    * 
    *  @param data Byte array containing the data to be added to the codec
    *  @param offset The index of the first byte to add from 
    *  {@code data}
    * 
    *  @param count The number of bytes to addAdding data may, or may not, raise the 
    *  {@code DataAvailable}
    *  event
    */
    void add(byte[] data, int offset, int count) throws Exception ;

    /**
    * Finishes up any pending data that needs to be processed and handled.
    */
    void finish() throws Exception ;

    /**
    * Gets the checksum of the data that has been added so far
    */
    uint getChecksum() throws Exception ;

}


