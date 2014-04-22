//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import docx2tex.Library.StyleEnum;
import docx2tex.Library.StyleRun;
import docx2tex.Library.Styling;

public class StyleEndRun  extends StyleRun 
{
    public StyleEndRun(StyleEnum style, Styling stylingFn) throws Exception {
        super(style, stylingFn);
    }

    public String getTeXText() throws Exception {
        return _stylingFn.enum2TextEnd(this.getStyle());
    }

}


