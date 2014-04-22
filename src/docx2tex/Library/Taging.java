//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import docx2tex.Library.Config;
import docx2tex.Library.TagEnum;

public class Taging   
{
    public Taging() throws Exception {
    }

    public String enum2TextStart(TagEnum tagEnum) throws Exception {
        switch(tagEnum)
        {
            case Verbatim: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getVerbatim();
            case Math: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getMath();
            case Figure: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getFigure();
            case Enumerate: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getEnumerate();
            case Itemize: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getItemize();
            case Table: 
                return Config.Instance.getLaTeXTags().getTagPair().getBegin().getTable();
        
        }
        return "";
    }

    public String enum2TextEnd(TagEnum tagEnum) throws Exception {
        switch(tagEnum)
        {
            case Verbatim: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getVerbatim();
            case Math: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getMath();
            case Figure: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getFigure();
            case Enumerate: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getEnumerate();
            case Itemize: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getItemize();
            case Table: 
                return Config.Instance.getLaTeXTags().getTagPair().getEnd().getTable();
        
        }
        return "";
    }

}


