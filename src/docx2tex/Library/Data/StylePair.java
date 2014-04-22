//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Style;
import docx2tex.Library.Data.StylePair;

/**
* Pair of begin and end styles
*/
final public class StylePair  extends Docx2TexAutoConfig 
{
    public StylePair() throws Exception {
        setBegin(new Style());
        setEnd(new Style());
    }

    public StylePair(StylePair system, StylePair user, StylePair document) throws Exception {
        super(system, user, document);
        setBegin(new Style(system.getBegin(),user != null ? user.getBegin() : null,document != null ? document.getBegin() : null));
        setEnd(new Style(system.getEnd(),user != null ? user.getEnd() : null,document != null ? document.getEnd() : null));
    }

    /**
    * Begin styles
    */
    private Style __Begin;
    public Style getBegin() {
        return __Begin;
    }

    public void setBegin(Style value) {
        __Begin = value;
    }

    /**
    * End styles
    */
    private Style __End;
    public Style getEnd() {
        return __End;
    }

    public void setEnd(Style value) {
        __End = value;
    }

}


