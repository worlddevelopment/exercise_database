//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class DiFatInconsistentException  extends Exception 
{
    public DiFatInconsistentException() throws Exception {
        super("Inconsistancy found while writing DiFat.");
    }

    protected DiFatInconsistentException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


