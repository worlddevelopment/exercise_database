//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import docx2tex.Library.Run;
import docx2tex.Library.StyleEnum;
import docx2tex.Library.Styling;

abstract public class StyleRun  extends Run 
{
    private StyleEnum __Style = StyleEnum.TextIt;
    public StyleEnum getStyle() {
        return __Style;
    }

    public void setStyle(StyleEnum value) {
        __Style = value;
    }

    protected Styling _stylingFn;
    public StyleRun(StyleEnum style, Styling stylingFn) throws Exception {
        this.setStyle(style);
        this._stylingFn = stylingFn;
    }

}


