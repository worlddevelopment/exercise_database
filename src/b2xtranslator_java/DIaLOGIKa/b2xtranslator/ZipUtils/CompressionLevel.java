//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;


public enum CompressionLevel
{
    /**
    * Specifies the amount of compression to apply to compressed zip entires.Default compression level.  A good choice for speed and size.
    */
    Default,
    /**
    * Do not perfrom compression.
    */
    None,
    /**
    * Compress the entry as fast as possible size trading size for time.
    */
    Fastest,
    __dummyEnum__0,
    __dummyEnum__1,
    /**
    * Compress the entry using a balance of size and time.
    */
    Average,
    __dummyEnum__2,
    __dummyEnum__3,
    __dummyEnum__4,
    /**
    * Compress the entry to smallest possible size trading time for size.
    */
    Smallest
}

