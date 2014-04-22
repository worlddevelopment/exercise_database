//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Tag;

/**
* Tag info
*/
final public class Tag  extends Docx2TexAutoConfig 
{
    public Tag() throws Exception {
    }

    public Tag(Tag system, Tag user, Tag document) throws Exception {
        super(system, user, document);
    }

    /**
    * Verbatim
    */
    private String __Verbatim;
    public String getVerbatim() {
        return __Verbatim;
    }

    public void setVerbatim(String value) {
        __Verbatim = value;
    }

    /**
    * Math
    */
    private String __Math;
    public String getMath() {
        return __Math;
    }

    public void setMath(String value) {
        __Math = value;
    }

    /**
    * Figure
    */
    private String __Figure;
    public String getFigure() {
        return __Figure;
    }

    public void setFigure(String value) {
        __Figure = value;
    }

    /**
    * Enumerate
    */
    private String __Enumerate;
    public String getEnumerate() {
        return __Enumerate;
    }

    public void setEnumerate(String value) {
        __Enumerate = value;
    }

    /**
    * Itemize
    */
    private String __Itemize;
    public String getItemize() {
        return __Itemize;
    }

    public void setItemize(String value) {
        __Itemize = value;
    }

    /**
    * Table
    */
    private String __Table;
    public String getTable() {
        return __Table;
    }

    public void setTable(String value) {
        __Table = value;
    }

}


