//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class StreamNotFoundException  extends Exception 
{
    public StreamNotFoundException(String name) throws Exception {
        super("Stream with name '" + name + "' not found.");
    }

    protected StreamNotFoundException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


