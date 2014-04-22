//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:59 AM
//

package docx2tex.Library;

import docx2tex.Library.Run;

public class VerbatimRun  extends Run 
{
    private String __Text;
    public String getText() {
        return __Text;
    }

    public void setText(String value) {
        __Text = value;
    }

    public VerbatimRun(String text) throws Exception {
        this.setText(text);
    }

    public String getTeXText() throws Exception {
        return getText();
    }

}


