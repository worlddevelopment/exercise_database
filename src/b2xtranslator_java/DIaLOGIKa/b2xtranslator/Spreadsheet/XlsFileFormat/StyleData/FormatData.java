//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:53 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.StyleData;


/**
* This object stores the data from a format biff record
*/
public class FormatData   
{
    public int ifmt;
    public String formatString;
    /**
    * Ctor
    * 
    *  @param ifmt ifmt value from the parsed biff record
    *  @param formatstring the formatstring
    */
    public FormatData(int ifmt, String formatstring) throws Exception {
        this.formatString = formatstring;
        this.ifmt = ifmt;
    }

}


