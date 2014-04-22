//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class FileHandlerNotCorrectlyInitializedException  extends Exception 
{
    public FileHandlerNotCorrectlyInitializedException() throws Exception {
        super("The file handler is not correctly initialized.");
    }

    protected FileHandlerNotCorrectlyInitializedException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


