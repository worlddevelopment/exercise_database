//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:03 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;


public class UnspportedFileVersionException  extends Exception 
{
    private static final String MSG = "DocFileFormat does not support .doc files that have been created with Word versions older than Word 97.";
    public UnspportedFileVersionException() throws Exception {
        super(MSG);
    }

    public UnspportedFileVersionException(String text) throws Exception {
        super(text);
    }

}


