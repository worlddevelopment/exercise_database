//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class ValueNotZeroException  extends Exception 
{
    public ValueNotZeroException(String value) throws Exception {
        super(value + " must be zero.");
    }

    protected ValueNotZeroException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


