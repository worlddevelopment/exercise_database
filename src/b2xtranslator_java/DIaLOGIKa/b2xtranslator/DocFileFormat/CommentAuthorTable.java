//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:57 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;

public class CommentAuthorTable  extends CSList<String> 
{
    public CommentAuthorTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        tableStream.Seek(fib.fcGrpXstAtnOwners, System.IO.SeekOrigin.Begin);
        VirtualStreamReader reader = new VirtualStreamReader(tableStream);
        while (tableStream.Position < (fib.fcGrpXstAtnOwners + fib.lcbGrpXstAtnOwners))
        {
            short cch = reader.ReadInt16();
            this.Add(Encoding.Unicode.GetString(reader.ReadBytes(cch * 2)));
        }
    }

}


