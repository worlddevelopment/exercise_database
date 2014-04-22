//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class InvalidSectorInChainException  extends Exception 
{
    public InvalidSectorInChainException() throws Exception {
        super("Chain could not be build due to an invalid sector id.");
    }

    protected InvalidSectorInChainException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


