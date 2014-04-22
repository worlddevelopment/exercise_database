//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:30 AM
//

package DotZLib;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum CompressLevel
{
    /**
    * Defines constants for the available compression levels in zlib
    * 
    * The default compression level with a reasonable compromise between compression and speed
    */
    Default,
    /**
    * No compression at all. The data are passed straight through.
    */
    None,
    __dummyEnum__0,
    __dummyEnum__1,
    __dummyEnum__2,
    __dummyEnum__3,
    __dummyEnum__4,
    __dummyEnum__5,
    __dummyEnum__6,
    /**
    * The maximum compression rate available.
    */
    Best,
    /**
    * The fastest available compression level.
    */
    Fastest
}

