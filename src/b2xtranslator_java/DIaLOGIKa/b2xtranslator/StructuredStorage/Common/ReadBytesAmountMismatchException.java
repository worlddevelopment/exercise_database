//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class ReadBytesAmountMismatchException  extends Exception 
{
    public ReadBytesAmountMismatchException() throws Exception {
        super("The number of bytes read mismatches the specified amount.");
    }

    protected ReadBytesAmountMismatchException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


