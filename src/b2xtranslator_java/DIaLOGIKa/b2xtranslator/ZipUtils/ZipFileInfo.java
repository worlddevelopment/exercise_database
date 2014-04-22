//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:54 AM
//

package DIaLOGIKa.b2xtranslator.ZipUtils;


/**
* Global information about the zip file.
*/
public class ZipFileInfo   
{
    public ZipFileInfo() {
    }

    /**
    * The number of entries in the directory.
    */
    public UIntPtr EntryCount = new UIntPtr();
    /**
    * Length of zip file comment in bytes (8 bit characters).
    */
    public UIntPtr CommentLength = new UIntPtr();
}


