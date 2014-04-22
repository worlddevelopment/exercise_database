//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:31 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* Encapsulates general information about the ZLib library
*/
public class Info   
{
    private static /* [UNSUPPORTED] 'extern' modifier not supported */ uint zlibCompileFlags() throws Exception ;

    private static /* [UNSUPPORTED] 'extern' modifier not supported */ String zlibVersion() throws Exception ;

    private uint _flags;
    // helper function that unpacks a bitsize mask
    private static int bitSize(uint bits) throws Exception {
        long __dummyScrutVar0 = bits;
        if (__dummyScrutVar0.equals(0))
        {
            return 16;
        }
        else if (__dummyScrutVar0.equals(1))
        {
            return 32;
        }
        else if (__dummyScrutVar0.equals(2))
        {
            return 64;
        }
           
        return -1;
    }

    /**
    * Constructs an instance of the 
    *  {@code Info}
    *  class.
    */
    public Info() throws Exception {
        _flags = zlibCompileFlags();
    }

    /**
    * True if the library is compiled with debug info
    */
    public boolean getHasDebugInfo() throws Exception {
        return 0 != (_flags & 0x100);
    }

    /**
    * True if the library is compiled with assembly optimizations
    */
    public boolean getUsesAssemblyCode() throws Exception {
        return 0 != (_flags & 0x200);
    }

    /**
    * Gets the size of the unsigned int that was compiled into Zlib
    */
    public int getSizeOfUInt() throws Exception {
        return bitSize(_flags & 3);
    }

    /**
    * Gets the size of the unsigned long that was compiled into Zlib
    */
    public int getSizeOfULong() throws Exception {
        return bitSize((_flags >> 2) & 3);
    }

    /**
    * Gets the size of the pointers that were compiled into Zlib
    */
    public int getSizeOfPointer() throws Exception {
        return bitSize((_flags >> 4) & 3);
    }

    /**
    * Gets the size of the z_off_t type that was compiled into Zlib
    */
    public int getSizeOfOffset() throws Exception {
        return bitSize((_flags >> 6) & 3);
    }

    /**
    * Gets the version of ZLib as a string, e.g. "1.2.1"
    */
    public static String getVersion() throws Exception {
        return zlibVersion();
    }

}


