//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:22:00 AM
//

package docx2tex.Library;

import docx2tex.Library.Config;
import docx2tex.Library.StyleEnum;
import java.util.HashMap;

public class Styling   
{
    private static HashMap<String,String> _paraStylePairs;
    private static HashMap<String,String> _runStylePairs;
    public Styling() throws Exception {
        initStylePairs();
    }

    public String resolveParaStyle(String styleName) throws Exception {
        if (_paraStylePairs.containsKey(styleName.toLowerCase()))
        {
            return _paraStylePairs.get(styleName);
        }
         
        return styleName;
    }

    public String resolveRunStyle(String styleName) throws Exception {
        if (_runStylePairs.containsKey(styleName.toLowerCase()))
        {
            return _runStylePairs.get(styleName);
        }
         
        return styleName;
    }

    private void initStylePairs() throws Exception {
        _paraStylePairs = new HashMap<String,String>();
        _paraStylePairs.put("section", Config.Instance.getStyleMap().getSection());
        _paraStylePairs.put("subsection", Config.Instance.getStyleMap().getSubSection());
        _paraStylePairs.put("subsubsection", Config.Instance.getStyleMap().getSubSubSection());
        _paraStylePairs.put("verbatim", Config.Instance.getStyleMap().getVerbatim());
        _runStylePairs = new HashMap<String,String>();
        _runStylePairs.put("section", Config.Instance.getStyleMap().getSection());
        _runStylePairs.put("subsection", Config.Instance.getStyleMap().getSubSection());
        _runStylePairs.put("subsubsection", Config.Instance.getStyleMap().getSubSubSection());
        _runStylePairs.put("verbatim", Config.Instance.getStyleMap().getVerbatim());
    }

    public String enum2TextStart(StyleEnum styleEnum) throws Exception {
        switch(styleEnum)
        {
            case TextIt: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getTextIt();
            case TextBf: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getTextBf();
            case Underline: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getUnderline();
            case Sout: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getSout();
            case Xout: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getXout();
            case TextSc: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getTextSc();
            case TextC: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getTextC();
            case SuperScript: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getSuperScript();
            case SubScript: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getSubScript();
            case ParaFlushRight: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getParaFlushRight();
            case ParaCenter: 
                return Config.Instance.getLaTeXTags().getStylePair().getBegin().getParaCenter();
        
        }
        return "";
    }

    public String enum2TextEnd(StyleEnum styleEnum) throws Exception {
        switch(styleEnum)
        {
            case TextIt: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getTextIt();
            case TextBf: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getTextBf();
            case Underline: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getUnderline();
            case Sout: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getSout();
            case Xout: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getXout();
            case TextSc: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getTextSc();
            case TextC: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getTextC();
            case SuperScript: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getSuperScript();
            case SubScript: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getSubScript();
            case ParaFlushRight: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getParaFlushRight();
            case ParaCenter: 
                return Config.Instance.getLaTeXTags().getStylePair().getEnd().getParaCenter();
        
        }
        return "";
    }

}


