//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class UnsupportedSizeException  extends Exception 
{
    public UnsupportedSizeException(String value) throws Exception {
        super("The size of " + value + " is not supported.");
    }

    protected UnsupportedSizeException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


