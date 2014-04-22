//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;

public class AnnotationOwnerList  extends CSList<String> 
{
    public AnnotationOwnerList(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        super();
        tableStream.Seek(fib.fcGrpXstAtnOwners, System.IO.SeekOrigin.Begin);
        while (tableStream.getPosition() < (fib.fcGrpXstAtnOwners + fib.lcbGrpXstAtnOwners))
        {
            this.add(DIaLOGIKa.b2xtranslator.Tools.Utils.readXst(tableStream));
        }
    }

}


