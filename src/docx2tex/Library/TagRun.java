//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import docx2tex.Library.Run;
import docx2tex.Library.TagEnum;
import docx2tex.Library.Taging;

abstract public class TagRun  extends Run 
{
    private TagEnum __Tag = TagEnum.Verbatim;
    public TagEnum getTag() {
        return __Tag;
    }

    public void setTag(TagEnum value) {
        __Tag = value;
    }

    protected Taging _tagingFn;
    public TagRun(TagEnum tag, Taging tagingFn) throws Exception {
        this.setTag(tag);
        this._tagingFn = tagingFn;
    }

}


