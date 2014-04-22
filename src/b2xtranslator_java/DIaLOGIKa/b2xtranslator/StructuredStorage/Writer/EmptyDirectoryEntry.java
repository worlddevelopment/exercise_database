//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:53 AM
//

package DIaLOGIKa.b2xtranslator.StructuredStorage.Writer;

import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryColor;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.DirectoryEntryType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.BaseDirectoryEntry;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Writer.StructuredStorageContext;

/**
* Empty directory entry used to pad out directory stream.
* Author: math
*/
public class EmptyDirectoryEntry  extends BaseDirectoryEntry 
{
    /**
    * Constructor
    * 
    *  @param context the current context
    */
    public EmptyDirectoryEntry(StructuredStorageContext context) throws Exception {
        super("", context);
        setColor(DirectoryEntryColor.DE_RED);
        // 0x0
        setType(DirectoryEntryType.STGTY_INVALID);
    }

}


