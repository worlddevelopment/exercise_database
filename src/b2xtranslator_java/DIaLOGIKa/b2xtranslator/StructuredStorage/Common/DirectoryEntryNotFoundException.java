//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:51 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Common;


public class DirectoryEntryNotFoundException  extends Exception 
{
    public DirectoryEntryNotFoundException(String name) throws Exception {
        super("DirectoryEntry with name '" + name + "' not found.");
    }

    protected DirectoryEntryNotFoundException(SerializationInfo info, StreamingContext ctxt) throws Exception {
        super(info, ctxt);
    }

}


