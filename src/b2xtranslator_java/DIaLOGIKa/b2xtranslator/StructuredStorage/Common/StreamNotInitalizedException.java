//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class StreamNotInitalizedException  extends Exception 
{
    public StreamNotInitalizedException() throws Exception {
        super("The current stream is not initialized.");
    }

    protected StreamNotInitalizedException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


