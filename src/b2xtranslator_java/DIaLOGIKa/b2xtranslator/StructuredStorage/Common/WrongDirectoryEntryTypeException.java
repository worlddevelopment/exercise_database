//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class WrongDirectoryEntryTypeException  extends Exception 
{
    public WrongDirectoryEntryTypeException() throws Exception {
        super("The directory entry is not of type STGTY_STREAM.");
    }

    protected WrongDirectoryEntryTypeException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


