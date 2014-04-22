//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.StyleMap;

/**
* Style mapping (map style in word doc to latex style)
*/
final public class StyleMap  extends Docx2TexAutoConfig 
{
    public StyleMap() throws Exception {
    }

    public StyleMap(StyleMap system, StyleMap user, StyleMap document) throws Exception {
        super(system, user, document);
    }

    /**
    * Name of the section style
    */
    private String __Section;
    public String getSection() {
        return __Section;
    }

    public void setSection(String value) {
        __Section = value;
    }

    /**
    * Name of the subsection style
    */
    private String __SubSection;
    public String getSubSection() {
        return __SubSection;
    }

    public void setSubSection(String value) {
        __SubSection = value;
    }

    /**
    * Name of the subsubsection style
    */
    private String __SubSubSection;
    public String getSubSubSection() {
        return __SubSubSection;
    }

    public void setSubSubSection(String value) {
        __SubSubSection = value;
    }

    /**
    * Name of the verbatim style
    */
    private String __Verbatim;
    public String getVerbatim() {
        return __Verbatim;
    }

    public void setVerbatim(String value) {
        __Verbatim = value;
    }

}


