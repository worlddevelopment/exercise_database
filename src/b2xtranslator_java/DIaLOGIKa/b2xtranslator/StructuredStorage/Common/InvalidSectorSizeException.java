//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class InvalidSectorSizeException  extends Exception 
{
    public InvalidSectorSizeException() throws Exception {
        super("Inconsistancy found while writing a sector.");
    }

    protected InvalidSectorSizeException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


