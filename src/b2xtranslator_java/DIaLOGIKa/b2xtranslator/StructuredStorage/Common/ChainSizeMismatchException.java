//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class ChainSizeMismatchException  extends Exception 
{
    public ChainSizeMismatchException(String name) throws Exception {
        super("The number of sectors used by " + name + " does not match the specified size.");
    }

    protected ChainSizeMismatchException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


