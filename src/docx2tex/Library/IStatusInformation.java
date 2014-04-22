//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;


/**
* Interface for status info output
*/
public interface IStatusInformation   
{
    /**
    * Write simple text
    * 
    *  @param data
    */
    void write(String data) throws Exception ;

    /**
    * Write simple text with CR
    * 
    *  @param data
    */
    void writeCR(String data) throws Exception ;

    /**
    * Write simple text with CRLF
    * 
    *  @param data
    */
    void writeLine(String data) throws Exception ;

}


