//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:56 AM
//

package docx2tex;

import docx2tex.Library.IStatusInformation;

public class ConsoleOutput   implements IStatusInformation
{
    public void write(String data) throws Exception {
        System.out.print(data);
    }

    public void writeCR(String data) throws Exception {
        System.out.print(data + "\r");
    }

    public void writeLine(String data) throws Exception {
        System.out.println(data);
    }

    public void writeLine() throws Exception {
        System.out.println();
    }

}


