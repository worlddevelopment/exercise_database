//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import docx2tex.Library.TagEnum;
import docx2tex.Library.Taging;
import docx2tex.Library.TagRun;

public class TagStartRun  extends TagRun 
{
    public TagStartRun(TagEnum tag, Taging tagingFn) throws Exception {
        super(tag, tagingFn);
    }

    public String getTeXText() throws Exception {
        return _tagingFn.enum2TextStart(this.getTag());
    }

}


