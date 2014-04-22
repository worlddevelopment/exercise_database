//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Tag;
import docx2tex.Library.Data.TagPair;

/**
* Pair of begin and end tags
*/
final public class TagPair  extends Docx2TexAutoConfig 
{
    public TagPair() throws Exception {
        setBegin(new Tag());
        setEnd(new Tag());
    }

    public TagPair(TagPair system, TagPair user, TagPair document) throws Exception {
        super(system, user, document);
        setBegin(new Tag(system.getBegin(),user != null ? user.getBegin() : null,document != null ? document.getBegin() : null));
        setEnd(new Tag(system.getEnd(),user != null ? user.getEnd() : null,document != null ? document.getEnd() : null));
    }

    /**
    * Begin tags
    */
    private Tag __Begin;
    public Tag getBegin() {
        return __Begin;
    }

    public void setBegin(Tag value) {
        __Begin = value;
    }

    /**
    * End tags
    */
    private Tag __End;
    public Tag getEnd() {
        return __End;
    }

    public void setEnd(Tag value) {
        __End = value;
    }

}


