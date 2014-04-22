//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Infra;
import docx2tex.Library.Data.LaTeXTags;
import docx2tex.Library.Data.StyleMap;

/**
* Root of config data to be serialized
*/
final public class Docx2TexConfig   
{
    public Docx2TexConfig() throws Exception {
        cleanProperties();
    }

    private Infra __Infra;
    public Infra getInfra() {
        return __Infra;
    }

    public void setInfra(Infra value) {
        __Infra = value;
    }

    private LaTeXTags __LaTeXTags;
    public LaTeXTags getLaTeXTags() {
        return __LaTeXTags;
    }

    public void setLaTeXTags(LaTeXTags value) {
        __LaTeXTags = value;
    }

    private StyleMap __StyleMap;
    public StyleMap getStyleMap() {
        return __StyleMap;
    }

    public void setStyleMap(StyleMap value) {
        __StyleMap = value;
    }

    private String __ConfigurationFilePath;
    public String getConfigurationFilePath() {
        return __ConfigurationFilePath;
    }

    public void setConfigurationFilePath(String value) {
        __ConfigurationFilePath = value;
    }

    public void cleanProperties() throws Exception {
        setInfra(new Infra());
        setLaTeXTags(new LaTeXTags());
        setStyleMap(new StyleMap());
    }

}


