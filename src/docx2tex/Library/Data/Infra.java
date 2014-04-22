//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.Infra;

/**
* docx2tex running infrastructure
*/
final public class Infra  extends Docx2TexAutoConfig 
{
    public Infra() throws Exception {
    }

    public Infra(Infra system, Infra user, Infra document) throws Exception {
        super(system, user, document);
    }

    /**
    * Path to the imagemagick program
    */
    private String __ImageMagickPath;
    public String getImageMagickPath() {
        return __ImageMagickPath;
    }

    public void setImageMagickPath(String value) {
        __ImageMagickPath = value;
    }

    /**
    * Input encoding
    */
    private String __InputEnc;
    public String getInputEnc() {
        return __InputEnc;
    }

    public void setInputEnc(String value) {
        __InputEnc = value;
    }

    private Integer __LineLength;
    /**
    * Line length
    */
    public Integer getLineLength() {
        return __LineLength;
    }

    public void setLineLength(Integer value) {
        __LineLength = value;
    }

    /**
    * Document class type
    */
    private String __DocumentClass;
    public String getDocumentClass() {
        return __DocumentClass;
    }

    public void setDocumentClass(String value) {
        __DocumentClass = value;
    }

    /**
    * default font size
    */
    private String __FontSize;
    public String getFontSize() {
        return __FontSize;
    }

    public void setFontSize(String value) {
        __FontSize = value;
    }

    /**
    * paper size
    */
    private String __PaperSize;
    public String getPaperSize() {
        return __PaperSize;
    }

    public void setPaperSize(String value) {
        __PaperSize = value;
    }

    private Boolean __Landscape;
    /**
    * is landscape
    */
    public Boolean getLandscape() {
        return __Landscape;
    }

    public void setLandscape(Boolean value) {
        __Landscape = value;
    }

}


