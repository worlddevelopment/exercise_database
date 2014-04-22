//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Style;

/**
* Style info
*/
final public class Style  extends Docx2TexAutoConfig 
{
    public Style() throws Exception {
    }

    public Style(Style system, Style user, Style document) throws Exception {
        super(system, user, document);
    }

    /**
    * Italic
    */
    private String __TextIt;
    public String getTextIt() {
        return __TextIt;
    }

    public void setTextIt(String value) {
        __TextIt = value;
    }

    /**
    * Bold
    */
    private String __TextBf;
    public String getTextBf() {
        return __TextBf;
    }

    public void setTextBf(String value) {
        __TextBf = value;
    }

    /**
    * Underline
    */
    private String __Underline;
    public String getUnderline() {
        return __Underline;
    }

    public void setUnderline(String value) {
        __Underline = value;
    }

    /**
    * Strike out
    */
    private String __Sout;
    public String getSout() {
        return __Sout;
    }

    public void setSout(String value) {
        __Sout = value;
    }

    /**
    * Double strike out
    */
    private String __Xout;
    public String getXout() {
        return __Xout;
    }

    public void setXout(String value) {
        __Xout = value;
    }

    /**
    * Small capitals
    */
    private String __TextSc;
    public String getTextSc() {
        return __TextSc;
    }

    public void setTextSc(String value) {
        __TextSc = value;
    }

    /**
    * All capitals
    */
    private String __TextC;
    public String getTextC() {
        return __TextC;
    }

    public void setTextC(String value) {
        __TextC = value;
    }

    /**
    * Superscript
    */
    private String __SuperScript;
    public String getSuperScript() {
        return __SuperScript;
    }

    public void setSuperScript(String value) {
        __SuperScript = value;
    }

    /**
    * Subscript
    */
    private String __SubScript;
    public String getSubScript() {
        return __SubScript;
    }

    public void setSubScript(String value) {
        __SubScript = value;
    }

    /**
    * Flush right paragraph
    */
    private String __ParaFlushRight;
    public String getParaFlushRight() {
        return __ParaFlushRight;
    }

    public void setParaFlushRight(String value) {
        __ParaFlushRight = value;
    }

    /**
    * Flush left paragraph
    */
    private String __ParaCenter;
    public String getParaCenter() {
        return __ParaCenter;
    }

    public void setParaCenter(String value) {
        __ParaCenter = value;
    }

}


