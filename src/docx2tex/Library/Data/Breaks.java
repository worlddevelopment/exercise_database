//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Breaks;
import docx2tex.Library.Data.Docx2TexAutoConfig;

/**
* Break representations
*/
final public class Breaks  extends Docx2TexAutoConfig 
{
    public Breaks() throws Exception {
    }

    public Breaks(Breaks system, Breaks user, Breaks document) throws Exception {
        super(system, user, document);
    }

    /**
    * Page break
    */
    private String __Page;
    public String getPage() {
        return __Page;
    }

    public void setPage(String value) {
        __Page = value;
    }

    /**
    * Line break
    */
    private String __Line;
    public String getLine() {
        return __Line;
    }

    public void setLine(String value) {
        __Line = value;
    }

}


