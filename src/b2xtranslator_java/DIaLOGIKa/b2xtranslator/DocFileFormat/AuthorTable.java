//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:56 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.FileInformationBlock;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;

public class AuthorTable  extends CSList<String> 
{
    /**
    * Parses the bytes to retrieve a AuthorTable
    * 
    *  @param bytes The bytes
    */
    public AuthorTable(FileInformationBlock fib, VirtualStream tableStream) throws Exception {
        int pos = 8;
        byte[] uniChar = new byte[2];
        StringBuilder name = new StringBuilder();
        while (pos < fib.lcbSttbfRMark)
        {
            tableStream.read(uniChar,0,2,(int)(fib.fcSttbfRMark + pos));
            char cPos = Encoding.Unicode.GetString(uniChar).ToCharArray()[0];
            if ((int)cPos > 0x1F)
            {
                name.append(cPos);
            }
            else
            {
                //there is a seperator that terminates this name
                this.add(name.toString());
                name = new StringBuilder();
            } 
            pos += 2;
        }
        //add last name
        this.add(name.toString());
    }

}


