//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class InvalidValueInHeaderException  extends Exception 
{
    public InvalidValueInHeaderException(String value) throws Exception {
        super("The value for '" + value + "' in the header is invalid.");
    }

    protected InvalidValueInHeaderException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


